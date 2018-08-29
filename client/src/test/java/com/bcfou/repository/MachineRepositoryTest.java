package com.bcfou.repository;

import com.bcfou.entity.Machine;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: 编程否
 * @Date: 2018/8/27 0:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MachineRepositoryTest {

    @Autowired
    private MachineRepository machineRepository;
    @Test
    public void find(){
        List<Machine> machineList = machineRepository.findAll();
        Assert.assertNotNull(machineList);
    }

}