package com.iyysoft.msdp.dp.app.entity.snap;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iyysoft.msdp.dp.app.enums.snap.ShotTagEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 随手拍-发布的随手拍-标签
 *
 * @author iyysoft code generator
 * @date 2019-08-22 14:15:40
 */
@Data
@TableName("snap_shot_tag")
public class SnapShotTag implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.UUID)
    private String sid;
    /**
     * 随手拍id
     */
    private String shotSid;
    /**
     * 标签类型
     */
    private ShotTagEnum tag;
    /**
     * 创建人
     */
    private String createId;
    /**
     * 创建时间
     */
    private LocalDateTime createDate;

}
