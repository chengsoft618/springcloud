package com.iyysoft.msdp.dp.app.dto.demo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 账单Dto
 */
@Data
@ApiModel("充值DTO")
public class InCashDto implements Serializable {


    @ApiModelProperty(value="充值金额",required=true, dataType="Money", example="100.00")
    private BigDecimal payAmnt;

    @ApiModelProperty(value="充值频道",required=true, dataType="String", example="jcmini")
    private String payChannel;
    //  h5                                                                                                                            (H5微信外部浏览器支付)
    // app_buyer 买家  app_seller 厂家                                                                                                (APP端支付)
    // jsapi_buyer_h5 H5商城买家 jsapi_seller_h5 H5商城厂家    jsapi_buyer_mini小程序商城买家   jsapi_seller_mini 小程序商城厂家      (公众号内支付)
    // wenative                                                                                                                       (扫码支付)

}
