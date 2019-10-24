package com.iyysoft.msdp.dp.app.entity.snap;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;

import java.io.Serializable;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 随手拍-媒体
 *
 * @author iyysoft code generator
 * @date 2019-08-21 13:59:49
 */
@Data
@TableName("snap_media")
public class SnapMedia implements Serializable {

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
     * 资源路径
     */
    private String url;

    private String hdUrl;
    /**
     * 创建人
     */
    private String createId;
    /**
     * 创建时间
     */
    private LocalDateTime createDate;
    /**
     * 排序
     */
    private Integer orders;


}
