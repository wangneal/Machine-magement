package io.renren.modules.sys.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.renren.common.utils.ExportExcelUtils;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.dataObject.ExcelData;
import io.renren.modules.sys.entity.MachinesEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.MachinesService;
import io.renren.modules.sys.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.sys.entity.OrderEntity;
import io.renren.modules.sys.service.OrderService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;

import javax.servlet.http.HttpServletResponse;


/**
 * 订单管理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-08-25 22:17:09
 */
@RestController
@RequestMapping("sys/order")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;

    @Autowired
    private SysUserService userService;

    @Autowired
    private MachinesService machinesService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:order:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:order:info")
    public R info(@PathVariable("id") Long id){
        OrderEntity order = orderService.selectById(id);

        return R.ok().put("order", order);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:order:save")
    public R save(@RequestBody OrderEntity order){
        orderService.insert(order);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:order:update")
    public R update(@RequestBody OrderEntity order){
        ValidatorUtils.validateEntity(order);
        orderService.updateAllColumnById(order);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:order:delete")
    public R delete(@RequestBody Long[] ids){
        orderService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }
    /**
     * 导出全部数据的excel
     */
    @RequestMapping("/excels")
    public R createExcel(HttpServletResponse response){
        try {
            //首先查询数据库
            List<OrderEntity> orderEntityList = orderService.findAll();
            //拼接数据
            //设置excel表的数据格式
            ExcelData data = new ExcelData();
            List<String> titleList = new ArrayList<>();//标题
            data.setName("设备进出记录");
            //设置标题
            titleList.add("序号");
            titleList.add("设备名称");
            titleList.add("设备编号");
            titleList.add("设备状态");
            titleList.add("借出时间");
            titleList.add("归还时间");
            titleList.add("借用人/归还人");
            data.setTitles(titleList);
            //设置数据
            List<List<Object>> rows = new ArrayList();
            SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );//日期格式化
            for (OrderEntity order : orderEntityList) {
                List<Object> row = new ArrayList();
                MachinesEntity machinesEntity = machinesService.selectById(order.getMachineId());
                order.setMachineName(machinesEntity.getMachinesName());
                order.setMachineType(machinesEntity.getMachinesType());
                order.setMachineStatus(machinesEntity.getMachinesStatus());
                SysUserEntity userEntity = userService.selectById(order.getUId());
                order.setUserName(userEntity.getUsername());
                row.add(order.getId());
                row.add(order.getMachineName());
                row.add(order.getMachineType());
                String machineStatus = null;
                if (order.getMachineStatus() == 0) {
                    machineStatus = "正常";
                } else if (order.getMachineStatus() == 1) {
                    machineStatus = "待维修";
                } else {
                    machineStatus = "已报废";
                }
                row.add(machineStatus);
                row.add(simpleDateFormat.format(order.getCreateTime()));
                //判断下更新时间是不是空的，因为如果是空的会导致出现异常
                if (order.getLastTime() != null){
                    row.add(simpleDateFormat.format(order.getLastTime()));
                }else {
                    row.add("");
                }
                row.add(order.getUserName());
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
