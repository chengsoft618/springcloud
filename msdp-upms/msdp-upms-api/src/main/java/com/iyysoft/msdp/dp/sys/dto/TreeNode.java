package com.iyysoft.msdp.dp.sys.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mao.chi
 * @date 2017年11月9日23:33:45
 */
@Data
public class TreeNode {
    protected String id;
    protected String parentId;
    protected List<TreeNode> children = new ArrayList<TreeNode>();

    public void add(TreeNode node) {
        children.add(node);
    }
}
