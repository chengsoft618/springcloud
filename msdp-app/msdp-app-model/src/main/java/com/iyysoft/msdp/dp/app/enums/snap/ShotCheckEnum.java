package com.iyysoft.msdp.dp.app.enums.snap;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 随手拍-审核状态
 */
public enum ShotCheckEnum {

    /**
     * 随手拍-审核状态
     * 审核（0-待审核，5-未通过，10-通过, 15-超期未审核）
     */
    WAIT("0","待审核"),
    FAIL("5","未通过"),
    PASS("10","通过"),
    OVERDUE("15","超期未审"),
    ;
    ShotCheckEnum(String code, String value) {
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
