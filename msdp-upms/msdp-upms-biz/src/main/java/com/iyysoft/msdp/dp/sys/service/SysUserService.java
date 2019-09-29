package com.iyysoft.msdp.dp.sys.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iyysoft.msdp.dp.sys.entity.SysUser;
import com.iyysoft.msdp.dp.sys.vo.UserInfoVo;
import com.iyysoft.msdp.dp.sys.vo.UserVo;
import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.dp.sys.dto.*;

import java.util.List;
import java.util.Map;

/**
 * @author mao.chi
 * @date 2017/10/31
 */
@DS("master")
public interface SysUserService extends IService<SysUser> {
    /**
     * 查询用户信息
     *
     * @param sysUser 用户
     * @return userInfo
     */
    UserInfo findUserInfo(SysUser sysUser);

    /**
     * 分页查询用户信息（含有角色信息）
     *
     * @param page    分页对象
     * @param userDto 参数列表
     * @return
     */
    IPage getUsersWithRolePage(Page page, UserDto userDto);

    /**
     * 删除用户
     *
     * @param sysUser 用户
     * @return boolean
     */
    Boolean deleteUserById(SysUser sysUser);

    /**
     * 更新当前用户基本信息
     *
     * @param userDto 用户信息
     * @return Boolean
     */
    R<Boolean> updateUserInfo(UserDto userDto);

    /**
     * 更新用户证件信息
     *
     * @param userId
     * @param userIdInfoDto
     * @return
     */
    void updateIdInfo(String userId, UserIdInfoDto userIdInfoDto);

    /**
     * 更新指定用户信息
     *
     * @param userDto 用户信息
     * @return
     */
    Boolean updateUser(UserDto userDto);

    /**
     * 通过ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserVo selectUserVoById(String userId);

    /**
     * 查询上级组织机构的用户信息
     *
     * @param username 用户名
     * @return R
     */
    List<SysUser> listAncestorUsers(String username);

    /**
     * 保存用户信息
     *
     * @param userDto Dto 对象
     * @return success/fail
     */
    Boolean saveUser(UserDto userDto);

    String saveLandlordUser(UserDto userDto);

    SysUser getUserByThirdId(String thirdId, String openid);

    /**
     * @param sysUserAllDto 需要添加的入住人 WQ
     * @return
     */
    Map<String, String> saveORupateSysuser(SysUserAllDto sysUserAllDto) throws Exception;

    /**
     * 实名认证状态
     *
     * @param userRealStatusDto
     * @return
     */
    R updateUserRealStatus(UserRealStatusDto userRealStatusDto);


    /**
     * 根据OpenId查询或绑定用户
     *
     * @param thirdId
     * @param openId
     * @param userName
     * @return
     */
    R<UserInfo> thirdInfo(String thirdId, String openId, String userName);

    Integer resetPassword(String userId, String encode);

    R<UserInfo> thirdOpenId(String thirdId, String openId, String userName);

    //根据省份证查询信息
    SysUser selectSysuserByIdno(String idno);

    UserInfoVo addUser(UserDto userDto);

    Integer updateIdnoName(String userId, String idno, String userName);

    /**
     * <p class="detail">
     * 功能:入住人添加新增用户记录，如果手机号已存在则直接返回userId
     * </p>
     *
     * @param checkInPersonDto :实体
     * @return list userID集合
     * @author Kings
     * @date 2019.05.17 01:07:51
     */
    List<String> saveCheckInPerson(CheckInPersonDto checkInPersonDto);

    /**
     * 根据用户ID修改手机号
     *
     * @param userId 用户ID
     * @param mobile 手机号
     * @return
     */
    Integer resetMobile(String userId, String mobile, String phone);

    Integer updateUserRole(String userId, List<String> roleList);
}
