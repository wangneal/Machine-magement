package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.MachinesEntity;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: 编程否
 * @Date: 2018/8/27 22:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MachinesDaoTest {

    @Resource
    private MachinesDao machinesDao;
    @Test
    public void findByRepoId() {
        List<Long> ids = new ArrayList<>();
        ids.add((long) 1);
        List<MachinesEntity> result = machinesDao.findByRepoId(ids);
        System.out.println(result.toString());
    }

    @Test
    public void findTest(){
        List<MachinesEntity> machinesEntityList = machinesDao.findByMachinesName("2500");
        System.out.println(machinesEntityList.toString());
    }
}