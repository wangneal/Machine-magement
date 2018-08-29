package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.MachinesEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 设备管理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-08-25 19:57:30
 */
public interface MachinesService extends IService<MachinesEntity> {
    List<MachinesEntity> findByMachinesName(String machinesName);
    List<MachinesEntity> findAll();
    PageUtils queryPage(Map<String, Object> params);
}

