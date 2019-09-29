package com.iyysoft.msdp.dp.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.iyysoft.msdp.dp.sys.dto.UserIdInfoDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户证件信息
 *
 * @author 谢旭斌
 * @version 2.0
 * @date 2019-06-10 20:46:15
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = " 用户证件信息")
@TableName(value = "sys_user_idinfo")
public class SysUserIdInfo extends Model<SysUserIdInfo> {
    @TableId(type = IdType.UUID, value = "user_idinfo_id")
    @ApiModelProperty(value = "用户证件信息id")
    private String userIdInfoId;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "客人档案号")
    private String idSno;

    @ApiModelProperty(value = "证件类型")
    private String idType;

    @ApiModelProperty(value = "证件号码")
    private String idno;

    @ApiModelProperty(value = "证件起始日")
    private LocalDateTime idDate;

    @ApiModelProperty(value = "证件有效期")
    private LocalDateTime idExp;

    @ApiModelProperty(value = "住址")
    private String address;

    @ApiModelProperty(value = "签发机关")
    private String ctfAddr;

    @ApiModelProperty(value = "签证种类")
    private String visaType;

    @ApiModelProperty(value = "签证号码")
    private String visaNo;

    @ApiModelProperty(value = "签证机关")
    private String visaAdd;

    @ApiModelProperty(value = "签证有效期")
    private LocalDateTime visaExpire;

    @ApiModelProperty(value = "入境日期")
    private LocalDateTime visaInDate;

    @ApiModelProperty(value = "入境口岸")
    private String visaInPort;

    @ApiModelProperty(value = "首页图片名称")
    private String photoCode;

    @ApiModelProperty(value = "头像图片名称")
    private String headCode;

    @ApiModelProperty(value = "页面名称")
    private String picCode;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateDate;

    @ApiModelProperty(value = "更新人")
    private String updateId;

    public SysUserIdInfo(String userId, UserIdInfoDto userIdInfoDto) {
        this.userId = userId;
        this.idSno = "1";
        this.idType = userIdInfoDto.getIdType();
        this.idno = userIdInfoDto.getIdNo();
        this.photoCode = userIdInfoDto.getUrlA();
        this.picCode = userIdInfoDto.getUrlB();
        this.createId = userIdInfoDto.getUpdateId();
        this.createDate = LocalDateTime.now();
    }
}