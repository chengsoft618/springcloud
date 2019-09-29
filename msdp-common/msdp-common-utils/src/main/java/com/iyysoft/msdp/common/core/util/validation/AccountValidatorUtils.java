package com.iyysoft.msdp.common.core.util.validation;

/**
 * @author HULIN
 * @created with: msdp-parent
 * @package com.jcwj.ms.common.utils.validation
 * @description:
 * @date 2018/7/16 10:23
 * @modified By:
 * @Copyright © 2018 HAILIANG Info. Tech Ltd. All rights reserved.
 */
public class AccountValidatorUtils {

    /**
     * 正则表达式：验证电子邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证手机
     */
    public static final String REGEX_MOBILE = "^0?(13[0-9]|15[012356789]|18[0123456789]|14[57]|17[678]|170[059]|14[57]|166|19[89])[0-9]{8}$";

    /**
     * 正则表达式：验证姓名是否合法
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{2,7}$";
}
