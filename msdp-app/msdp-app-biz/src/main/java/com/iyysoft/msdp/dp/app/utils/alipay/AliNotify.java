package com.iyysoft.msdp.dp.app.utils.alipay;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AliNotify {

    private Date gmt_create;    //2017-12-09 15:04:56 支付创建时间
    private String charset;     //utf-8 编码方式
    private Date gmt_payment;   //2017-12-09 15:04:58 支付时间
    private String seller_email;//payment@dfcapp.com 商家支付宝帐号
    private Date notify_time;   //2018-03-12 15:05:01 回调时间
    private String subject;     //大风车商城 支付主题
    private String sign;        //eocUgTi0TSwwVxymSMalybVg28d47h3hq+gB3GWKswoqXxiPd3FlaaJ0zoDFmrRQJvDbnhvr12nkZAntBUflcUNB8+iq7ezB6loLwcTKH//KdA/IV0sYki4504JIeDZphJ2RzbH1hoZYDw2TLbHo2Ahtcm5i+16AGEQzVmebOl4KkTKF5cAnwkPwHHZDYE76m0FZ7CuqoDiXyju2mC3mcLPmQ0SM6bSbdskfYjfO4kDhj9SGMbpVm2hmqrh91NpnfnTr8LdN9XdxSNbyGTc/yYE3PlUulvoBu0+DMRYrU/2Pj6hHTGEF5EzIstAklVSeHfd0ExGk39wHCo3w4o34SQ==
    private String body;        //升级为潮流馆 支付描述
    private String buyer_id;    //2088002505435091 卖家支付宝帐号ID
    private String version;     //1.0 版本号
    private String notify_id;   //bf6afd24de61347c7a57a7d91f84c95gp5 回调ID
    private String notify_type; //trade_status_sync 回调类型
    private String out_trade_no;    //17120932134863510009 提交的订单号
    private BigDecimal total_amount;    //1.00 总金额
    private String trade_status;    //TRADE_FINISHED TRADE_SUCCESS 订单状态
    private BigDecimal refund_fee;  //0.00 退款金额
    private String trade_no;    //2017120921001004090530872290 支付宝订单号
    private String auth_app_id; //2017022705930080 授权APPID
    private Date gmt_close;     //2018-03-12 15:05:01 订单关闭时间
    private String buyer_logon_id;  //695***@qq.com 卖家支付宝帐号
    private String app_id;      //2017022705930080 商家APPID
    private String sign_type;   //RSA2 签名方式
    private String seller_id;   //2088521066906221 商家ID
    private BigDecimal invoice_amount;     //0.01 开发票金额
    private String fund_bill_list;     //用户支付方式 [{"amount":"0.01","fundChannel":"ALIPAYACCOUNT"}]
    private BigDecimal receipt_amount;     //0.01 用户应付金额
    private BigDecimal buyer_pay_amount;   //0.01 用户实际支付金额
    private BigDecimal point_amount;       //0.00 税费金额
}
