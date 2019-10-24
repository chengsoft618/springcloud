package com.iyysoft.msdp.dp.app.entity.snap;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iyysoft.msdp.dp.app.enums.snap.ReportPunishObjectEnum;
import com.iyysoft.msdp.dp.app.enums.snap.ReportStatusEnum;
import com.iyysoft.msdp.dp.app.enums.snap.ReportTypeEnum;
import com.iyysoft.msdp.dp.app.enums.WhetherEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 随手拍-举报
 *
 * @author iyysoft code generator
 * @date 2019-08-21 13:18:55
 */
@Data
@TableName("snap_report")
public class SnapReport implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.UUID)
    private String sid;
    /**
     * 被举报的随手拍id
     */
    private String shotSid;
    /**
     * 举报原因类型
     */
    private ReportTypeEnum type;
    /**
     * 0-待处理， 1-已处理
     */
    @TableField(value = "`status`")
    private ReportStatusEnum status;

    /**
     * 举报者的昵称
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
    /**
     * 是否被忽略
     */
    @TableField(value = "is_igno")
    private WhetherEnum igno;
    /**
     * 是否删除随手拍
     */
    @TableField(value = "is_del")
    private WhetherEnum del;
    /**
     * 是否已经处罚
     */
    @TableField(value = "is_punish")
    private WhetherEnum punish;
    /**
     * 处罚的对象
     */
    @TableField(value = "punish_object")
    private ReportPunishObjectEnum punishObject;
    /**
     * 被举报人（随手拍的发布人）
     */
    @TableField(value = "pub_id")
    private String pubId;
    /**
     * 被举报人的昵称
     */
    @TableField(value = "pub_name")
    private String pubName;
    /**
     * 标签的字符串
     */
    @TableField(value = "tag")
    private String tag;








    /**
     *随手拍
     */
    @TableField(exist = false)
    private SnapShot snapShot;
    /**
     *发布会，被举报人
     */
    @TableField(exist = false)
    private SnapUser pubUser;
    /**
     *举报人
     */
    @TableField(exist = false)
    private SnapUser repUser;
    /**
     *标签列表
     */
    @TableField(exist = false)
    List<SnapShotTag> snapShotTagList;

}
