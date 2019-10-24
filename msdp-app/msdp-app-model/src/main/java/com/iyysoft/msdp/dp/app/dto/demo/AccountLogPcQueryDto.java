package com.iyysoft.msdp.dp.app.dto.demo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@ApiModel("账单查询dto")
@Data
public class AccountLogPcQueryDto {
	
	@ApiParam ("1充值 2提现 3消费 4退款  9店铺升级 51房租收入   2011平台红包收入")
	private Integer accType;
	
	@ApiModelProperty (value = "账单时间-开始",example = "2019-05-01")
	private String accDateBegin;
	
	@ApiModelProperty (value = "账单时间-结束",example = "2019-05-01")
	private String accDateEnd;
	
	
}
