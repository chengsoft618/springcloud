package com.iyysoft.msdp.dp.app.entity.snap;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iyysoft.msdp.dp.app.enums.snap.UserStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 随手拍-用户
 *
 * @author iyysoft code generator
 * @date 2019-08-21 13:18:42
 */
@Data
@TableName("snap_user")
public class SnapUser implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.UUID)
    private String sid;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 状态（0-正常，1-封号，2-禁言）
     */
    @TableField(value = "`status`")
    private UserStatusEnum status;
    /**
     * 禁言的解放时间
     */
    private LocalDateTime freeTime;
    /**
     * 创建人
     */
    private String createId;
    /**
     * 创建时间
     */
    private LocalDateTime createDate;
    /**
     * 更新人
     */
    private String updateId;
    /**
     * 更新时间
     */
    private LocalDateTime updateDate;
    /**
     * 版本号
     */
    private Integer version;
    /**
     * 头像
     */
    private String head;
    /**
     * 手机
     */
    private String phone;
    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String realName;
    /**
     * 实名照片
     */
    @TableField("real_photo")
    private String realPhoto;


}
