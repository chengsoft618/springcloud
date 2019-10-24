package com.iyysoft.msdp.dp.app.entity.snap;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 随手拍-评论
 *
 * @author iyysoft code generator
 * @date 2019-08-21 13:18:52
 */
@Data
@TableName("snap_comment")
public class SnapComment implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.UUID)
    private String sid;

    /**
     * 随手拍id,shot_snap的id
     */
    private String shotSid;
    /**
     * 描述
     */
    @TableField(value = "`comment`")
    private String comment;
    /**
     * 描述
     */
    private String nickName;

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

/*    @TableField(exist = false)
    private JtgUserInfo userInfo;*/
    @TableField(exist = false)
    private SnapUser snapUser;

}
