package com.iyysoft.msdp.dp.app.enums;

import com.iyysoft.msdp.common.core.constant.enums.REnum;

/**
 * 错误返回信息
 * @author iyysoft code generator
 * @date 2019/9/5 10:18
 */
public enum RAppREnum implements REnum {

    //错误标兵
    ERROR_HEAD(1299),
    /**
     * 用户已禁言
     */
    BANNED(1300),
    /**
     * 封号
     */
    DISABLE(1301),
    /**
     * 处罚已撤销
     */
    PUNISH_CANCEL(1302),
    /**
     * 该昵称已被占用！
     */
    NICKNAME_REPEAT(1303),
    /**
     * 24小时内，不可以重复举报
     */
    REPORT_REPEAT(1304),
    /**
     * 该随手拍已被删除，请刷新列表
     */
    SHOT_DELETED(1305),
    ;

    private int code;

    RAppREnum(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public boolean isSuccess() {
        return code < ERROR_HEAD.code;
    }
}
