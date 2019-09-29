package com.iyysoft.msdp.common.core.constant.enums;

/**
 * 操作返回码
 *
 * @author mao chi
 * @date 2019/5/10 0010
 */
public interface REnum {
    /**
     * 获取错误码
     */
    int getCode();

    /**
     * 操作是否成功
     */
    boolean isSuccess();

    /**
     * 全局异常
     */
    enum CODE implements REnum {

        //操作成功
        OK(0),

        //操作失败
        FAILED(1),

        //操作错误
        ERROR(2),

        //未知异常
        UNKNOWN_ERROR(3),

        //授权失败
        AUTH_FAILED(110),

        //系统传递参数错误
        PARAM_ERROR(111),

        //错误头部
        ERROR_HEAD(112),

        //身份验证失败
        VALID_FAILED(113),

        //您的请求已超时
        TIME_OUT(114),

        //请求受限
        LIMIT(115),

        //请等待
        WAIT(116),


        //输入为空
        IS_NULL(117),

        //不能为空
        IS_NOT_NULL(118),

        //未绑定
        NOT_BINDING(124),

        //验证失败
        VALIDATE_CODE_FAILED(125),

        //授权已失效
        AUTH_EXPIRES(126),

        //授权不存在
        TOKEN_IS_NOT_EXIST(127),

        //用户未注册
        USER_NOT_REGISTER(130),

        //该用户不存在
        USER_IS_NOT_EXIST(131),

        //用户已存在
        USER_IS_EXIST(132),

        //用户未绑定
        USER_NOT_BINDING(133),

        //用户禁止登录
        USER_IS_DISABLED(134),

        //用户信息不存在
        NOT_USER_INFO(135),

        //用户未登录
        NOT_LOGIN(136),

        //您无权对此进行操作
        INVALID_PERMISSIONS(137),

        //用户名或密码错误
        USER_PASSWORD_ERROR(138),

        //登录失败
        LOGIN_FAILED(139),


        //密码错误，请再仔细想想
        PASSWORD_ERROR(140),

        //请修改您的初始密码
        MOD_INIT_PASSWORD(141),

        //您的密码已经过期
        PASSWORD_EXPIRED(142),

        //两次密码不相等
        PASSWORD_IS_UNEQUAL(143),


        //手机号已经存在
        MOBILE_IS_EXIST(150),

        //手机号不存在
        MOBILE_NOT_EXIST(151),

        //手机号未注册
        MOBILE_NOT_REGISTER(152),

        //手机号错误
        MOBILE_ERROR(153),


        //邮箱已存在
        MAIL_IS_EXIST(160),
        //邮箱不存在
        MAIL_NOT_EXIST(161),
        //邮箱未注册
        MAIL_NOT_REGISTER(162),


        //短信发送失败
        SMS_FAILED(171),
        //发送超过限制数请5分钟后再试
        SMS_LIMIT(172),
        //验证码错误
        VERIFY_CODE_ERROR(173),
        //请检查当前时间
        TIMESTAMP_ERROR(174),
        //签名无效
        SIGN_INVALID(175),
        //远程调用失败
        FEIGN_FAILED(176),
        //远程调用失败
        HYSTRIX_FAILED(177),

        //微信APPID错误
        WEXIN_APPID_FAILED(181),
        //微信服务器错误
        WEXIN_SERVICE_FAILED(182),

        //支付宝调用失败
        ALIPAY_APPID_FAILED(185),
        //支付宝服务器错误
        ALIPAY_SERVICE_FAILED(186),

        //强制用打开URL显示
        SYSTEM_FORCIBLY_OPEN_URL(901),

        //强制用提交URL
        SYSTEM_FORCIBLY_SAVE_URL(902),

        //强制用户更新
        SYSTEM_FORCIBLY_UPDATE(903),

        //网络服务器异常
        NETWORK_ERROR(994),

        //服务器异常
        SYSTEM_ERROR(995),

        //请求数据，数据绑定失败，请检查请求数据格式
        BIND_ERROR(996),

        //请求数据，JSON解析失败
        JSON_ERROR(997),

        //强制退出，并清空数据
        SYSTEM_FORCIBLY_OUT(998),

        //其他错误
        OTHER_ERROR(999);

        private int code;

        CODE(int code) {
            this.code = code;
        }

        public int getCode() {
            return this.code;
        }

        @Override
        public boolean isSuccess() {
            return this.code == 0;
        }
    }
}