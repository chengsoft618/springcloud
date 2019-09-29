package com.iyysoft.msdp.dp.sys.vo;

import com.iyysoft.msdp.dp.sys.entity.SysClientServer;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xubinXie
 * @date 2019/5/6 0006
 */
@Data
@NoArgsConstructor
//@ApiModel(description = " 客户服务器推送信息")
public class ClientServerVo {
    //	@ApiModelProperty(value = "客户id")
    private String clientId;

    //	@ApiModelProperty(value = "客户名称")
    private String name;

    //	@ApiModelProperty(value = "设备信息重定向地址")
    private String redirectUrl;

    //	@ApiModelProperty(value = "设备执行回调地址")
    private String callbackUrl;

    //	@ApiModelProperty(value = "消息AES秘钥")
    private String informAesKey;

    //	@ApiModelProperty(value = "通知模式")
    private Integer informMode;

    //	@ApiModelProperty(value = "消息token")
    private String informToken;

    //	@ApiModelProperty(value = "消息推送是否启用")
    private Boolean startServer;

    public ClientServerVo(SysClientServer clientServer) {
        this.clientId = clientServer.getClientId();
        this.name = clientServer.getName();
        this.redirectUrl = clientServer.getRedirectUrl();
        this.callbackUrl = clientServer.getCallbackUrl();
        this.informAesKey = clientServer.getInformAesKey();
        this.informMode = clientServer.getInformMode();
        this.informToken = clientServer.getInformToken();
        this.startServer = clientServer.getStartServer();
    }
}
