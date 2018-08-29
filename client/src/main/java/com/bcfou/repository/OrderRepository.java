package com.bcfou.repository;

import com.bcfou.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: 编程否
 * @Date: 2018/8/25 23:38
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByMachineIdAndStatus(Long machineId, Integer status);
}
