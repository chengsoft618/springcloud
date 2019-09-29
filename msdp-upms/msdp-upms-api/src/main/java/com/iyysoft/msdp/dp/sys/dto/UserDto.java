package com.iyysoft.msdp.dp.sys.dto;

import com.iyysoft.msdp.dp.sys.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author mao.chi
 * @date 2017/11/5
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDto extends SysUser {
    /**
     * 角色ID
     */
    private List<String> role;

    private String orgId;

    /**
     * 新密码
     */
    private String newpassword1;
}
