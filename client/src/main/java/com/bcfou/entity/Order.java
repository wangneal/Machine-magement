package com.bcfou.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 编程否
 * @Date: 2018/8/26 9:23
 */
@Entity
@Table(name = "tb_order")
@Data
@DynamicUpdate
public class Order implements Serializable {
    private static final long serialVersionUID = -2792637140511461820L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 设备id外键
     */
    private Long machineId;
    /**
     * 设备信息
     */
    @Transient
    private Machine machines;
    /**
     * 设备名称
     */
    @Transient
    private String machineName;
    /**
     * 设备编号
     */
    @Transient
    private String machineType;
    /**
     * 设备编号
     */
    @Transient
    private Integer machineStatus;

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
    private Integer status = 0;
    /**
     * 用户ID外键
     */
    private Long uId;
    /**
     * 用户名
     */
    @Transient
    private String userName;
}
