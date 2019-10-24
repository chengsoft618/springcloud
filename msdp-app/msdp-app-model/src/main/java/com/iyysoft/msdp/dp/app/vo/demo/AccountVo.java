package com.iyysoft.msdp.dp.app.vo.demo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 帐户余额VO
 */
@Data
@ApiModel(value = "AccountVo", description = "安装端-添加房源-楼幢列表")
public class AccountVo {

	@ApiModelProperty(value = "总余额", required = true, dataType = "Money", example = "100.00")
	BigDecimal totAmnt;

	@ApiModelProperty(value = "当前余额", required = true, dataType = "Money", example = "100.00")
	BigDecimal accAmnt;

	@ApiModelProperty(value = "冻结余额-提现时冻结", required = true, dataType = "Money", example = "100.00")
	BigDecimal accFrozen;

	@ApiModelProperty(value = "在途资金", required = true, dataType = "Money", example = "100.00")
	BigDecimal accOnway;

	@ApiModelProperty(value = "押金余额", required = true, dataType = "Money", example = "100.00")
	BigDecimal accBond;

//
//	@ApiModelProperty(value = "等级积分", required = true, dataType = "Number", example = "100")
//	Integer rankPoints;
//
//	@ApiModelProperty(value = "消费积分", required = true, dataType = "Number", example = "100")
//	Integer payPoints;
//
//	@ApiModelProperty(value = "红包积分", required = true, dataType = "Number", example = "0")
//	Integer redPacketPoints;
}

