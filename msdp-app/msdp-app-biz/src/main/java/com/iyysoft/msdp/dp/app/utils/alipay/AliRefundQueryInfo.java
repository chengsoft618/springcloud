package com.iyysoft.msdp.dp.app.utils.alipay;

import lombok.Data;

@Data
public class AliRefundQueryInfo {
    private String outTradeNo;
    private String tradeNo;
    private String orgPid;
    private String outRequestNo;
}
