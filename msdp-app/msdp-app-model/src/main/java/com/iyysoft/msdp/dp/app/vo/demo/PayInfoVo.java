package com.iyysoft.msdp.dp.app.vo.demo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 支付信息VO
 */
@Data
@ApiModel(value = "PayInfoVo", description = "支付信息VO")
public class PayInfoVo {

	@ApiModelProperty(value = "支付ID", required = true, dataType = "String", example = "")
	String payId;

	@ApiModelProperty(value = "支付单号", required = true, dataType = "String", example = "")
	String tradeNo;

	@ApiModelProperty(value = "支付频道", required = true, dataType = "String", example = "")
	String payChannel;

	@ApiModelProperty(value = "支付信息", required = true, dataType = "String", example = "")
	String payInfo;

}

