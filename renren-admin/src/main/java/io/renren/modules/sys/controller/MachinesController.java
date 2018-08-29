package io.renren.modules.sys.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.renren.common.utils.ExportExcelUtils;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.dataObject.ExcelData;
import io.renren.modules.sys.entity.OrderEntity;
import io.renren.modules.sys.entity.ReposEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.ReposService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.MachinesEntity;
import io.renren.modules.sys.service.MachinesService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;

import javax.servlet.http.HttpServletResponse;


/**
 * 设备管理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-08-25 19:57:30
 */
@RestController
@RequestMapping("sys/machines")
public class MachinesController {
    @Autowired
    private MachinesService machinesService;
    @Autowired
    private ReposService reposService;
    private static final Logger log = LoggerFactory.getLogger(MachinesController.class);
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:machines:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = machinesService.queryPage(params);
        List<ReposEntity> reposEntityList = reposService.findAll();

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{machinesId}")
    @RequiresPermissions("sys:machines:info")
    public R info(@PathVariable("machinesId") Long machinesId){
        MachinesEntity machines = machinesService.selectById(machinesId);
        //查询设备对应的仓位
        ReposEntity reposEntity = reposService.selectById(machines.getRepoId());
        machines.setRepoName(reposEntity.getName());
        return R.ok().put("machines", machines);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:machines:save")
    public R save(@RequestBody MachinesEntity machines){
        machinesService.insert(machines);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:machines:update")
    public R update(@RequestBody MachinesEntity machines){
        ValidatorUtils.validateEntity(machines);
        machinesService.updateAllColumnById(machines);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:machines:delete")
    public R delete(@RequestBody Long[] machinesIds){
        machinesService.deleteBatchIds(Arrays.asList(machinesIds));

        return R.ok();
    }
    /**
     * 导出全部数据的excel
     */
    @RequestMapping("/excels")
    public R createExcel(HttpServletResponse response){
        try {
            //首先查询数据库
            List<MachinesEntity> machinesEntityList = machinesService.findAll();
            //拼接数据
            //设置excel表的数据格式
            ExcelData data = new ExcelData();
            List<String> titleList = new ArrayList<>();//标题
            data.setName("设备详情");
            //设置标题
            titleList.add("序号");
            titleList.add("设备名称");
            titleList.add("设备编号");
            titleList.add("设备状态");
            titleList.add("设备分类");
            titleList.add("设备所在仓位");
            data.setTitles(titleList);
            //设置数据
            List<List<Object>> rows = new ArrayList();
            for (MachinesEntity m : machinesEntityList) {
                List<Object> row = new ArrayList<>();
                ReposEntity reposEntity = reposService.selectById(m.getRepoId());
                row.add(m.getMachinesId());
                row.add(m.getMachinesName());
                row.add(m.getMachinesType());
                if (m.getMachinesStatus() == 0){
                    row.add("正常");
                }
                if (m.getMachinesStatus() == 1){
                    row.add("待维修");
                }
                if (m.getMachinesStatus() == 2){
                    row.add("已报废");
                }

                row.add(m.getMachinesCategory());
                row.add(reposEntity.getName());
                rows.add(row);
            }
            data.setRows(rows);
            //导出excel
            ExportExcelUtils.exportExcel(response, "orderData.xlsx", data);
            log.info("导出excel格式全部数据成功");
            return R.ok();
        }catch (Exception e){
            return R.error("出了点故障，导出数据失败！");
        }
    }
}
