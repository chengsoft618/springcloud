package com.iyysoft.msdp.common.core.util;

import com.iyysoft.msdp.common.core.constant.enums.REnum;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Builder
@Data
@Accessors(chain = true)
public class R<T> implements Serializable {

    //数据
    private T data;

    private Boolean result;

    //返回编码
    private Integer code;

    //返回信息
    private String msg;

    public R(T data, Boolean result, int code, String msg) {
        this.result = result;
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public R(T data, REnum rCode, String... args) {
        this(data, rCode.isSuccess(), rCode.getCode(), MessageManager.getMsg(rCode, args));
    }

    public R(T data, REnum rCode) {
        this(data, rCode.isSuccess(), rCode.getCode(), MessageManager.getMsg(rCode));
    }

    public R(REnum rCode) {
        this(null, rCode);
    }

    public R(REnum rCode, String arg) {
        this(null, rCode, arg);
    }

    public R(T data) {
        this(data, REnum.CODE.OK);
    }

    public R() {
        this(REnum.CODE.OK);
    }
}