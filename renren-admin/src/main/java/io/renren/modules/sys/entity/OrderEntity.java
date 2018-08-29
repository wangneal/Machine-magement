package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.ibatis.annotations.UpdateProvider;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-08-25 22:17:09
 */
@TableName("tb_order")
public class OrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long id;
	/**
	 * 设备id外键
	 */
	private Long machineId;
	/**
	 * 设备信息
	 */
	@TableField(exist = false)
	private MachinesEntity machinesEntity;
	/**
	 * 设备名称
	 */
	@TableField(exist = false)
	private String machineName;
	/**
	 * 设备编号
	 */
	@TableField(exist = false)
	private String machineType;
	/**
	 * 设备编号
	 */
	@TableField(exist = false)
	private Integer machineStatus;

	public Integer getMachineStatus() {
		return machineStatus;
	}

	public void setMachineStatus(Integer machineStatus) {
		this.machineStatus = machineStatus;
	}

	/**
	 * 创建时间
	 */
	private Date createTime = new Date();
	/**
	 * 更新时间
	 */
	private Date lastTime;
	/**
	 * 状态 默认0 未归还， 1 已归还订单关闭
	 */
	private Integer status;
	/**
	 * 用户ID外键
	 */
	private Long uId;
	/**
	 * 用户名
	 */
	@TableField(exist = false)
	private String userName;

	public MachinesEntity getMachinesEntity() {
		return machinesEntity;
	}

	public void setMachinesEntity(MachinesEntity machinesEntity) {
		this.machinesEntity = machinesEntity;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public String getMachineType() {
		return machineType;
	}

	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 设置：主键
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：设备id外键
	 */
	public void setMachineId(Long machineId) {
		this.machineId = machineId;
	}
	/**
	 * 获取：设备id外键
	 */
	public Long getMachineId() {
		return machineId;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：更新时间
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getLastTime() {
		return lastTime;
	}
	/**
	 * 设置：状态 默认0 未归还， 1 已归还订单关闭
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态 默认0 未归还， 1 已归还订单关闭
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：用户ID外键
	 */
	public void setUId(Long uId) {
		this.uId = uId;
	}
	/**
	 * 获取：用户ID外键
	 */
	public Long getUId() {
		return uId;
	}
}
