package com.iyysoft.msdp.dp.app.utils.alipay;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AliRefundInfo {
    private String outTradeNo;
    private String tradeNo;
    private BigDecimal refundAmount;
    private String refundCurrency;
    private String refundReason;
    private String outRequestNo;
    private String operatorId;
    private String storeId;
    private String terminalId;
    private AliGoodsDetail goodsDetail;
}
