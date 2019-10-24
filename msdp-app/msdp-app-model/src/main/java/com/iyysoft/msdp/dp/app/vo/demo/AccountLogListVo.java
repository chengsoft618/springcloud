package com.iyysoft.msdp.dp.app.vo.demo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 帐户余额VO
 */
@Data
@ApiModel(value = "AccountLogListVo", description = "帐户明细列表")
public class AccountLogListVo {

	@ApiModelProperty(value = "记录ID", required = true, dataType = "String", example = "")
	private String logId;

	@ApiModelProperty(value = "操作发生时间", required = true, dataType = "Date", example = "2012-12-31 12:00:00")
	@JSONField (format = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime accDate;

	@ApiModelProperty(value = "操作名称", required = true, dataType = "String", example = "")
	private String accName;

	@ApiModelProperty(value = "资金的数目，正数为增加，负数为减少", required = true, dataType = "String", example = "")
	private BigDecimal accAmnt;

	@ApiModelProperty(value = "余额", required = true, dataType = "Number", example = "")
	private BigDecimal accBalance;

	@ApiModelProperty(value = "备注", required = true, dataType = "String", example = "")
	private String remarks;

	@ApiModelProperty(value = "资金进出-1出，1-进", required = true, dataType = "Integer", example = "")
	private Integer inOut;


	@ApiModelProperty(value = "操作类型，1充值 2提现 3消费 4退款  9店铺升级 51房租收入   2011平台红包收入", required = true, dataType = "Integer", example = "")
	private Integer accType;


	@ApiModelProperty(value = "状态 1到帐，-1取消，0-在途处理中 ,2拒绝提现，3转账失败", required = true, dataType = "Integer", example = "")
	private Integer accStatus;


	@ApiModelProperty(value = "审核意见", required = true, dataType = "String", example = "")
	private String auditDesc;


	@ApiModelProperty(value = "状态名称 到帐，取消，在途处理中，拒绝提现，转账失败", required = true, dataType = "String", example = "")
	private String accStatusDesc;

}

