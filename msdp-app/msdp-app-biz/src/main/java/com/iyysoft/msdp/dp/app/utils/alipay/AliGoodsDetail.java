package com.iyysoft.msdp.dp.app.utils.alipay;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AliGoodsDetail {

    private String goodsId;
    private String alipayGoodsId;
    private String goodsName;
    private BigDecimal quantity;
    private BigDecimal price;
    private String goodsCategory;
    private String body;
    private String showUrl;
}
