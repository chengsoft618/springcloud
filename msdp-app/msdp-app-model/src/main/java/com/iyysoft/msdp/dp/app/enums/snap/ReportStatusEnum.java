package com.iyysoft.msdp.dp.app.enums.snap;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 举报处理状态
 */
public enum ReportStatusEnum {

    /**
     * 举报处理状态
     */
    WAIT("0","待处理"),
    DONE("1","已处理"),

    ;
    ReportStatusEnum(String code, String value) {
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
