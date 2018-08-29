package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.renren.common.utils.Constant;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.dao.MachinesDao;
import io.renren.modules.sys.entity.MachinesEntity;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.service.MachinesService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.ReposEntity;
import io.renren.modules.sys.service.ReposService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;

import javax.annotation.Resource;


/**
 * 仓位管理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-08-25 19:57:30
 */
@RestController
@RequestMapping("sys/repos")
public class ReposController {
    @Autowired
    private ReposService reposService;

    @Autowired
    private MachinesService machinesService;

    @Resource
    private MachinesDao machinesDao;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:repos:list")
    public List<ReposEntity> list(){
        List<ReposEntity> reposEntityList = reposService.findAll();

        return reposEntityList;
    }

    @RequestMapping("/select")
    @RequiresPermissions("sys:repos:list")
    public R selectlist(){
        List<ReposEntity> list = reposService.findAll();
        ReposEntity reposEntity = new ReposEntity();
        reposEntity.setParentId((long) -1);
        reposEntity.setName("一级仓库");
        reposEntity.setReposId((long) 0);
        reposEntity.setOpen(true);
        list.add(reposEntity);
        return R.ok().put("reposList", list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{reposId}")
    @RequiresPermissions("sys:repos:info")
    public R info(@PathVariable("reposId") Long reposId){
        ReposEntity repos = reposService.selectById(reposId);
        return R.ok().put("repos", repos);
    }

    /**
     * 上级仓库Id(主仓库为0)
     */
    @RequestMapping("/info")
    @RequiresPermissions("sys:dept:list")
    public R info(){
        long reposId = 0;
        List<ReposEntity> reposEntityList = reposService.findAll();
        Long parentId = null;
        for (ReposEntity r : reposEntityList){
            if (parentId == null){
                parentId = r.getParentId();
                continue;
            }
            if (parentId > r.getParentId().longValue()){
                parentId = r.getParentId();
            }
        }
        reposId = parentId;

        return R.ok().put("reposId", reposId);
    }
    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:repos:save")
    public R save(@RequestBody ReposEntity repos){
        reposService.insert(repos);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:repos:update")
    public R update(@RequestBody ReposEntity repos){
        ValidatorUtils.validateEntity(repos);
        reposService.updateAllColumnById(repos);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:repos:delete")
    public R delete(@RequestBody Long[] reposIds){
        //由于仓库在设置好后一般不会去增删，
        // 但是为了防止用户这样操作，我们还是加一个判断，
        // 如果仓库下有设备就必须将设备与仓库解绑才能删除。
        List<MachinesEntity> machinesEntityList = machinesDao.findByRepoId(Arrays.asList(reposIds));
        if (machinesEntityList.size() != 0){
            return R.error("该仓位下有设备，无法进行删除，需要将设备移除后再执行此操作");
        }
        reposService.deleteBatchIds(Arrays.asList(reposIds));
        return R.ok();
    }

}
