package com.iyysoft.msdp.dp.app.enums.snap;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 随手拍标签
 */
public enum ShotTagEnum {

    /**
     * 随手拍标签
     */
    BOARD("1","#项目公告板"),
    EXPOSURE("2","#我要曝光"),
    GIRLS("3","#看美女"),
    FUNNY("4","#工地趣事"),
    JOKE("5","#笑话段子"),
    EMP("6","#招工找活"),
    PROBLEM("7","#APP问题"),
    LIFE("8","#生活小记"),
    ;
    ShotTagEnum(String code, String value) {
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
