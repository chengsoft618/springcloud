package com.iyysoft.msdp.dp.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iyysoft.msdp.dp.sys.dto.SysUserAllDto;
import com.iyysoft.msdp.dp.sys.dto.UserIdInfoDto;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author mao.chi
 * @since 2017-10-29
 */
@Data
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "user_id", type = IdType.UUID)
    private String userId;
    /**
     * 登录名
     */
    @TableId(value = "login_name")
    private String loginName;
    /**
     * 用户名
     */
    @TableId(value = "user_name")
    private String userName;

    private String password;
    /**
     * 随机盐
     */
    @JsonIgnore
    private String salt;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
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
    private String deptId;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 身份证
     */
    private String idno;

    /**
     * 是否实名认证 0 未认证 1 认证完成 2 认证中
     */
    private String realStatus;

    /**
     * 证件类型
     */
    private String idnoType;

    /**
     * 姓
     */
    private String lastName;

    /**
     * 名
     */
    private String firstName;

    /**
     * 推送模板openid
     */
    private String tempOpenId;

    public SysUser(SysUserAllDto sysUserAllDto) {
        //手机号
        this.setMobile(sysUserAllDto.getMobile());
        //用户名
        this.setUserName(sysUserAllDto.getUserName());
        //证件号(身份证)
        this.setIdno(sysUserAllDto.getIdno());
        //所属租户 数据库有默认值
        //this.setTenantId(sysUserAllDto.getTenantId());
        //密码
        //this.setPassword(sysUserAllDto.getPassword());
        //登录名
        this.setLoginName(sysUserAllDto.getMobile());
    }

    public SysUser() {
    }

    public SysUser(String userId, UserIdInfoDto userIdInfoDto, String realStatus) {
        this.userId = userId;
        this.idnoType = userIdInfoDto.getIdType();
        this.idno = userIdInfoDto.getIdNo();
        this.userName = userIdInfoDto.getUserName();
        this.updateTime = LocalDateTime.now();
        this.realStatus = realStatus;
    }
}
