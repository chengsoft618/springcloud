package com.iyysoft.msdp.dp.sys.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iyysoft.msdp.common.security.util.SecurityUtils;
import com.iyysoft.msdp.dp.sys.enums.IdTypeEnum;
import com.iyysoft.msdp.dp.sys.service.*;
import com.iyysoft.msdp.dp.sys.vo.MenuVo;
import com.iyysoft.msdp.dp.sys.vo.UserInfoVo;
import com.iyysoft.msdp.dp.sys.vo.UserVo;
import com.iyysoft.msdp.dp.sys.mapper.SysThirdMapper;
import com.iyysoft.msdp.dp.sys.mapper.SysUserIdInfoMapper;
import com.iyysoft.msdp.dp.sys.mapper.SysUserMapper;
import com.iyysoft.msdp.dp.sys.mapper.UserThirdMapper;
import com.iyysoft.msdp.common.core.constant.CommonConstants;
import com.iyysoft.msdp.common.core.constant.SecurityConstants;
import com.iyysoft.msdp.common.core.constant.enums.REnum;
import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.common.data.datascope.DataScope;
import com.iyysoft.msdp.dp.sys.dto.*;
import com.iyysoft.msdp.dp.sys.entity.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author mao.chi
 * @date 2017/10/31
 */
@Slf4j
@Service
@DS("master")
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    private final SysMenuService sysMenuService;
    private final SysRoleService sysRoleService;
    private final SysDeptService sysDeptService;
    private final SysUserRoleService sysUserRoleService;
    private final SysDeptRelationService orgRelationService;

    private final SysThirdMapper thirdMapper;
    private final UserThirdMapper userThirdMapper;

    private SysUserIdInfoMapper sysUserIdInfoMapper;

    private final RedisTemplate redisTemplate;

    /**
     * 保存用户信息
     *
     * @param userDto Dto 对象
     * @return success/fail
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveUser(UserDto userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setDelFlag(CommonConstants.STATUS_NORMAL);
        sysUser.setLockFlag(CommonConstants.STATUS_NORMAL);
        sysUser.setTenantId(null);
        sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
        baseMapper.insert(sysUser);
        List<SysUserRole> userRoleList = userDto.getRole()
                .stream().map(roleId -> {
                    SysUserRole userRole = new SysUserRole();
                    userRole.setUserId(sysUser.getUserId());
                    userRole.setRoleId(roleId);
                    return userRole;
                }).collect(Collectors.toList());
        return sysUserRoleService.saveBatch(userRoleList);
    }

    /**
     * 保存用户信息
     *
     * @param userDto Dto 对象
     * @return success/fail
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserInfoVo addUser(UserDto userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setDelFlag(CommonConstants.STATUS_NORMAL);
        sysUser.setTenantId(null);
        sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
        baseMapper.insert(sysUser);
        List<SysUserRole> userRoleList = userDto.getRole()
                .stream().map(roleId -> {
                    SysUserRole userRole = new SysUserRole();
                    userRole.setUserId(sysUser.getUserId());
                    userRole.setRoleId(roleId);
                    return userRole;
                }).collect(Collectors.toList());
        sysUserRoleService.saveBatch(userRoleList);
        UserInfoVo userInfo = new UserInfoVo();
        BeanUtils.copyProperties(sysUser, userInfo);
        return userInfo;
    }

    @Override
    public Integer updateIdnoName(String userId, String idno, String userName) {
        return baseMapper.updateUserIdnoName(userId, idno, userName);
    }

    /**
     * 保存房东信息
     *
     * @param userDto Dto 对象
     * @return success/fail
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveLandlordUser(UserDto userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setDelFlag(CommonConstants.STATUS_NORMAL);
        sysUser.setPassword(userDto.getPassword());
        sysUser.setTenantId(null);
        baseMapper.insert(sysUser);
        return sysUser.getUserId();
    }

    /**
     * 通过查用户的全部信息
     *
     * @param sysUser 用户
     * @return
     */
    @Override
    public UserInfo findUserInfo(SysUser sysUser) {
        UserInfo userInfo = new UserInfo();
        userInfo.setSysUser(sysUser);
        //设置角色列表  （ID）
        List<String> roleIds = sysRoleService.findRolesByUserId(sysUser.getUserId())
                .stream()
                .map(SysRole::getRoleId)
                .collect(Collectors.toList());
        userInfo.setRoles(ArrayUtil.toArray(roleIds, String.class));

        //设置权限列表（menu.permission）
        Set<String> permissions = new HashSet<>();
        roleIds.forEach(roleId -> {
            List<String> permissionList = sysMenuService.findMenuByRoleId(roleId)
                    .stream()
                    .filter(menuVo -> StringUtils.isNotEmpty(menuVo.getPermission()))
                    .map(MenuVo::getPermission)
                    .collect(Collectors.toList());
            permissions.addAll(permissionList);
        });
        userInfo.setPermissions(ArrayUtil.toArray(permissions, String.class));
        return userInfo;
    }

    /**
     * 分页查询用户信息（含有角色信息）
     *
     * @param page    分页对象
     * @param userDto 参数列表
     * @return
     */
    @Override
    public IPage getUsersWithRolePage(Page page, UserDto userDto) {
        return baseMapper.getUserVosPage(page, userDto, new DataScope());
    }

    /**
     * 通过ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @Override
    public UserVo selectUserVoById(String userId) {
        return baseMapper.getUserVoById(userId);
    }

    /**
     * 删除用户
     *
     * @param sysUser 用户
     * @return Boolean
     */
    @Override
    @CacheEvict(value = "user_details", key = "#sysUser.userId")
    public Boolean deleteUserById(SysUser sysUser) {
        sysUserRoleService.deleteByUserId(sysUser.getUserId());
        this.removeById(sysUser.getUserId());
        return Boolean.TRUE;
    }

    @Override
    @CacheEvict(value = "user_details", key = "#userDto.userId")
    public R updateUserInfo(UserDto userDto) {
        UserVo userVo = baseMapper.getUserVoByUsername(userDto.getLoginName());
        SysUser sysUser = new SysUser();
        if (StrUtil.isNotBlank(userDto.getPassword())
                && StrUtil.isNotBlank(userDto.getNewpassword1())) {
            if (ENCODER.matches(userDto.getPassword(), userVo.getPassword())) {
                sysUser.setPassword(ENCODER.encode(userDto.getNewpassword1()));
            } else {
                log.warn("原密码错误，修改密码失败:{}", userDto.getUserName());
                return new R<>(REnum.CODE.FAILED, "原密码错误，修改失败");
            }
        }
        sysUser.setPhone(userDto.getPhone());
        sysUser.setUserId(userVo.getUserId());
        sysUser.setAvatar(userDto.getAvatar());
        return new R<>(this.updateById(sysUser));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateIdInfo(String userId, UserIdInfoDto userIdInfoDto) {
        if (IdTypeEnum.ID_CARD.type().equals(userIdInfoDto.getIdType())) {
            //为id卡类型
            //TODO 用拼音拆分姓名
            //userIdInfoDto.setLastName(PinyinUtil.(userIdInfoDto.getUserName()));
            //userIdInfoDto.setFirstName();
        } else {
            //护照类型
            userIdInfoDto.setUserName(userIdInfoDto.getFirstName() + "/" + userIdInfoDto.getLastName());

            //插入护照信息
            SysUserIdInfo sysUserIdinfo = new SysUserIdInfo(userId, userIdInfoDto);
            sysUserIdInfoMapper.insert(sysUserIdinfo);
        }

        SysUser sysUser = new SysUser(userId, userIdInfoDto, "2");
        updateById(sysUser);
    }

    @Override
    @CacheEvict(value = "user_details", key = "#userDto.userId")
    public Boolean updateUser(UserDto userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setUpdateTime(LocalDateTime.now());

        if (StrUtil.isNotBlank(userDto.getPassword())) {
            sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
        }
        this.updateById(sysUser);

        sysUserRoleService.remove(Wrappers.<SysUserRole>update().lambda()
                .eq(SysUserRole::getUserId, userDto.getUserId()));
        userDto.getRole().forEach(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUser.getUserId());
            userRole.setRoleId(roleId);
            userRole.insert();
        });
        return Boolean.TRUE;
    }

    /**
     * 查询上级组织机构的用户信息
     *
     * @param loginName 用户名
     * @return R
     */
    @Override
    public List<SysUser> listAncestorUsers(String loginName) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>query().lambda()
                .eq(SysUser::getLoginName, loginName));

        SysDept org = sysDeptService.getById(sysUser.getDeptId());
        if (org == null) {
            return null;
        }

        String parentId = org.getParentId();
        return this.list(Wrappers.<SysUser>query().lambda()
                .eq(SysUser::getDeptId, parentId));
    }

    /**
     * 获取当前用户的子组织机构信息
     *
     * @return 子组织机构列表
     */
    private List<String> getChildSysDepts() {
        String orgId = SecurityUtils.getUser().getDeptId();
        //获取当前组织机构的子组织机构
        return orgRelationService
                .list(Wrappers.<SysDeptRelation>query().lambda()
                        .eq(SysDeptRelation::getAncestor, orgId))
                .stream()
                .map(SysDeptRelation::getDescendant)
                .collect(Collectors.toList());
    }

    /**
     * 根据第三方ID查询用户信息
     *
     * @param thirdId
     * @param openId
     * @return
     */
    @Override
    public SysUser getUserByThirdId(String thirdId, String openId) {
        return baseMapper.getUserByThirdId(thirdId, openId);

    }

    /**
     * 判断sysuser表中是否用用户,没有则添加
     *
     * @param sysUserAllDto 需要添加的入住人 WQ
     * @return
     */
    @Override
    public Map<String, String> saveORupateSysuser(SysUserAllDto sysUserAllDto) {
        Map<String, String> map = new HashMap<>();
        //根据手机查询用户信息查看是否手机被注册
        SysUser sysUsers = baseMapper.selectSysuserByMobile(sysUserAllDto.getMobile());
        if (sysUsers != null) {
            //设置返回信息[3]表示,页面传进来的用户在系统中手机已注册
            map.put("message", "3");
            return map;
        }
        SysUser sysUser = this.selectSysuserByIdno(sysUserAllDto.getIdno());
        //判断时是否根据身份证查询出数据
        if (sysUser != null) {
            //判断系统记录的手机号与用户所填的手机号是否相同
            if (sysUser.getMobile().equals(sysUserAllDto.getMobile())) {
                //设置返回信息[0]表示,页面传进来的用户在系统中存在且手机号相同
                map.put("message", "0");
            } else {
                //将页面传进来的电话设置到数据库操作实体类[sysUser]库中
                sysUser.setMobile(sysUserAllDto.getMobile());
                //String passwd = RandomUtil.randomNumbers(Integer.parseInt(SecurityConstants.PASSWORD_SIZE));
                //sysUser.setPassword(passwd);
                //操作数据库修改当前对象
                SysUser a = new SysUser();
                a.setUserId(sysUser.getUserId());
                a.setMobile(sysUserAllDto.getMobile());


                baseMapper.updateById(a);
                //设置返回信息[1]表示,页面传进来的用户在系统中存在但手机号不相同
                map.put("message", "1");
            }
        } else { //表示系统中不存在此用户需要新增

            SysUser user = new SysUser(sysUserAllDto);
            //密码
            user.setPassword(RandomUtil.randomNumbers(Integer.parseInt(SecurityConstants.PASSWORD_SIZE)));
            baseMapper.insert(user);
            //设置返回信息[2]表示,页面传进来的用户在系统中不存在,但已添加成功
            map.put("message", "2");

        }
        return map;
    }


    /**
     * <p class="detail">
     * 功能:根据身份证号码查询sysuser表中数据
     * </p>
     *
     * @param idno :
     * @return sys user
     * @author WangQi
     * @date 2019.05.13 09:42:31
     */
    public SysUser selectSysuserByIdno(String idno) {
        SysUser sysUser = baseMapper.selectSysuserByIdno(idno);
        return sysUser;
    }


    @Override
    public List<String> saveCheckInPerson(CheckInPersonDto checkInPersonDto) {
        List<String> result = new ArrayList<>();
        List<CheckInPersonDto.CheckInPerson> checkInPersonList = checkInPersonDto.getCheckInPersonList();
        if (checkInPersonList == null) {
            return result;
        }
        //todo kings 替换数量 取sys_para表中数据
        if (checkInPersonList.size() > 3) {
            //不允许超过添加3个
            return result;
        }

        for (CheckInPersonDto.CheckInPerson checkInPerson : checkInPersonList) {
            //手机号存在直接返回userId,不存在插入并返回userId
            String mobile = checkInPerson.getMobile();
            SysUser sysUserExist = baseMapper.selectOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getMobile, mobile));
            if (sysUserExist != null) {
                result.add(sysUserExist.getUserId());
                continue;
            }
            SysUser sysUser4i = new UserDto();
            sysUser4i.setMobile(mobile);
            sysUser4i.setIdno(checkInPerson.getIdno());
            sysUser4i.setPassword(RandomUtil.randomNumbers(Integer.parseInt(SecurityConstants.PASSWORD_SIZE)));
            sysUser4i.setUserName(checkInPerson.getName());
            baseMapper.insert(sysUser4i);
            result.add(sysUser4i.getUserId());
        }
        return result;
    }

    @Override
    public Integer resetMobile(String userId, String mobile, String phone) {
        LocalDateTime updateTime = LocalDateTime.now();
        return baseMapper.resetMobile(userId, mobile, phone, updateTime);
    }

    @Override
    public R updateUserRealStatus(UserRealStatusDto userRealStatusDto) {
        return this.baseMapper.updateUserRealStatus(userRealStatusDto);
    }

    /**
     * 根据OpenId查询或绑定用户
     *
     * @param thirdId
     * @param openId
     * @param userName
     * @return
     */

    @Override
    public R<UserInfo> thirdInfo(String thirdId, String openId, String userName) {
        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(thirdId)) {
            return new R<>(REnum.CODE.PARAM_ERROR);
        }
        SysThird third = thirdMapper.selectOne(Wrappers.<SysThird>query()
                .lambda().eq(SysThird::getThirdId, thirdId)

        );

        SysUser user = this.getUserByThirdId(thirdId, openId);
        if (user == null) {
            return new R(REnum.CODE.USER_IS_NOT_EXIST);
        }
        return new R<UserInfo>(this.findUserInfo(user));
    }

    /**
     * 根据OpenId查询或绑定用户
     *
     * @param thirdId
     * @param openId
     * @param userName
     * @return
     */

    @Override
    public R<UserInfo> thirdOpenId(String thirdId, String openId, String userName) {
        String key = CommonConstants.DEFAULT_CODE_KEY + openId;
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        if (!redisTemplate.hasKey(key)) {
            return new R<>(REnum.CODE.PARAM_ERROR);
        }
        Object codeObj = redisTemplate.opsForValue().get(key);
        if (codeObj == null) {
            return new R<>(REnum.CODE.PARAM_ERROR);
        }

        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(thirdId)) {
            return new R<>(REnum.CODE.PARAM_ERROR);
        }
        SysThird third = thirdMapper.selectOne(Wrappers.<SysThird>query()
                .lambda().eq(SysThird::getThirdId, thirdId)

        );

        SysUser user = this.getUserByThirdId(thirdId, openId);
        if (user == null) {
            if (StringUtils.isEmpty(userName)) {
                return new R<>(REnum.CODE.NOT_BINDING);
            }
            if (userName.indexOf("@") > 0) {
                user = this.getOne(Wrappers.<SysUser>query()
                        .lambda().eq(SysUser::getEmail, userName));
            } else if (Pattern.compile("[0-9]*").matcher(userName).matches() && userName.length() == 11) {
                user = this.getOne(Wrappers.<SysUser>query()
                        .lambda().eq(SysUser::getMobile, userName));
            } else {
                user = this.getOne(Wrappers.<SysUser>query()
                        .lambda().eq(SysUser::getLoginName, userName));

            }
            if (user == null) {
                UserDto userDto = new UserDto();
                userDto.setLoginName(userName);
                userDto.setMobile(userName);
                userDto.setEmail(userName + "@iyysoft.com");
                String passwd = RandomUtil.randomNumbers(Integer.parseInt(SecurityConstants.PASSWORD_SIZE));
                userDto.setPassword(passwd);
                userDto.setDeptId(third.getDefaultDept());

                List<String> roleList = Arrays.asList(third.getDefaultRole().split(","));

                userDto.setRole(roleList);//默认角色
                this.saveUser(userDto);

                user = this.getOne(Wrappers.<SysUser>query()
                        .lambda().eq(SysUser::getMobile, userName));

            }
            userThirdMapper.removeOpenId(thirdId, openId);
            UserThird userThird = new UserThird();
            userThird.setThirdId(thirdId);
            userThird.setOpenId(openId);
            userThird.setUserId(user.getUserId());
            userThirdMapper.insert(userThird);
        }
        return new R<UserInfo>(this.findUserInfo(user));
    }


    @Override
    public Integer resetPassword(String userId, String password) {
        return baseMapper.resetPassword(userId, password);
    }

    @Override
    public Integer updateUserRole(String userId, List<String> roleList) {
        sysUserRoleService.deleteByUserId(userId);
        List<SysUserRole> userRoleList = roleList
                .stream().map(roleId -> {
                    SysUserRole userRole = new SysUserRole();
                    userRole.setUserId(userId);
                    userRole.setRoleId(roleId);
                    return userRole;
                }).collect(Collectors.toList());
        sysUserRoleService.saveBatch(userRoleList);
        return 1;
    }
}
