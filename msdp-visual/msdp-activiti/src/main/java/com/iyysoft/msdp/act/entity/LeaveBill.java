package com.iyysoft.msdp.act.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 请假流程
 * @TableField(exist = false)：表示该属性不为数据库表字段，但又是必须使用的。
 * @TableField(exist = true)：表示该属性为数据库表字段。
 *
 * @TableName：数据库表相关
 *
 * @TableId：表主键标识
 * @TableField：表字段标识
 * @TableLogic：表字段逻辑处理注解（逻辑删除）
 *
 * @author mao.chi
 * @date 2018-09-27 15:20:44
 */
@Data
@TableName("ms_app.act_leave_bill")
@EqualsAndHashCode(callSuper = true)
public class LeaveBill extends Model<LeaveBill> {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer leaveId;
    /**
     * 申请人
     */
    private String username;
    /**
     * 天数
     */
    private Integer days;
    /**
     * 备注
     */
    private String content;
    /**
     * 0-未提交,1-未审核,2-批准,9-驳回
     */
    private String state;
    /**
     * 提交时间
     */
    private LocalDateTime leaveTime;
    /**
     * 提交时间
     */
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
    /**
     * 删除标志
     */
    @TableLogic
    private String delFlag;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.leaveId;
    }
}
