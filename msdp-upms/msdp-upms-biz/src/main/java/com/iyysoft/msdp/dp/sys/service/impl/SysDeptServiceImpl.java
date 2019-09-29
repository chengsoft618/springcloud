package com.iyysoft.msdp.dp.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.iyysoft.msdp.dp.sys.dto.OrgTree;
import com.iyysoft.msdp.dp.sys.entity.SysDept;
import com.iyysoft.msdp.dp.sys.entity.SysDeptRelation;
import com.iyysoft.msdp.dp.sys.mapper.SysDeptMapper;
import com.iyysoft.msdp.dp.sys.service.SysDeptRelationService;
import com.iyysoft.msdp.dp.sys.service.SysDeptService;
import com.iyysoft.msdp.dp.sys.vo.TreeUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 部门管理 服务实现类
 * </p>
 *
 * @author mao.chi
 * @since 2018-01-20
 */
@Service
@AllArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {
    private final SysDeptRelationService sysDeptRelationService;

    /**
     * 添加信息部门
     *
     * @param dept 部门
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveDept(SysDept dept) {
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(dept, sysDept);
        this.save(sysDept);
        sysDeptRelationService.insertDeptRelation(sysDept);
        return Boolean.TRUE;
    }


    /**
     * 删除部门
     *
     * @param id 部门 ID
     * @return 成功、失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeDeptById(String id) {
        //级联删除部门
        List<String> idList = sysDeptRelationService
                .list(Wrappers.<SysDeptRelation>query().lambda()
                        .eq(SysDeptRelation::getAncestor, id))
                .stream()
                .map(SysDeptRelation::getDescendant)
                .collect(Collectors.toList());

        if (CollUtil.isNotEmpty(idList)) {
            this.removeByIds(idList);
        }

        //删除部门级联关系
        sysDeptRelationService.deleteAllDeptRealtion(id);
        return Boolean.TRUE;
    }

    /**
     * 更新部门
     *
     * @param sysDept 部门信息
     * @return 成功、失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateDeptById(SysDept sysDept) {
        //更新部门状态
        this.updateById(sysDept);
        //更新部门关系
        SysDeptRelation relation = new SysDeptRelation();
        relation.setAncestor(sysDept.getParentId());
        relation.setDescendant(sysDept.getDeptId());
        sysDeptRelationService.updateDeptRealtion(relation);
        return Boolean.TRUE;
    }

    /**
     * 查询全部部门树
     *
     * @return 树
     */
    @Override
    public List<OrgTree> selectTree() {
        return getDeptTree(this.list(Wrappers.emptyWrapper()));
    }


    /**
     * 构建部门树
     *
     * @param depts 部门
     * @return
     */
    private List<OrgTree> getDeptTree(List<SysDept> depts) {
        List<OrgTree> treeList = depts.stream()
                .filter(dept -> !dept.getDeptId().equals(dept.getParentId()))
                .map(dept -> {
                    OrgTree node = new OrgTree();
                    node.setId(dept.getDeptId());
                    node.setParentId(dept.getParentId());
                    node.setName(dept.getName());
                    return node;
                }).collect(Collectors.toList());
        return TreeUtil.build(treeList, "0");
    }
}
