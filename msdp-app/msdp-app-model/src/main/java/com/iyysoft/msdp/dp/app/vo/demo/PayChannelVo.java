package com.iyysoft.msdp.dp.app.vo.demo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 可支付的频道VO
 */
@Data
@ApiModel(value = "InCashChannelVo", description = "支付信息")
@AllArgsConstructor
public class PayChannelVo {

	@ApiModelProperty(value = "支付频道CODE", required = true, dataType = "String", example = "")
	String payChannel;

	@ApiModelProperty(value = "支付频道名称", required = true, dataType = "String", example = "")
	String channelName;

}

