package com.iyysoft.msdp.dp.app.enums.snap;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 举报处理结果
 */
public enum ReportResultEnum {

    /**
     * 举报处理结果
     */
    DELETED("0","已删除"),
    IGNORE("5","已忽视"),
    DISABLE("10","封号"),
    BAN("15","禁言"),
    ;
    ReportResultEnum(String code, String value) {
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
