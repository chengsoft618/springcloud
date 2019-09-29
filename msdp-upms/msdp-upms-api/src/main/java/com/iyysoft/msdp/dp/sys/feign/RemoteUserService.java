package com.iyysoft.msdp.dp.sys.feign;

import com.iyysoft.msdp.dp.sys.dto.*;
import com.iyysoft.msdp.dp.sys.entity.SysRole;
import com.iyysoft.msdp.dp.sys.entity.SysUser;
import com.iyysoft.msdp.dp.sys.vo.UserInfoVo;
import com.iyysoft.msdp.dp.sys.vo.UserVo;
import com.iyysoft.msdp.common.core.constant.SecurityConstants;
import com.iyysoft.msdp.common.core.constant.ServiceNameConstants;
import com.iyysoft.msdp.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * <p class="detail">
 * 功能:
 * </p>
 *
 * @author mao.chi
 * @ClassName Remote user service.
 * @Version V1.0.
 * @date 2018 /6/22
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteUserService {
    /**
     * 通过用户名查询用户、角色信息
     *
     * @param userName 用户名
     * @param from     调用标志
     * @return R
     * @author cm
     * @date 2019.05.14 16:02:49
     */
    @GetMapping("/user/info/{userName}")
    R<UserInfo> info(@PathVariable("userName") String userName
            , @RequestHeader(SecurityConstants.FROM) String from);

    /**
     * 查询上级组织机构的用户信息
     *
     * @param userName 用户名
     * @return R
     * @author cm
     * @date 2019.05.14 16:02:49
     */
    @GetMapping("/user/ancestor/{userName}")
    R<List<SysUser>> ancestorUsers(@PathVariable("userName") String userName);

    /**
     * 根据微信code 登录
     *
     * @param appId    :
     * @param code     :
     * @param userName :
     * @param fromIn   :
     * @return
     * @author cm
     * @date 2019.05.14 16:02:49
     */
    @PostMapping("/user/third/wxmini")
    R<UserInfo> loadUserByWxMini(@RequestParam("appId") String appId, @RequestParam("code") String code, @RequestParam("userName") String userName, @RequestHeader(SecurityConstants.FROM) String fromIn);

    /**
     * 根据微信code 登录
     *
     * @param thirdId :
     * @param openId  :
     * @param fromIn  :
     * @return
     * @author cm
     * @date 2019.05.14 16:02:49
     */
    @PostMapping("/user/third/openid")
    R<UserInfo> loadUserByOpenId(@RequestParam("thirdId") String thirdId, @RequestParam("openId") String openId, @RequestParam("userName") String userName, @RequestHeader(SecurityConstants.FROM) String fromIn);

    /**
     * <p class="detail">
     * 功能:
     * </p>
     *
     * @param userDto :
     * @return r
     * @author cm
     * @date 2019.05.14 16:02:49
     */
    @PostMapping("/user/landlordUser")
    R<String> landlordUser(@RequestBody UserDto userDto);

    /**
     * <p class="detail">
     * 功能:
     * </p>
     *
     * @param userId :
     * @return r
     * @author cm
     * @date 2019.05.14 16:02:49
     */
    @GetMapping("/user/{userId}")
    R<UserVo> userName(@PathVariable("userId") String userId);

    /**
     * <p class="detail">
     * 功能:
     * </p>
     *
     * @param userDto :
     * @return r
     * @author cm
     * @date 2019.05.14 16:02:49
     */
    @PostMapping("/user")
    R user(@RequestBody UserDto userDto);

    /**
     * <p class="detail">
     * 功能: 添加用户
     * </p>
     *
     * @param sysUserAllDto :
     * @return r
     * @author cm
     * @date 2019.05.14 16:02:49
     */
    @PostMapping("/user/save/sysUser")
    R<Map<String, String>> saveORupateSysuser(@Valid @RequestBody SysUserAllDto sysUserAllDto);

    /**
     * <p class="detail">
     * 功能:  实名认证状态
     * </p>
     *
     * @return r
     * @author cm
     * @date 2019.05.14 16:02:49
     */
    @GetMapping("/user/realStatus/{userName}")
    R realStatus(@PathVariable("userName") String userName);

    /**
     * <p class="detail">
     * 功能:  实名认证状态
     * </p>
     *
     * @return r
     * @author cm
     * @date 2019.05.14 16:02:49
     */
    @GetMapping("/user/realStatus")
    R realStatus();

    /**
     * <p class="detail">
     * 功能:  实名认证状态
     * </p>
     *
     * @return r
     * @author cm
     * @date 2019.05.14 16:02:49
     */
    @GetMapping("/user/updateUserRealStatus")
    R updateUserRealStatus();

    /**
     * <p class="detail">
     * 功能:  实名认证状态
     * </p>
     *
     * @return r
     * @author cm
     * @date 2019.05.14 16:02:49
     * UserRealStatusDto userRealStatusDto
     */
    @GetMapping("/user/updateRealStatus")
    R updateRealStatus();

    /**
     * <p class="detail">
     * 功能:  实名认证状态
     * </p>
     *
     * @return r
     * @author cm
     * @date 2019.05.14 16:02:49
     * UserRealStatusDto userRealStatusDto
     */
    @PutMapping("/user/updateRealStatus")
    R updateRealStatus(@Valid @RequestBody UserRealStatusDto userRealStatusDto);

    @PutMapping("/user/{userId}/realStatus")
    R updateRealStatus(@PathVariable("userId") String userId, @RequestParam("realStatus") String realStatus);

    /**
     * <p class="detail">
     * 功能:  查找用户信息
     * </p>
     *
     * @return r
     * @author wangqi
     * @date 2019.05.14 16:02:49
     */
    @GetMapping("/user/select/sysUser/{userId}")
    R<SysUser> selectSysuser(@PathVariable("userId") String userId);

    /**
     * 根据身份证查询信息获取userid WQ
     *
     * @return
     */
    @GetMapping("/user/select/sysUserid/{idno}")
    R<String> selectSysuserUserid(@PathVariable("idno") String idno);

    /**
     * 通过手UserId查询用户基本信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/user/userId/{userId}")
    R<UserInfoVo> userInfoByUserId(@PathVariable("userId") String userId);

    /**
     * 通过手机号/email/登录名查询用户基本信息
     *
     * @param userName
     * @return
     */
    @GetMapping("/user/all/{userName}")
    R<UserInfoVo> userInfoByAll(@PathVariable("userName") String userName);

    /**
     * 通过手机号查询用户基本信息
     *
     * @param mobile
     * @return
     */
    @GetMapping("/user/mobile/{mobile}")
    R<UserInfoVo> userInfoByMobile(@PathVariable("mobile") String mobile);

    /**
     * 通过手机号查询用户基本信息
     *
     * @param email
     * @return
     */
    @GetMapping("/user/email/{email}")
    R<UserInfoVo> userInfoByEmail(@PathVariable("email") String email);

    /**
     * 通过手机号查询用户基本信息
     *
     * @param loginName
     * @return
     */
    @GetMapping("/user/loginName/{loginName}")
    R<UserInfoVo> userInfoByLoginName(@PathVariable("loginName") String loginName);

    /**
     * 新增用户返回用户基本信息
     *
     * @param userDto
     * @return
     */
    @PostMapping("/user/addUser")
    R<UserInfoVo> addUser(@RequestBody UserDto userDto);

    /**
     * 修改证件号和姓名
     *
     * @return
     */
    @PostMapping("/user/updateIdnoName/{userId}")
    R updateUserIdnoName(@PathVariable("userId") String userId, @RequestParam("idno") String idno, @RequestParam("userName") String userName);

    /**
     * 修改证件信息
     *
     * @return
     */
    @PutMapping("/user/{userId}/idInfo")
    R updateIdInfo(@PathVariable("userId") String userId, @RequestBody UserIdInfoDto dto);

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
    @PostMapping("/user/saveCheckperson")
    R<List<String>> saveCheckperson(@Valid @RequestBody CheckInPersonDto checkInPersonDto);

    @PostMapping("/reg/resetMobile")
    R resetMobile(@RequestParam("mobile") String mobile, @RequestParam("smsCode") String smsCode, @RequestParam("reMobile") String reMobile);

    @GetMapping("/role/code/{code}")
    R<SysRole> findRolebyCode(@PathVariable("code") String code);

    @PutMapping("/user/changeRole")
    R updateUserRole(@RequestParam("userId") String userId, @RequestParam("roleList") List<String> roleList);
}
