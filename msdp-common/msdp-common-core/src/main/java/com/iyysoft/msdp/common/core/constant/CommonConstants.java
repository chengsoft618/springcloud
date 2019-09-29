package com.iyysoft.msdp.common.core.constant;

/**
 * @author mao.chi
 * @date 2019/08/25
 */
public interface CommonConstants {
    /**
     * header 中租户ID
     */
    String TENANT_ID = "TenantId";
    /**
     * 删除
     */
    String STATUS_DEL = "1";
    /**
     * 正常
     */
    String STATUS_NORMAL = "0";

    /**
     * 锁定
     */
    String STATUS_LOCK = "9";

    /**
     * 菜单
     */
    String MENU = "0";

    /**
     * 编码
     */
    String UTF8 = "UTF-8";

    /**
     * 前端工程名
     */
    String FRONT_END_PROJECT = "msdp-ui";

    /**
     * 后端工程名
     */
    String BACK_END_PROJECT = "msdp";

    /**
     * 路由存放
     */
    String ROUTE_KEY = "gateway_route";

    /**
     * spring boot admin 事件key
     */
    String EVENT_KEY = "event_key";

    /**
     * 验证码前缀
     */
    String DEFAULT_CODE_KEY = "DEFAULT_CODE_KEY_";

    /**
     * 成功标记
     */
    Integer SUCCESS = 0;
    /**
     * 失败标记
     */
    Integer FAIL = 1;

    /**
     * 默认存储bucket
     * msdp.default0/组织机构ID/模块名称/年/月日
     */
    String BUCKET_NAME = "msdp.default";
}
