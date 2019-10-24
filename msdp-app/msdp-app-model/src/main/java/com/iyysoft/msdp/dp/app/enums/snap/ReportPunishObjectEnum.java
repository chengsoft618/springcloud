package com.iyysoft.msdp.dp.app.enums.snap;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 举报的惩罚对象
 */
public enum ReportPunishObjectEnum {

    /**
     * 举报人
     */
    PLAINTIFF("0","举报人"),
    /**
     * 被举报人(随手拍的发布人)
     */
    ACCUSED("1","被举报人");

    ReportPunishObjectEnum(String code, String value) {
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
