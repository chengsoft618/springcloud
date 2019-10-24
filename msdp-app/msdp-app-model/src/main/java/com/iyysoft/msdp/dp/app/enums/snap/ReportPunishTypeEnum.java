package com.iyysoft.msdp.dp.app.enums.snap;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 举报的惩罚类型
 */
public enum ReportPunishTypeEnum {
    /**
     *
     */
    DISABLE("1","封号"),
    BAN("2","禁言"),
    ;
    ReportPunishTypeEnum(String code, String value) {
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
