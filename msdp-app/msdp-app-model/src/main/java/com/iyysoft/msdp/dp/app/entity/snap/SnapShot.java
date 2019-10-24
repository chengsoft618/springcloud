package com.iyysoft.msdp.dp.app.entity.snap;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.iyysoft.msdp.dp.app.enums.snap.ShotCheckEnum;
import com.iyysoft.msdp.dp.app.enums.snap.ShotStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 随手拍-发布的随手拍
 *
 * @author iyysoft code generator
 * @date 2019-08-21 13:18:48
 */
@Data
@TableName("snap_shot")
public class SnapShot implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.UUID)
    private String sid;

    /**
     * 描述
     */
    @TableField(value="content")
    private String content;

    /**
     * 图片张数
     */
    private Integer medianum;
    /**
     * 点赞数
     */
    private Integer likenum;
    /**
     * 点赞数假的
     */
    private Integer likenumf;

    /**
     * 评论数量
     */
    private Integer commnum;

    /**
     * 地点
     */
    private String address;

    /**
     * 经度
     */
    private BigDecimal lng;

    /**
     * 纬度
     */
    private BigDecimal lat;

    /**
     * 审核（0-待审核，5-未通过，10-通过, ）
     */
    @TableField(value = "`check`")
    private ShotCheckEnum check;

    /**
     * 状态(0-显示，1-隐藏)
     */
    @TableField(value = "`status`")
    private ShotStatusEnum status;

    /**
     * 发布人的随手拍账号的昵称
     */
    private String nickName;

    /**
     * 标签的字符串
     */
    @TableField(value = "tag")
    private String tag;

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
    @Version
    private Integer version;

    /**
     * 排序
     */
    private Integer orders;


    //发布人的信息
    @TableField(exist = false)
    private SnapUser snapUser;
    //媒体资源
    @TableField(exist = false)
    private List<SnapMedia> snapMediaList;
    @TableField(exist = false)
    private SnapLikeRecord snapLikeRecord;


}
