package com.iyysoft.msdp.dp.app.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PayProductInfo {
    private String tradeNo;

    private BigDecimal totalAmnt;

    private String productName;

    private String productDesc;

    private LocalDateTime createTime;

    private LocalDateTime expireTime;

    private Integer outTime;

    private String notifyUrl;

    private String ip;
}
