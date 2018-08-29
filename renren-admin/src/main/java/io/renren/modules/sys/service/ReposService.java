package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.ReposEntity;

import java.util.List;
import java.util.Map;

/**
 * 仓位管理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-08-25 19:57:30
 */
public interface ReposService extends IService<ReposEntity> {
    List<ReposEntity> findAll();
    PageUtils queryPage(Map<String, Object> params);
}

