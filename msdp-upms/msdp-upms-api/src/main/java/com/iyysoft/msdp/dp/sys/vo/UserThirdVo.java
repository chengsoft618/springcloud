package com.iyysoft.msdp.dp.sys.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author mao.chi
 * @date 2017/10/29
 */
@Data
public class UserThirdVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 第三方类型
     */
    private String thirdType;
    /**
     * 第三方ID
     */
    private String thirdId;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 验证码
     */
    private String code;


}
