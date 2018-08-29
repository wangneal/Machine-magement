package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 设备管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-08-25 19:57:30
 */
@TableName("tb_machines")
public class MachinesEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long machinesId;
	/**
	 * 设备名称
	 */
	private String machinesName;
	/**
	 * 设备编号
	 */
	private String machinesType;
	/**
	 * 设备状态 默认0正常，1待维修，2已报废
	 */
	private Integer machinesStatus;
	/**
	 * 设备分类 默认打印机
	 */
	private String machinesCategory;
	/**
	 * 设备所在仓位
	 */
	private Long repoId;
	/**
	 * 获取仓库列表
	 */
	@TableField(exist = false)
	private List<ReposEntity> reposEntityList;
	/**
	 * 仓位名称
	 */
	@TableField(exist=false)
	private String repoName;
	@TableField(exist = false)
	private List<Long> ids;
	public List<ReposEntity> getReposEntityList() {
		return reposEntityList;
	}

	public void setReposEntityList(List<ReposEntity> reposEntityList) {
		this.reposEntityList = reposEntityList;
	}

	/**
	 * 获取：仓位名称
	 */
	public String getRepoName() {
		return repoName;
	}
	/**
	 * 设置：仓位名称
	 */
	public void setRepoName(String repoName) {
		this.repoName = repoName;
	}

	/**
	 * 设置：主键
	 */
	public void setMachinesId(Long machinesId) {
		this.machinesId = machinesId;
	}
	/**
	 * 获取：主键
	 */
	public Long getMachinesId() {
		return machinesId;
	}
	/**
	 * 设置：设备名称
	 */
	public void setMachinesName(String machinesName) {
		this.machinesName = machinesName;
	}
	/**
	 * 获取：设备名称
	 */
	public String getMachinesName() {
		return machinesName;
	}
	/**
	 * 设置：设备编号
	 */
	public void setMachinesType(String machinesType) {
		this.machinesType = machinesType;
	}
	/**
	 * 获取：设备编号
	 */
	public String getMachinesType() {
		return machinesType;
	}
	/**
	 * 设置：设备状态 默认0正常，1待维修，2已报废
	 */
	public void setMachinesStatus(Integer machinesStatus) {
		this.machinesStatus = machinesStatus;
	}
	/**
	 * 获取：设备状态 默认0正常，1待维修，2已报废
	 */
	public Integer getMachinesStatus() {
		return machinesStatus;
	}
	/**
	 * 设置：设备分类 默认打印机
	 */
	public void setMachinesCategory(String machinesCategory) {
		this.machinesCategory = machinesCategory;
	}
	/**
	 * 获取：设备分类 默认打印机
	 */
	public String getMachinesCategory() {
		return machinesCategory;
	}
	/**
	 * 设置：设备所在仓位
	 */
	public void setRepoId(Long repoId) {
		this.repoId = repoId;
	}
	/**
	 * 获取：设备所在仓位
	 */
	public Long getRepoId() {
		return repoId;
	}
}
