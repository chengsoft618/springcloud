package com.iyysoft.msdp.dp.sys.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author mao.chi
 * @date 2018/1/20
 * 组织机构树
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrgTree extends TreeNode {
    private String name;
}
