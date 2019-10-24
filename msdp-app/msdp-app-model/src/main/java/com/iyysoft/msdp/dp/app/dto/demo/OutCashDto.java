package com.iyysoft.msdp.dp.app.dto.demo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 账单Dto
 */
@Data
@ApiModel(value="提现DTO",description="")
@Getter
@Setter
public class OutCashDto implements Serializable {

    @ApiModelProperty(value="提现金额",required=true, dataType="String", example="100.00")
    private BigDecimal amnt;    //必选,提现金额

    @ApiModelProperty(value="提现方式1-提现到支付宝 2-提现到微信 3-提现到银行卡 4-汇款  5支票",required=true, dataType="Number", example="")
    private String payChannel;   //必选,提现方式1-提现到支付宝 2-提现到微信 3-提现到银行卡 4-汇款  5支票

    @ApiModelProperty(value="户名",required=true, dataType="String", example="张三")
    private String accName;    //必选,户名

    @ApiModelProperty(value="开户银行 见银行字典",required=false, dataType="String", example="CBC")
    private String bankName;    //可选，type=3 是必选 开户银行 见银行字典

    @ApiModelProperty(value="开户行",required=false, dataType="String", example="中国工商银杭州西溪支付")
    private String bankBranch;    //可选，type=3 是必选 开户行

    @ApiModelProperty(value="提现账号",required=true, dataType="String", example="620112312132221339")
    private String bankNo;       //必选,提现账号

//    @ApiModelProperty(value="原因",required=false, dataType="String", example="")
//    private String accReason;       //原因

    @ApiModelProperty(value="备注",required=false, dataType="String", example="提现")
    private String remarks;       //备注

    @ApiModelProperty(value="短信验证码",required=false, dataType="String", example="和支付密码必选一个")
    private String smsCode; //短信验证码

    @ApiModelProperty(value="支付密码",required=false, dataType="String", example="和短信验证码必选一个")
    private String password; //短信验证码

}
