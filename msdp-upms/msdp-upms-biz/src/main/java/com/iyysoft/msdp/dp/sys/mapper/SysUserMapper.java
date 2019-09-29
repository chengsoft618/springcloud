package com.iyysoft.msdp.dp.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iyysoft.msdp.dp.sys.dto.UserDto;
import com.iyysoft.msdp.dp.sys.dto.UserRealStatusDto;
import com.iyysoft.msdp.dp.sys.entity.SysUser;
import com.iyysoft.msdp.dp.sys.vo.UserVo;
import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.common.data.datascope.DataScope;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author mao.chi
 * @since 2017-10-29
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 通过用户名查询用户信息（含有角色信息）
     *
     * @param username 用户名
     * @return userVo
     */
    UserVo getUserVoByUsername(String username);

    /**
     * 分页查询用户信息（含角色）
     *
     * @param page      分页
     * @param userDto   查询参数
     * @param dataScope
     * @return list
     */
    IPage<UserVo> getUserVosPage(Page page, @Param("query") UserDto userDto, DataScope dataScope);

    /**
     * 通过ID查询用户信息
     *
     * @param userId 用户ID
     * @return userVo
     */
    UserVo getUserVoById(String userId);

    SysUser getUserByThirdId(@Param("thirdId") String thirdId, @Param("openId") String openId);

    Integer resetPassword(@Param("userId") String userId, @Param("password") String password);

    //根据身份证号查询对应数据
    SysUser selectSysuserByIdno(@Param("idno") String idno);

    /**
     * 实名认证状态
     *
     * @param userRealStatusDto
     * @return
     */
    R updateUserRealStatus(UserRealStatusDto userRealStatusDto);

    /**
     * 根据手机查找用户信息
     *
     * @param mobile
     * @return
     */
    SysUser selectSysuserByMobile(@Param("mobile") String mobile);

    Integer updateUserIdnoName(@Param("userId") String userId, @Param("idno") String idno, @Param("userName") String userName);

    /**
     * 根据用户ID修改手机号
     *
     * @param userId
     * @param mobile
     * @return
     */
    Integer resetMobile(@Param("userId") String userId, @Param("mobile") String mobile,
                        @Param("phone") String phone, @Param("updateTime") LocalDateTime updateTime);
}
