package com.iyysoft.msdp.dp.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iyysoft.msdp.dp.sys.entity.SysDict;

/**
 * 字典表
 *
 * @author mao.chi
 * @date 2019/03/19
 */
public interface SysDictService extends IService<SysDict> {

    /**
     * 根据ID 删除字典
     *
     * @param dictId
     * @return
     */
    Boolean removeDict(String dictId);
}
