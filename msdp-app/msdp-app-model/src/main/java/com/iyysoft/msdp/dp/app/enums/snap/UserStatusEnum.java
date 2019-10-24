package com.iyysoft.msdp.dp.app.enums.snap;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 随手拍-用户状态
 */
public enum UserStatusEnum {

    /**
     * 随手拍-用户状态
     */
    NORMAL("0","正常"),
    DISABLE("1","封号"),
    BAN("2","禁言"),
    ;
    UserStatusEnum(String code, String value) {
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
