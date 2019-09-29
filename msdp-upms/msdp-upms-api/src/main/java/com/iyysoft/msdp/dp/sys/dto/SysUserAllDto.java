package com.iyysoft.msdp.dp.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author WQ
 * @date 2019-05-12 18:47
 * @description: <描述>
 */

@Data
@ApiModel("用户信息")
public class SysUserAllDto implements Serializable {


    /**
     * 登录名
     */
    //@NotBlank(message = "登录名不能为空")
    @ApiModelProperty(value = "登录名", required = true, dataType = "String", example = "123456543")
    private String loginName;
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名", required = true, dataType = "String", example = "张三")
    private String userName;

    //@NotBlank(message = "账户密码不能为空")
    @ApiModelProperty(value = "账户密码", dataType = "String", example = "1234")
    private String password;
    /**
     * 随机盐
     */
    @ApiModelProperty(value = "随机盐", dataType = "String", example = "12345")
    private String salt;

    /**
     * 0-正常，1-删除
     */
    @ApiModelProperty(value = "0-正常，1-删除", dataType = "String", example = "0")
    private String delFlag;

    /**
     * 锁定标记
     */
    @ApiModelProperty(value = "锁定标记", dataType = "String", example = "1234")
    private String lockFlag;
    /**
     * 手机
     */
    @NotBlank(message = "手机不能为空")
    @ApiModelProperty(value = "手机", required = true, dataType = "String", example = "12345678901")
    private String mobile;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话", dataType = "String", example = "5816578")
    private String phone;

    /**
     * Email
     */
    @ApiModelProperty(value = "Email", dataType = "String", example = "123456789@qq.com")
    private String email;

    /**
     * 用户呢称
     */
    @ApiModelProperty(value = "用户呢称", dataType = "String", example = "张三")
    private String userNick;

    /**
     * 生日
     */
    @ApiModelProperty(value = "生日", dataType = "String", example = "2189-12-12")
    private String birthday;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别", dataType = "String", example = "男")
    private String sex;

    /**
     * 用户区域
     */
    @ApiModelProperty(value = "用户区域", dataType = "String", example = "123")
    private String areaId;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像", dataType = "String", example = "")
    private String avatar;

    /**
     * 组织机构ID
     */
    @ApiModelProperty(value = "组织机构ID", dataType = "String", example = "123")
    private String orgId;

    /**
     * 租户ID
     */
    //@NotBlank(message = "租户ID不能为空")
    @ApiModelProperty(value = "租户ID", required = true, dataType = "String", example = "1234")
    private String tenantId;

    /**
     * 身份证
     */
    @NotBlank(message = "身份证不能为空")
    @ApiModelProperty(value = "身份证", required = true, dataType = "String", example = "123456789012345678")
    private String idno;


}
