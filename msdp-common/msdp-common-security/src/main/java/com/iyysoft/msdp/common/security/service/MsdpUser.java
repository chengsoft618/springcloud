package com.iyysoft.msdp.common.security.service;

import lombok.Getter;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author mao.chi
 * @date 2018/8/20
 * 扩展用户信息
 */
public class MsdpUser extends User implements Serializable {
    /**
     * 用户ID
     */
    @Getter
    private String userId;
    /**
     * 用户userName
     */
    @Getter
    private String userName;

    /**
     * 用户userName
     */
    @Getter
    private String mobile;

    /**
     * 组织机构ID
     */
    @Getter
    private String deptId;

    /**
     * 租户ID
     */
    @Getter
    private String tenantId;

    /**
     *  性别
     */
    @Getter
    private String sex;

    /**
     *  用户类型
     */
    @Getter
    private String idType;

    /**
     *  用户唯一身份编号
     */
    @Getter
    private String uid;

    /**
     *  用户所属区域
     */
    @Getter
    private String areaId;


    /**
     * Construct the <code>User</code> with the details required by
     * {@link DaoAuthenticationProvider}.
     *
     * @param userId                用户ID
     * @param orgId                组织机构ID
     * @param tenantId              租户ID
     * @param userName              the username presented to the
     *                              <code>DaoAuthenticationProvider</code>
     * @param password              the password that should be presented to the
     *                              <code>DaoAuthenticationProvider</code>
     * @param enabled               set to <code>true</code> if the user is enabled
     * @param accountNonExpired     set to <code>true</code> if the account has not expired
     * @param credentialsNonExpired set to <code>true</code> if the credentials have not
     *                              expired
     * @param accountNonLocked      set to <code>true</code> if the account is not locked
     * @param authorities           the authorities that should be granted to the caller if they
     *                              presented the correct username and password and the user is enabled. Not null.
     * @throws IllegalArgumentException if a <code>null</code> value was passed either as
     *                                  a parameter or as an element in the <code>GrantedAuthority</code> collection
     */
    public MsdpUser(String userId, String orgId, String tenantId, String userName, String mobile, String loginName, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(loginName, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
        this.deptId = orgId;
        this.tenantId = tenantId;
        this.userName = userName;
        this.mobile = mobile;
    }
}
