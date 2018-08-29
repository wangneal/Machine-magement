package io.renren.modules.sys.service.impl;

import com.mchange.lang.LongUtils;
import com.mchange.v1.util.ListUtils;
import io.renren.common.utils.Constant;
import io.renren.modules.sys.entity.MachinesEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.MachinesService;
import io.renren.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.OrderDao;
import io.renren.modules.sys.entity.OrderEntity;
import io.renren.modules.sys.service.OrderService;

import javax.annotation.Resource;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    private MachinesService machinesService;
    @Autowired
    private SysUserService userService;
    @Resource
    private OrderDao orderDao;

    @Override
    public List<OrderEntity> findAll() {
        return orderDao.findAll();
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        //先通过设备名称查询到设备表中的位置
        String machineName = (String) params.get("machineName");
        List<MachinesEntity> machinesEntityList = machinesService.findByMachinesName(machineName);
        List<Long> machineIds = machinesEntityList.stream()
                .map(MachinesEntity::getMachinesId)
                .collect(Collectors.toList());
        Page<OrderEntity> page = this.selectPage(
                new Query<OrderEntity>(params).getPage(),
                new EntityWrapper<OrderEntity>()
                        .in(true, "machine_id", machineIds)
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER)));

        for (OrderEntity orderEntity : page.getRecords()){
            //查询相关的设备信息
            MachinesEntity machinesEntity = machinesService.selectById(orderEntity.getMachineId());
            orderEntity.setMachineName(machinesEntity.getMachinesName());
            orderEntity.setMachineType(machinesEntity.getMachinesType());
            orderEntity.setMachineStatus(machinesEntity.getMachinesStatus());
            orderEntity.setMachinesEntity(machinesEntity);
            //查询用户信息
            SysUserEntity userEntity = userService.selectById(orderEntity.getUId());
            orderEntity.setUserName(userEntity.getUsername());
        }


        return new PageUtils(page);
    }

}
