package com.iyysoft.msdp.dp.app.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @Author: 码农
 * @Date: 2019/9/20 15:42
 */
public enum  WhetherEnum {

    /**
     * 是否 枚举类
     */
    NO("0","否"),
    YES("1","是");

    WhetherEnum(String code,String value) {
        this.code = code;
        this.value = value;
    }
    @EnumValue
    private final String code;
    private final String value;

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getCode();
    }
}
