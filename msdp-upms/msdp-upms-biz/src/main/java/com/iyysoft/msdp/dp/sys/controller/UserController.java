package com.iyysoft.msdp.dp.sys.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iyysoft.msdp.common.security.util.MsdpSecurityUtils;
import com.iyysoft.msdp.dp.sys.entity.SysRole;
import com.iyysoft.msdp.dp.sys.entity.SysThird;
import com.iyysoft.msdp.dp.sys.entity.SysUser;
import com.iyysoft.msdp.dp.sys.entity.UserThird;
import com.iyysoft.msdp.dp.sys.vo.UserInfoVo;
import com.iyysoft.msdp.dp.sys.vo.UserVo;
import com.iyysoft.msdp.dp.sys.service.SysRoleService;
import com.iyysoft.msdp.dp.sys.service.SysThirdService;
import com.iyysoft.msdp.dp.sys.service.SysUserService;
import com.iyysoft.msdp.dp.sys.service.UserThirdService;
import com.iyysoft.msdp.common.core.constant.SecurityConstants;
import com.iyysoft.msdp.common.core.constant.enums.REnum;
import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.common.log.annotation.SysLog;
import com.iyysoft.msdp.common.security.annotation.Inner;
import com.iyysoft.msdp.dp.sys.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author mao.chi
 * @date 2018/12/16
 */
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/user")
@Api(value = "user", tags = "用户管理模块")
public class UserController {

    private final static String ROLECODE = "ROLE_RENTER";

    private final SysUserService userService;
    private final SysThirdService thirdService;
    private final UserThirdService userThirdService;

    private SysRoleService sysRoleService;

    //@Value("${msdp.user.default_role:'1'}")
    //private final String default_role;


    /**
     * 获取当前用户全部信息
     *
     * @return 用户信息
     */
    @GetMapping(value = {"/info"})
    @ApiOperation(value = "获取当前用户全部信息")
    public R<UserInfo> info() {
        String userId = MsdpSecurityUtils.getUser().getUserId();
        SysUser user = userService.getOne(Wrappers.<SysUser>query()
                .lambda().eq(SysUser::getUserId, userId));
        if (user == null) {
            return new R<>(REnum.CODE.NOT_USER_INFO);
        }
        return new R<>(userService.findUserInfo(user));
    }

    /**
     * 获取当前用户基本信息
     *
     * @return 用户信息
     */
    @GetMapping(value = {"/userInfo"})
    @ApiOperation(value = "获取当前用户基本信息")
    public R<UserInfoVo> userInfo() {
        String userId = MsdpSecurityUtils.getUser().getUserId();
        SysUser user = userService.getOne(Wrappers.<SysUser>query()
                .lambda().eq(SysUser::getUserId, userId));
        if (user == null) {
            return new R<>(REnum.CODE.NOT_USER_INFO);
        }
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtil.copyProperties(user, userInfoVo);
        return new R<UserInfoVo>(userInfoVo);
    }

    /**
     * 判断当前用户是否实名
     *
     * @return 用户信息
     */
    @GetMapping(value = {"/realStatus"})
    @ApiOperation(value = "判断当前用户是否实名")
    public R<String> getRealStatus(@ApiParam("'0'未认证 '1' 已认证 '2' 待认证 ")
                                   @RequestParam(required = false) String userId) {
        if (userId == null) {
            userId = MsdpSecurityUtils.getUser().getUserId();
        }
        log.debug("judge user id {} real status", userId);
        SysUser user = userService.getOne(Wrappers.<SysUser>query()
                .lambda().eq(SysUser::getUserId, userId));
        if (user == null) {
            return new R<>(REnum.CODE.NOT_USER_INFO);
        }
        return new R<>(user.getRealStatus());
    }

    /**
     * 获取指定用户全部信息
     *
     * @return 用户信息
     */
    @Inner
    @ApiOperation(value = "获取指定用户全部信息")
    @GetMapping("/info/{userName}")
    public R<UserInfo> info(@PathVariable String userName) {
        if (StringUtils.isEmpty(userName)) return new R<>(REnum.CODE.PARAM_ERROR);
        if (userName.indexOf("@") > 0) {
            SysUser user = userService.getOne(Wrappers.<SysUser>query()
                    .lambda().eq(SysUser::getEmail, userName));
            if (user == null) {
                return new R<UserInfo>(REnum.CODE.NOT_USER_INFO);
            }
            return new R<UserInfo>(userService.findUserInfo(user));
        } else if (Pattern.compile("[0-9]*").matcher(userName).matches() && userName.length() == 11) {
            SysUser user = userService.getOne(Wrappers.<SysUser>query()
                    .lambda().eq(SysUser::getMobile, userName));
            if (user == null) {
                return new R<UserInfo>(REnum.CODE.NOT_USER_INFO);
            }
            return new R<UserInfo>(userService.findUserInfo(user));

        } else {
            SysUser user = userService.getOne(Wrappers.<SysUser>query().lambda().
                    eq(SysUser::getLoginName, userName)
                    .or()
                    .eq(SysUser::getUserId, userName));
            if (user == null) {
                return new R<UserInfo>(REnum.CODE.NOT_USER_INFO);
            }
            return new R<UserInfo>(userService.findUserInfo(user));

        }
    }

    /**
     * 获取当前用户基本信息
     *
     * @return 用户信息
     */
    @GetMapping(value = {"/userId/{userId}"})
    @ApiOperation(value = "获取指定用户基本信息")
    public R<UserInfoVo> userId(@PathVariable String userId) {
        if (StringUtils.isEmpty(userId)) return new R<>(REnum.CODE.PARAM_ERROR);
        SysUser user = userService.getOne(Wrappers.<SysUser>query()
                .lambda().eq(SysUser::getUserId, userId));
        if (user == null) return new R(REnum.CODE.USER_IS_NOT_EXIST);
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtil.copyProperties(user, userInfoVo);
        return new R<UserInfoVo>(userInfoVo);
    }

    /**
     * 获取当前用户基本信息
     *
     * @return 用户信息
     */
    @GetMapping(value = {"/userinfo/{userName}"})
    @ApiOperation(value = "获取指定用户基本信息")
    public R<UserInfoVo> userInfo(@PathVariable String userName) {
        if (StringUtils.isEmpty(userName)) return new R<>(REnum.CODE.PARAM_ERROR);
        if (userName.indexOf("@") > 0) {
            SysUser user = userService.getOne(Wrappers.<SysUser>query()
                    .lambda().eq(SysUser::getEmail, userName));
            if (user == null) return new R(REnum.CODE.USER_IS_NOT_EXIST);
            UserInfoVo userInfoVo = new UserInfoVo();
            BeanUtil.copyProperties(user, userInfoVo);
            return new R<UserInfoVo>(userInfoVo);
        } else if (Pattern.compile("[0-9]*").matcher(userName).matches() && userName.length() == 11) {
            SysUser user = userService.getOne(Wrappers.<SysUser>query()
                    .lambda().eq(SysUser::getMobile, userName));
            if (user == null) return new R(REnum.CODE.USER_IS_NOT_EXIST);
            UserInfoVo userInfoVo = new UserInfoVo();
            BeanUtil.copyProperties(user, userInfoVo);
            return new R<UserInfoVo>(userInfoVo);

        } else {
            SysUser user = userService.getOne(Wrappers.<SysUser>query()
                    .lambda().eq(SysUser::getLoginName, userName));
            if (user == null) return new R(REnum.CODE.USER_IS_NOT_EXIST);
            UserInfoVo userInfoVo = new UserInfoVo();
            BeanUtil.copyProperties(user, userInfoVo);
            return new R<UserInfoVo>(userInfoVo);
        }

    }

    /**
     * 按手机获取当前用户基本信息
     *
     * @return 用户信息
     */
    @GetMapping(value = {"/mobile/{mobile}"})
    @ApiOperation(value = "按手机获取当前用户基本信息")
    public R<UserInfoVo> userMobile(@PathVariable String mobile) {
        if (StringUtils.isEmpty(mobile)) return new R<>(REnum.CODE.PARAM_ERROR);

        SysUser user = userService.getOne(Wrappers.<SysUser>query()
                .lambda().eq(SysUser::getMobile, mobile));
        if (user == null) return new R(REnum.CODE.USER_IS_NOT_EXIST);
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtil.copyProperties(user, userInfoVo);
        return new R<UserInfoVo>(userInfoVo);
    }

    /**
     * 按手机获取当前用户基本信息
     *
     * @return 用户信息
     */
    @GetMapping(value = {"/email/{mobile}"})
    @ApiOperation(value = "按手机获取当前用户基本信息")
    public R<UserInfoVo> userEmail(@PathVariable String email) {
        if (StringUtils.isEmpty(email)) return new R<>(REnum.CODE.PARAM_ERROR);

        SysUser user = userService.getOne(Wrappers.<SysUser>query()
                .lambda().eq(SysUser::getEmail, email));
        if (user == null) return new R(REnum.CODE.USER_IS_NOT_EXIST);
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtil.copyProperties(user, userInfoVo);
        return new R<UserInfoVo>(userInfoVo);
    }

    /**
     * 按手机获取当前用户基本信息
     *
     * @return 用户信息
     */
    @GetMapping(value = {"/loginName/{mobile}"})
    @ApiOperation(value = "按手机获取当前用户基本信息")
    public R<UserInfoVo> userLoginName(@PathVariable String loginName) {
        if (StringUtils.isEmpty(loginName)) return new R<>(REnum.CODE.PARAM_ERROR);

        SysUser user = userService.getOne(Wrappers.<SysUser>query()
                .lambda().eq(SysUser::getLoginName, loginName));
        if (user == null) return new R(REnum.CODE.USER_IS_NOT_EXIST);
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtil.copyProperties(user, userInfoVo);
        return new R<UserInfoVo>(userInfoVo);
    }

    /**
     * 按证件号获取当前用户基本信息
     *
     * @return 用户信息
     */
    @GetMapping(value = {"/idno/{idno}"})
    @ApiOperation(value = "按证件号获取当前用户基本信息")
    public R<UserInfoVo> userIdno(@PathVariable String idno) {
        if (StringUtils.isEmpty(idno)) return new R<>(REnum.CODE.PARAM_ERROR);

        SysUser user = userService.getOne(Wrappers.<SysUser>query()
                .lambda().eq(SysUser::getIdno, idno));
        if (user == null) return new R(REnum.CODE.USER_IS_NOT_EXIST);
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtil.copyProperties(user, userInfoVo);
        return new R<UserInfoVo>(userInfoVo);
    }

    /**
     * 判断当前用户是否实名
     *
     * @return 用户信息
     */
    @GetMapping(value = {"/realStatus/{userName}"})
    @ApiOperation(value = "判断指定用户是否实名")
    public R<Boolean> realStatus(@PathVariable String userName) {
        if (StringUtils.isEmpty(userName)) return new R<>(REnum.CODE.PARAM_ERROR);
        if (userName.indexOf("@") > 0) {
            SysUser user = userService.getOne(Wrappers.<SysUser>query()
                    .lambda().eq(SysUser::getEmail, userName));
            if (user == null) return new R(REnum.CODE.USER_IS_NOT_EXIST);
            if (user.getRealStatus().equals("1")) return new R<Boolean>(Boolean.TRUE);
            else return new R<Boolean>(Boolean.FALSE);
        } else if (Pattern.compile("[0-9]*").matcher(userName).matches() && userName.length() == 11) {
            SysUser user = userService.getOne(Wrappers.<SysUser>query()
                    .lambda().eq(SysUser::getMobile, userName));
            if (user == null) return new R(REnum.CODE.USER_IS_NOT_EXIST);
            if (user.getRealStatus().equals("1")) return new R<Boolean>(Boolean.TRUE);
            else return new R<Boolean>(Boolean.FALSE);

        } else {
            SysUser user = userService.getOne(Wrappers.<SysUser>query()
                    .lambda().eq(SysUser::getLoginName, userName));
            if (user == null) return new R(REnum.CODE.USER_IS_NOT_EXIST);
            if (user.getRealStatus().equals("1")) return new R<Boolean>(Boolean.TRUE);
            else return new R<Boolean>(Boolean.FALSE);
        }
    }


    /**
     * 获取微信小程序用户信息
     *
     * @return 用户信息
     */
    @Inner
    @PostMapping("/third/wxmini")
    public R<UserInfo> wxminiBind(@RequestParam("code") String code, @RequestParam("appId") String appId, @RequestParam(value = "userName", required = false) String userName) {
        SysRole sysRole = sysRoleService.getOne(Wrappers.<SysRole>query().lambda().eq(SysRole::getRoleCode, ROLECODE));
        String default_role = sysRole.getRoleId();
        SysThird sysThird = thirdService.getByTypeAppId("WXMINI", appId);
        if (sysThird == null) {
            return new R<>(REnum.CODE.WEXIN_APPID_FAILED);
        }

        String url = sysThird.getRedirectUrl() + "/sns/jscode2session?appid="
                + sysThird.getAppId() + "&secret=" + sysThird.getAppSecret()
                + "&js_code=" + code + "&grant_type=authorization_code";
        JSONObject message;
        try {
            // RestTemplate是Spring封装好的, 挺好用, 可做成单例模式
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);
            message = JSON.parseObject(response);
        } catch (Exception e) {
            //log.error("微信 服务器 请求错误", e);
            return new R<>(REnum.CODE.WEXIN_SERVICE_FAILED);
        }
        log.info("message：" + message.toString());
        log.info("==============小程序登录方法结束================");
        if (message.get("errcode") != null) {
            log.debug("Authentication failed: no credentials provided");

            return new R<>(REnum.CODE.WEXIN_SERVICE_FAILED, message.get("errmsg").toString());
        }
        String sessionKey = (String) message.get("session_key").toString();
        String openId = message.get("openid").toString();
        String unionId = message.getString("unionid");
        if (unionId != null) {
            userThirdService.updateUnionId(sysThird.getThirdId(), openId, unionId);
        }


//		String openId = "oWxwc5CJh_1Kj9r4PQPrZGHaVLVI";
//		String sessionKey = "wvg13HN6wkwFLQz1TgG5HQ==";
        SysUser user = userService.getUserByThirdId(sysThird.getThirdId(), openId);
        if (user == null) {
            if (com.baomidou.mybatisplus.core.toolkit.StringUtils.isEmpty(userName))
                return new R<>(REnum.CODE.NOT_BINDING, sessionKey);
            if (userName.indexOf("@") > 0) {
                user = userService.getOne(Wrappers.<SysUser>query()
                        .lambda().eq(SysUser::getEmail, userName));
            } else if (Pattern.compile("[0-9]*").matcher(userName).matches() && userName.length() == 11) {
                user = userService.getOne(Wrappers.<SysUser>query()
                        .lambda().eq(SysUser::getMobile, userName));
            } else {
                user = userService.getOne(Wrappers.<SysUser>query()
                        .lambda().eq(SysUser::getLoginName, userName));

            }
            if (user == null) {
                UserDto userDto = new UserDto();
                userDto.setLoginName(userName);
                userDto.setMobile(userName);
                userDto.setEmail(userName + "@iyysoft.com");
                String passwd = RandomUtil.randomNumbers(Integer.parseInt(SecurityConstants.PASSWORD_SIZE));
                userDto.setPassword(passwd);


                List<String> roleList = Arrays.asList(default_role.split(","));

                userDto.setRole(roleList);//默认角色
                userService.saveUser(userDto);

                user = userService.getOne(Wrappers.<SysUser>query()
                        .lambda().eq(SysUser::getMobile, userName));

                return new R<>(REnum.CODE.NOT_USER_INFO);
            }
            userThirdService.wxminiBind(user, sysThird.getThirdId(), openId);
            return new R<UserInfo>(userService.findUserInfo(user));
        }
        return new R<UserInfo>(userService.findUserInfo(user));
    }

//	/**
//	 * 获取微信小程序用户信息
//	 *
//	 * @return 用户信息
//	 */
//	@Inner
//	@PostMapping("/wxmini/bind")
//	public R wxminiBind(@RequestParam("tenantId") String tenantId, @RequestParam("openId") String openId, @RequestParam("userName") String userName) {
//
//		SysThird sysThird = thirdService.getByTypeTeantId("WXMINI", tenantId);
//		if(sysThird == null){
//			return new R<>(Boolean.FALSE, String.format("第三方配置信息为空 %s", tenantId));
//		}
//
//
//		SysUser user = userService.getUserByThirdId(sysThird.getThirdId(), openId);
//		if (user == null) {
//			if(StringUtils.isEmpty(userName))return new R<>(REnum.CODE.NOT_USER_INFO);
//			if(userName.indexOf("@")>0) {
//				user = userService.getOne(Wrappers.<SysUser>query()
//						.lambda().eq(SysUser::getMail, userName));
//			}
//			else if(Pattern.compile("[0-9]*").matcher(userName).matches() && userName.length()==11){
//				user = userService.getOne(Wrappers.<SysUser>query()
//						.lambda().eq(SysUser::getMobile, userName));
//			}
//			else {
//				user = userService.getOne(Wrappers.<SysUser>query()
//						.lambda().eq(SysUser::getLoginName, userName));
//
//			}
//			if (user == null) {
//				return new R<>(Boolean.FALSE, REnum.CODE.NOT_USER_INFO, String.format("用户信息为空 %s", userName));
//			}
//			userThirdService.wxminiBind(user, sysThird.getThirdId(), openId);
//			return new R<>(userService.findUserInfo(user));
//		}
//		return new R<>(user);
//	}


    /**
     * 通过ID查询用户信息
     *
     * @param userId ID
     * @return 用户信息
     */
    @GetMapping("/{userId}")
    public R<UserVo> user(@PathVariable String userId) {
        return new R<>(userService.selectUserVoById(userId));
    }

    /**
     * 根据用户名查询用户信息
     *
     * @param loginName 用户名
     * @return
     */
    @GetMapping("/details/{loginName}")
    public R<SysUser> userName(@PathVariable String loginName) {
        SysUser condition = new SysUser();
        condition.setLoginName(loginName);
        return new R<SysUser>(userService.getOne(new QueryWrapper<>(condition)));
    }

    /**
     * 删除用户信息
     *
     * @param userId ID
     * @return R
     */
    @SysLog("删除用户信息")
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('user_del') or hasRole('msdp')")
    @ApiOperation(value = "删除用户", notes = "根据ID删除用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String", paramType = "path")
    public R userDel(@PathVariable String userId) {
        SysUser sysUser = userService.getById(userId);
        return new R<>(userService.deleteUserById(sysUser));
    }

    /**
     * 添加用户
     *
     * @param userDto 用户信息
     * @return success/false
     */
    @SysLog("添加用户")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('user_add') or hasRole('msdp')")
    public R user(@RequestBody UserDto userDto) {
        return new R<>(userService.saveUser(userDto));
    }

    /**
     * 添加用户
     *
     * @param userDto 用户信息
     * @return success/false
     */
    @SysLog("添加用户")
    @PostMapping("/addUser")
    public R<UserInfoVo> addUser(@RequestBody UserDto userDto) {
        return new R<>(userService.addUser(userDto));
    }

    /**
     * 修改证件号和姓名
     */
    @SysLog("修改证件号和姓名")
    @PostMapping("/updateIdnoName/{userId}")
    public R updateIdnoName(@PathVariable(value = "userId") String userId,
                            @RequestParam(value = "idno") String idno,
                            @RequestParam(value = "userName") String userName) {
        return new R<>(userService.updateIdnoName(userId, idno, userName));
    }

    /**
     * 修改证件信息
     */
    @SysLog("修改证件号信息")
    @ApiOperation("修改用户证件信息")
    @PutMapping("/{userId}/idInfo")
    public R updateIdInfo(@PathVariable(value = "userId") String userId,
                          @RequestBody UserIdInfoDto dto) {
        userService.updateIdInfo(userId, dto);
        return new R<>();
    }

    /**
     * 添加房东用户
     *
     * @param userDto 用户信息
     * @return success/false
     */
    @SysLog("添加房东用户")
    @PostMapping("/landlordUser")
    @PreAuthorize("@pms.hasPermission('user_add')")
    public R<String> landlordUser(@RequestBody UserDto userDto) {
        return new R<>(userService.saveLandlordUser(userDto));
    }

    /**
     * 更新用户信息
     *
     * @param userDto 用户信息
     * @return R
     */
    @SysLog("更新用户信息")
    @PutMapping
    @PreAuthorize("@pms.hasPermission('user_edit')")
    public R updateUser(@Valid @RequestBody UserDto userDto) {
        return new R<>(userService.updateUser(userDto));
    }

    /**
     * 分页查询用户
     *
     * @param page    参数集
     * @param userDto 查询参数列表
     * @return 用户集合
     */
    @GetMapping("/page")
    public R getUserPage(Page page, UserDto userDto) {
        return new R<>(userService.getUsersWithRolePage(page, userDto));
    }

    /**
     * 修改个人信息
     *
     * @param userDto userDto
     * @return success/false
     */
    @SysLog("修改个人信息")
    @PutMapping("/edit")
    public R updateUserInfo(@Valid @RequestBody UserDto userDto) {
        return userService.updateUserInfo(userDto);
    }

    /**
     * @param userName 用户名称
     * @return 上级组织机构用户列表
     */
    @GetMapping("/ancestor/{userName}")
    public R listAncestorUsers(@PathVariable String userName) {
        return new R<>(userService.listAncestorUsers(userName));
    }

    /**
     * 创建用户
     *
     * @return
     */
    @PostMapping("/save/sysUser")
    public R<Map<String, String>> saveORupateSysuser(@Valid @RequestBody SysUserAllDto sysUserAllDto) throws Exception {
        Map<String, String> map = userService.saveORupateSysuser(sysUserAllDto);
        return new R<>(map);
    }

    /**
     * 实名认证状态
     *
     * @return
     */
    @GetMapping("/updateRealStatus")
    public R updateRealStatus() {
        SysUser sysUser = new SysUser();
        sysUser.setRealStatus("1");
        return new R<>(this.userService.update(sysUser, Wrappers.<SysUser>query().lambda().eq(SysUser::getUserId, MsdpSecurityUtils.getUser().getUserId())));
    }

    /**
     * 实名认证状态
     *
     * @return
     */
    @PutMapping("/updateRealStatus")
    public R updateRealStatus(@Valid @RequestBody UserRealStatusDto userRealStatusDto) {
        SysUser sysUser = new SysUser();
        sysUser.setRealStatus("1");
        sysUser.setIdno(userRealStatusDto.getNumber());
        sysUser.setUserName(userRealStatusDto.getUserName());
        return new R<>(this.userService.update(sysUser, Wrappers.<SysUser>query().lambda().eq(SysUser::getUserId, MsdpSecurityUtils.getUser().getUserId())));
    }

    /**
     * 指定用户实名认证
     *
     * @return
     */
    @PutMapping("/{userId}/realStatus")
    public R updateRealStatus(@Valid @PathVariable("userId") String userId,
                              @RequestParam("realStatus") String realStatus) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setRealStatus(realStatus);
        return new R<>(this.userService.updateById(sysUser));
    }


    /**
     * 根据OpenId查询或绑定用户
     *
     * @param thirdId
     * @param openId
     * @param userName
     * @return
     */
    @Inner
    @GetMapping("/third/info")
    public R<UserInfo> thirdInfo(@RequestParam String thirdId, @RequestParam String openId, @RequestParam(value = "userName", required = false) String userName) {
        return userService.thirdInfo(thirdId, openId, userName);

    }

    /**
     * 根据OpenId绑定用户
     *
     * @param thirdId
     * @param openId
     * @param userName
     * @return
     */
    @Inner
    @PostMapping("/third/openid")
    public R<UserInfo> thirdOpenId(@RequestParam String thirdId, @RequestParam String openId, @RequestParam(value = "userName", required = false) String userName) {
        return userService.thirdOpenId(thirdId, openId, userName);

    }

    /**
     * 查询用户 WQ
     *
     * @return
     */
    @GetMapping("/select/sysUser/{userId}")
    public R<SysUser> selectSysuser(@PathVariable String userId) {
        SysUser sysUser = userService.getById(userId);
        return new R<>(sysUser);
    }

    /**
     * 根据身份证查询信息获取userid WQ
     *
     * @return
     */
    @GetMapping("/select/sysUserid/{idno}")
    public R<String> selectSysuserUserid(@PathVariable String idno) {
        SysUser sysUser = userService.selectSysuserByIdno(idno);
        return new R<>(sysUser.getUserId());
    }

    @PostMapping("/saveCheckperson")
    @ApiOperation(value = "入住人添加注册用户[@Kings]", notes = "返回userId集合")
    public R<List<String>> get(@Valid @RequestBody CheckInPersonDto checkInPersonDto) {
        return new R<>(userService.saveCheckInPerson(checkInPersonDto));
    }

    @GetMapping("/wx/logout")
    @ApiOperation(value = "退出微信登录[@cm]", notes = "返回退出信息")
    public R<String> getWxLogout(@Valid WxLogoutDto dto) {
        try {
            userThirdService.remove(Wrappers.<UserThird>query().lambda().eq(UserThird::getOpenId, dto.getOpenId()).eq(UserThird::getThirdId, dto.getThirdId()));
            return new R<>(REnum.CODE.OK, "成功退出微信。");
        } catch (Exception e) {
            return new R<>(REnum.CODE.FAILED, "退出微信失败，请联系管理员。");
        }
    }

    @ApiOperation(value = "根据userId变更用户角色")
    @PutMapping("/changeRole")
    R updateUserRole(@RequestParam("userId") String userId, @RequestParam("roleList") List<String> roleList) {
        return new R<>(userService.updateUserRole(userId, roleList));
    }
}
