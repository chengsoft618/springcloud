package com.iyysoft.msdp.dp.app.entity.snap;

import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 随手拍-点赞记录
 * </p>
 *
 * @author 码农
 * @since 2019-08-27
 */
@Data
@TableName("snap_like_record")
public class SnapLikeRecord implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "sid", type = IdType.UUID)
    private String sid;

    @TableField("shot_sid")
    private String shotSid;


    @TableField("nick_name")
    private String nickName;

    /**
     * 创建人
     */
    @TableField("create_id")
    private String createId;

    /**
     * 创建时间
     */
    @TableField("create_date")
    private LocalDateTime createDate;


}
