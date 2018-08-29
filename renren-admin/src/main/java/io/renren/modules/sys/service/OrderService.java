package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.OrderEntity;

import java.util.List;
import java.util.Map;

/**
 * 订单管理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-08-25 22:17:09
 */
public interface OrderService extends IService<OrderEntity> {
    List<OrderEntity> findAll();
    PageUtils queryPage(Map<String, Object> params);
}

