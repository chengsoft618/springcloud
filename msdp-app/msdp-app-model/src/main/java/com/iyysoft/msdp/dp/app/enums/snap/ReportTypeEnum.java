package com.iyysoft.msdp.dp.app.enums.snap;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 举报原因类型
 */
public enum ReportTypeEnum {

    /**
     * 举报原因类型
     */
    RUBBISH("0","垃圾广告营销"),
    CURSE("1","言语辱骂、骚扰、不友善"),
    SEXY("2","淫秽色情信息"),
    ILLEGAL("3","违法有害信息"),
    OTHER("4","其他"),


    ;
    ReportTypeEnum(String code, String value) {
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
