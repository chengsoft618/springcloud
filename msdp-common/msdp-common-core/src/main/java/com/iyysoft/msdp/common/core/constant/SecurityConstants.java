package com.iyysoft.msdp.common.core.constant;

/**
 * @author mao.chi
 * @date 2017-12-18
 */
public interface SecurityConstants {
    /**
     * 刷新
     */
    String REFRESH_TOKEN = "refresh_token";
    /**
     * 验证码有效期
     */
    int CODE_TIME = 300;

    /**
     * 付款有效期
     */
    int PAY_TIME = 600;

    /**
     * 验证码长度
     */
    String CODE_SIZE = "4";


    /**
     * 默认密码长度
     */
    String PASSWORD_SIZE = "6";

    /**
     * 角色前缀
     */
    String ROLE = "ROLE_";
    /**
     * 前缀
     */
    String MSDP_PREFIX = "msdp_";

    /**
     * oauth 相关前缀
     */
    String OAUTH_PREFIX = "oauth:";
    /**
     * 项目的license
     */
    String IYYSOFT_LICENSE = "msdp by iyysoft";

    /**
     * 内部
     */
    String FROM_IN = "Y";

    /**
     * 标志
     */
    String FROM = "msdpfrom"; //iyysoftfrom from

    /**
     * WEB AUTH URL
     */
    String OAUTH_TOKEN_URL = "/oauth/";

    /**
     * OpenId AUTH URL
     */
    String OPENID_TOKEN_URL = "/oauth/open?d";


    /**
     * 默认的处理验证码的url前缀
     */
    String DEFAULT_REFRESH_TOKEN = "/oauth/refresh_token";

    /**
     * 默认的处理验证码的url前缀
     */
    //String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/oauth/code";

    /**
     * 当请求需要身份认证时，默认跳转的url
     */
    String DEFAULT_UNAUTHENTICATION_URL = "/oauth/require";
    /**
     * 默认的用户名密码登录请求处理url
     */
    String DEFAULT_SIGN_IN_PROCESSING_URL_FORM = "/oauth/token";

    /**
     * 默认的手机验证码登录请求处理url
     */
    String DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE = "/oauth/mobile";

    /**
     * 默认的手机密码登录请求处理url
     */
    String DEFAULT_SIGN_IN_PROCESSING_URL_LOGIN = "/oauth/login";

    /**
     * 默认的OPENID登录请求处理url
     */
    String DEFAULT_SIGN_IN_PROCESSING_URL_OPENID = "/oauth/openid";

    /**
     * 默认的OPENID登录请求处理url未绑定手机号
     */
    String DEFAULT_SIGN_IN_PROCESSING_URL_OPEN_ID = "/oauth/openId";

    /**
     * 默认的OPENID登录请求处理url
     */
    String DEFAULT_SIGN_IN_PROCESSING_URL_WXMINI = "/oauth/wxmini";

    /**
     * 获取第三方用户信息的url
     */
    String DEFAULT_THIRD_USER_INFO_URL = "/third/info";

    /**
     * oauth 客户端信息
     */
    String CLIENT_DETAILS_KEY = "msdp_oauth:client:details";

    /**
     * 微信获取OPENID
     */
    String WX_AUTHORIZATION_CODE_URL = "https://api.weixin.qq.com/sns/oauth2/access_token" +
            "?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    /**
     * {bcrypt} 加密的特征码
     */
    String BCRYPT = "{bcrypt}";
    /**
     * sys_oauth_client_details 表的字段，不包括client_id、client_secret
     */
    String CLIENT_FIELDS = "client_id, CONCAT('{noop}',client_secret) as client_secret, resource_ids, scope, "
            + "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
            + "refresh_token_validity, additional_information, autoapprove";

    /**
     * JdbcClientDetailsService 查询语句
     */
    String BASE_FIND_STATEMENT = "select " + CLIENT_FIELDS
            + " from oauth_client_info";

    /**
     * 默认的查询语句
     */
    String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by client_id";

    /**
     * 按条件client_id 查询
     */
    String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where client_id = ?";

    /**
     * 资源服务器默认bean名称
     */
    String RESOURCE_SERVER_CONFIGURER = "resourceServerConfigurerAdapter";

    /**
     * 客户端模式
     */
    String CLIENT_CREDENTIALS = "client_credentials";

    /**
     * 用户ID字段
     */
    String DETAILS_USER_ID = "user_id";

    /**
     * 用户ID字段
     */
    String DETAILS_USER_UID = "user_uid";

    /**
     * 用户名字段
     */
    String DETAILS_USERNAME = "user_name";

    /**
     * 用户手机字段
     */
    String DETAILS_MOBILE = "mobile";

    /**
     * 用户组织机构字段
     */
    //String DETAILS_ORG_ID = "org_id";
    String DETAILS_ORG_ID = "dept_id";

    /**
     * 租户ID 字段
     */
    String DETAILS_TENANT_ID = "tenant_id";

    /**
     * 用户类型
     */
    String DETAILS_ID_TYPE = "idType";

    /**
     * 用户唯一身份编号
     */
    //String DETAILS_ORG_ID = "org_id";
    String DETAILS_UID = "uid";

    /**
     * 用户所属区域
     */
    String DETAILS_AREA_ID = "areaId";


    /**
     * 协议字段
     */
    String DETAILS_LICENSE = "license";


}
