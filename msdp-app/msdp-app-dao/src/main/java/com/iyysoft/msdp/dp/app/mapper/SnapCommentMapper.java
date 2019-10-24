package com.iyysoft.msdp.dp.app.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iyysoft.msdp.dp.app.entity.snap.SnapComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 随手拍-评论
 *
 * @author iyysoft code generator
 * @date 2019-08-21 13:18:52
 */
public interface SnapCommentMapper extends BaseMapper<SnapComment> {


    IPage<SnapComment> selectBackCommList(Page page, @Param("ew") Wrapper wrapper);

    List<SnapComment> selectOpenCommList(@Param("ew") Wrapper ew);
}
