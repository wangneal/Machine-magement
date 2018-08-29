package io.renren.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.ReposDao;
import io.renren.modules.sys.entity.ReposEntity;
import io.renren.modules.sys.service.ReposService;

import javax.annotation.Resource;


@Service("reposService")
public class ReposServiceImpl extends ServiceImpl<ReposDao, ReposEntity> implements ReposService {

    @Resource
    private ReposDao reposDao;
    @Override
    public List<ReposEntity> findAll() {
        return reposDao.findAll();
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ReposEntity> page = this.selectPage(
                new Query<ReposEntity>(params).getPage(),
                new EntityWrapper<ReposEntity>()
        );

        return new PageUtils(page);
    }

}
