package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.MachinesEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-08-25 19:57:30
 */
public interface MachinesDao extends BaseMapper<MachinesEntity> {
	List<MachinesEntity> findByRepoId(@Param("ids") List<Long> ids);
	List<MachinesEntity> findAll();
	List<MachinesEntity> findByMachinesName(String machinesName);
}
