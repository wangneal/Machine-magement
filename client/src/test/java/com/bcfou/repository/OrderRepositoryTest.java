package com.bcfou.repository;

import com.bcfou.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: 编程否
 * @Date: 2018/8/26 9:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderRepositoryTest {

    @Resource
    private OrderRepository orderRepository;
    @Test
    public void findByMachineIdAndStatus() {
        Order result = orderRepository.findByMachineIdAndStatus(1l, 0);
        System.out.println(result.toString());
    }

    @Test
    @Transactional
    public void saveTest(){
        Order order = new Order();
        order.setMachineId((long) 2);
        order.setUId((long) 1);
        //保存数据
        Order result = orderRepository.save(order);
        log.info("【result】:" + result.toString());
        Assert.assertNotNull(result);
    }
}