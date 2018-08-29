package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.OrderEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * 订单管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-08-25 22:17:09
 */
public interface OrderDao extends BaseMapper<OrderEntity> {
    List<OrderEntity> findAll();
	
}
