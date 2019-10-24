package com.iyysoft.msdp.dp.app.service.snap.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iyysoft.msdp.dp.app.entity.snap.SnapComment;
import com.iyysoft.msdp.dp.app.entity.snap.SnapLikeRecord;
import com.iyysoft.msdp.dp.app.entity.snap.SnapMedia;
import com.iyysoft.msdp.dp.app.entity.snap.SnapShotTag;
import com.iyysoft.msdp.dp.app.mapper.SnapCommentMapper;
import com.iyysoft.msdp.dp.app.mapper.SnapLikeRecordMapper;
import com.iyysoft.msdp.dp.app.mapper.SnapMediaMapper;
import com.iyysoft.msdp.dp.app.mapper.SnapReportMapper;
import com.iyysoft.msdp.dp.app.mapper.SnapShotTagMapper;
import com.iyysoft.msdp.dp.app.service.snap.SnapAsycnService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author: 码农
 * @Date: 2019/8/30 8:53
 */
@Service
@AllArgsConstructor
public class SnapAsycnServiceImpl implements SnapAsycnService {


    private final SnapLikeRecordMapper snapLikeRecordMapper;
    private final SnapCommentMapper snapCommentMapper;
    private final SnapReportMapper snapReportMapper;
    private final SnapMediaMapper snapMediaMapper;
    private final SnapShotTagMapper snapShotTagMapper;

    @Async
    @Override
    public void delShot(String shotSid) {
        //删除点赞记录
        snapLikeRecordMapper.delete(Wrappers.<SnapLikeRecord>lambdaQuery().eq(SnapLikeRecord::getShotSid,shotSid));
        //删除评论
        snapCommentMapper.delete(Wrappers.<SnapComment>lambdaQuery().eq(SnapComment::getShotSid,shotSid));
        //删除举报
        //snapReportMapper.delete(Wrappers.<SnapReport>lambdaQuery().eq(SnapReport::getShotSid,shotSid));
        //删除媒体资源
        snapMediaMapper.delete(Wrappers.<SnapMedia>lambdaQuery().eq(SnapMedia::getShotSid,shotSid));
        //删除标签
        snapShotTagMapper.delete(Wrappers.<SnapShotTag>lambdaQuery().eq(SnapShotTag::getShotSid,shotSid));
    }
}
