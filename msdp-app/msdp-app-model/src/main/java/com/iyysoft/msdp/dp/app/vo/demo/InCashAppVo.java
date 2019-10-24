package com.iyysoft.msdp.dp.app.vo.demo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 可支付的频道VO
 */
@Data
@ApiModel(value = "InCashAppVo", description = "应用信息")
public class InCashAppVo {

	@ApiModelProperty(value = "应用Code", required = true, dataType = "String", example = "")
	String appCode;

	@ApiModelProperty(value = "应用名称", required = true, dataType = "String", example = "")
	String appName;

	@ApiModelProperty(value = "应用描述", required = true, dataType = "String", example = "")
	String appDesc;

	@ApiModelProperty(value = "充值金额", required = true, dataType = "Money", example = "100.00")
	BigDecimal inAmnt;

	@ApiModelProperty(value = "最大允许充值", required = true, dataType = "Money", example = "0.00")
	BigDecimal maxAmnt;

	@ApiModelProperty(value = "最小允许充值", required = true, dataType = "Money", example = "99999.00")
	BigDecimal minAmnt;

	@ApiModelProperty(value = "可用的支付频道", required = true, dataType = "List", example = "")
	List<PayChannelVo> channelList;
}

