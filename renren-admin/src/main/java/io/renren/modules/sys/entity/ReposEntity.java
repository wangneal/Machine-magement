package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 仓位管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-08-25 19:57:30
 */
@TableName("tb_repos")
public class ReposEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long reposId;
	/**
	 * 仓位名称，如：A1, B22等
	 */
	private String name;
	/**
	 * 父节点记录 0 代表最高节点，
	 */
	private Long parentId;
	//上级仓库名称
	@TableField(exist=false)
	private String parentName;
	@TableField(exist=false)
	private Boolean open;
	@TableField(exist=false)
	private List<?> list;

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    /**
	 * 设置：主键
	 */
	public void setReposId(Long reposId) {
		this.reposId = reposId;
	}
	/**
	 * 获取：主键
	 */
	public Long getReposId() {
		return reposId;
	}
	/**
	 * 设置：仓位名称，如：A1, B22等
	 */
	public void setName(String reposName) {
		this.name = reposName;
	}
	/**
	 * 获取：仓位名称，如：A1, B22等
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：仓库编号, 如：A区， B区等
	 */
	public void setParentId(Long partentId) {
		this.parentId = partentId;
	}
	/**
	 * 获取：仓库编号, 如：A区， B区等
	 */
	public Long getParentId() {
		return parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}
