package io.renren.modules.sys.service.impl;

import io.renren.common.utils.Constant;
import io.renren.modules.sys.entity.ReposEntity;
import io.renren.modules.sys.service.ReposService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.MachinesDao;
import io.renren.modules.sys.entity.MachinesEntity;
import io.renren.modules.sys.service.MachinesService;

import javax.annotation.Resource;


@Service("machinesService")
public class MachinesServiceImpl extends ServiceImpl<MachinesDao, MachinesEntity> implements MachinesService {

    @Autowired
    private ReposService reposService;
    @Resource
    private MachinesDao machinesDao;

    @Override
    public List<MachinesEntity> findByMachinesName(String machinesName) {
        return machinesDao.findByMachinesName(machinesName);
    }

    @Override
    public List<MachinesEntity> findAll() {
        return machinesDao.findAll();
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String machineName = (String) params.get("machineName");

        Page<MachinesEntity> page = this.selectPage(
                new Query<MachinesEntity>(params).getPage(),
                new EntityWrapper<MachinesEntity>()
                .like(StringUtils.isNotBlank(machineName), "machines_name", machineName)
                .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
        );
        for (MachinesEntity machinesEntity : page.getRecords()){
            ReposEntity reposEntity = reposService.selectById(machinesEntity.getRepoId());
            if (reposEntity != null){
                machinesEntity.setRepoName(reposEntity.getName());
            }
        }
        return new PageUtils(page);
    }

}
