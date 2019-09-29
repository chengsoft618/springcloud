package com.iyysoft.msdp.dp.sys.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author mao.chi
 * @date 2017/10/29
 */
@Data
public class UserInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private String userId;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 电话
     */
    private String phone;
    /**
     * Mail
     */
    private String email;

    /**
     * 用户呢称
     */
    private String userNick;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 性别
     */
    private String sex;

    /**
     * 用户区域
     */
    private String areaId;
    /**
     * 头像
     */
    private String avatar;

    /**
     * 组织机构ID
     */
    private String orgId;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 组织机构名称
     */
    private String orgName;

    /**
     * 身份证
     */
    private String idno;

    /**
     * 是否实名认证
     */
    private String realStatus;

    /**
     * 证件类型
     */
    private String idnoType;

    /**
     * 模板openId
     */
    private String tempOpenId;

}
