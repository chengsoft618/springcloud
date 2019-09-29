package com.iyysoft.msdp.dp.sys.dto;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author mao.chi
 * @date 2017/11/11
 */
@Data
public class UserInfoDto implements Serializable {
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
     * 0-正常，1-删除
     */
    @TableLogic
    private String delFlag;

    /**
     * 锁定标记
     */
    private String lockFlag;
    /**
     * 手机
     */
    private String mobile;

    /**
     * 电话
     */
    private String phone;

    /**
     * Email
     */
    private String email;

    /**
     * 用户呢称
     */
    private String userNick;

    /**
     * 生日
     */
    private LocalDate birthday;

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
}
