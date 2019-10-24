package com.iyysoft.msdp.dp.app.service.snap.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iyysoft.msdp.dp.app.entity.snap.SnapComment;
import com.iyysoft.msdp.dp.app.mapper.SnapCommentMapper;
import com.iyysoft.msdp.dp.app.service.snap.SnapCommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 随手拍-评论
 *
 * @author iyysoft code generator
 * @date 2019-08-21 13:18:52
 */
@AllArgsConstructor
@Service
public class SnapCommentServiceImpl extends ServiceImpl<SnapCommentMapper, SnapComment> implements SnapCommentService {

    private final SnapCommentMapper snapCommentMapper;

    @Override
    public IPage<SnapComment> selectBackCommList(Page page, Wrapper ew) {
        return snapCommentMapper.selectBackCommList(page, ew);
    }

    @Override
    public List<SnapComment> selectOpenCommList(Wrapper ew) {
        return snapCommentMapper.selectOpenCommList(ew);
    }
}
