package com.iyysoft.msdp.dp.sys.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iyysoft.msdp.dp.sys.entity.SysDict;
import com.iyysoft.msdp.dp.sys.entity.SysDictItem;
import com.iyysoft.msdp.dp.sys.mapper.SysDictItemMapper;
import com.iyysoft.msdp.dp.sys.mapper.SysDictMapper;
import com.iyysoft.msdp.dp.sys.service.SysDictService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 字典表
 *
 * @author mao.chi
 * @date 2019/03/19
 */
@Service
@AllArgsConstructor
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {
    private final SysDictItemMapper dictItemMapper;

    /**
     * 根据ID 删除字典
     *
     * @param dictId
     * @return
     */
    @Override
    @CacheEvict(value = "dict_details", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeDict(String dictId) {
        baseMapper.deleteById(dictId);

        dictItemMapper.delete(Wrappers.<SysDictItem>lambdaQuery()
                .eq(SysDictItem::getDictId, dictId));
        return Boolean.TRUE;
    }
}
