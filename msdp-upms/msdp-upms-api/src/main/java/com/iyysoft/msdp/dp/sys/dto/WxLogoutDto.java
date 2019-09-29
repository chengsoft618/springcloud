package com.iyysoft.msdp.dp.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p class="detail">
 * 功能: 退出微信登录
 * </p>
 *
 * @author cm
 * @ClassName Winxin logout dto.
 * @Version V1.0.
 * @date 2019.05.17 18:11:19
 */
@Data
@ApiModel(value = "WxLogoutDto", description = "退出微信认证")
public class WxLogoutDto {

    @NotBlank(message = "第三方类型不能为空")
    @ApiModelProperty(value = "第三方类型 1-微信登录 2-支付宝登录 101-公众服务号 102-订阅号 104-小程序 105小程序", required = true, dataType = "String", example = "1-微信登录")
    private String thirdId;

    @NotBlank(message = "第三方ID不能为空")
    @ApiModelProperty(value = "第三方ID", required = true, dataType = "String", example = "oMsIv9XlyZmp9_gCpsrFCtaB1YGg")
    private String openId;

    /**
     * Instantiates a new Wx logout dto.
     */
    public WxLogoutDto() {
    }
}
