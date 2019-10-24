package com.iyysoft.msdp.dp.app.enums.snap;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 随手拍-显示状态
 */
public enum ShotStatusEnum {

    /**
     * 随手拍-显示状态
     */
    HIDE("0","隐藏"),
    SHOW("1","显示"),
    ;
    ShotStatusEnum(String code, String value) {
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
