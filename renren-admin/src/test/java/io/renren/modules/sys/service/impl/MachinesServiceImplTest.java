package io.renren.modules.sys.service.impl;

import io.renren.modules.sys.entity.MachinesEntity;
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
 * @Date: 2018/8/28 9:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MachinesServiceImplTest {

    @Autowired
    private MachinesServiceImpl machinesService;
    @Test
    public void findAll() {
        List<MachinesEntity> result = machinesService.findAll();
        Assert.assertNotNull(result);
    }
}