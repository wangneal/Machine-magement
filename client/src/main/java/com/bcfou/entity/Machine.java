package com.bcfou.entity;


import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @Author: 编程否
 * @Date: 2018/8/26 9:24
 */
@Entity
@Table(name = "tb_machines")
@Data
public class Machine implements Serializable {
    private static final long serialVersionUID = -3360581618575785155L;
    @Id
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
     * 仓位名称
     */
    @Transient
    private String repoName;
}
