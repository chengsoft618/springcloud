package com.iyysoft.msdp.dp.app.enums.snap;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 处罚时间
 */
public enum ReportPunishTimeEnum {

    /**
     * 举报的惩罚时间
     */
    ONEDAY(1,"24小时"),
    WEEK(7,"7天"),
    MONTH(30,"一个月"),
    FOREVER(18250,"永久"),
    ;

    ReportPunishTimeEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
    @EnumValue
    private final Integer code;
    private final String value;

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

}
