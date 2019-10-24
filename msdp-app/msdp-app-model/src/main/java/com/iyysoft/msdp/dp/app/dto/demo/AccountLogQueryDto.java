package com.iyysoft.msdp.dp.app.dto.demo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@ApiModel("账单查询dto")
@Data
public class AccountLogQueryDto {
	
	@ApiParam ("1充值 2提现 3消费 4退款  9店铺升级 51房租收入   2011平台红包收入")
	private Integer accType;
	
	@ApiModelProperty (value = "账单时间",example = "2019-05")
	@NotEmpty
	private String accDate;
	
	@ApiModelProperty (hidden = true)
	private LocalDateTime accDateBegin;
	
	@ApiModelProperty (hidden = true)
	private LocalDateTime accDateEnd;
	
}
