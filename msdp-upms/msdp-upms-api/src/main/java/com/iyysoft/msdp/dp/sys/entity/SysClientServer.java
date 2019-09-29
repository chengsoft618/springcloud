package com.iyysoft.msdp.dp.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 客户服务器推送信息
 *
 * @author xiexubin
 * @version 2.0
 * @date 2019-05-06 15:00:29
 */
@Data
@EqualsAndHashCode(callSuper = true)
//@ApiModel(description = " 客户服务器推送信息")
public class SysClientServer extends Model<SysDept> {
    //@ApiModelProperty(value = "客户服务id")
    @TableId
    private String clientId;

    //@ApiModelProperty(value = "客户名称")
    private String name;

    //@ApiModelProperty(value = "设备信息重定向地址")
    private String redirectUrl;

    //ApiModelProperty(value = "设备执行回调地址")
    private String callbackUrl;

    //@ApiModelProperty(value = "消息AES秘钥")
    private String informAesKey;

    //@ApiModelProperty(value = "通知模式")
    private Integer informMode;

    //@ApiModelProperty(value = "消息token")
    private String informToken;

    //@ApiModelProperty(value = "消息推送是否启用")
    private Boolean startServer;
}