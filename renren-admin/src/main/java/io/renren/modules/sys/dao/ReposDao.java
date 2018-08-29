package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.MachinesEntity;
import io.renren.modules.sys.entity.ReposEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * 仓位管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-08-25 19:57:30
 */
public interface ReposDao extends BaseMapper<ReposEntity> {
    List<ReposEntity> findAll();
}
