package com.iyysoft.msdp.dp.sys.enums;

/**
 * 证件类型枚举
 * 2019/6/11 0011
 *
 * @author xubinXie
 */
public enum IdTypeEnum {

    //身份证
    ID_CARD("01"),

    //护照
    PASSPORT("02");

    private String type;

    IdTypeEnum(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }
}
