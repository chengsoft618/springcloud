package com.iyysoft.msdp.dp.app.service.snap.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iyysoft.msdp.common.core.constant.enums.REnum;
import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.common.data.mybatis.QPage;
import com.iyysoft.msdp.common.security.service.MsdpUser;
import com.iyysoft.msdp.common.security.util.SecurityUtils;
import com.iyysoft.msdp.dp.app.dto.snap.ShotAddDto;
import com.iyysoft.msdp.dp.app.dto.snap.ShotBackListDto;
import com.iyysoft.msdp.dp.app.enums.RAppREnum;
import com.iyysoft.msdp.dp.app.enums.snap.ReportStatusEnum;
import com.iyysoft.msdp.dp.app.enums.snap.ReportTypeEnum;
import com.iyysoft.msdp.dp.app.enums.snap.ShotStatusEnum;
import com.iyysoft.msdp.dp.app.enums.snap.ShotTagEnum;
import com.iyysoft.msdp.dp.app.mapper.SnapShotMapper;
import com.iyysoft.msdp.dp.app.mq.SnapBufferSender;
import com.iyysoft.msdp.dp.app.vo.snap.SnapShotAppVo;
import com.iyysoft.msdp.dp.app.entity.snap.SnapComment;
import com.iyysoft.msdp.dp.app.entity.snap.SnapLikeRecord;
import com.iyysoft.msdp.dp.app.entity.snap.SnapMedia;
import com.iyysoft.msdp.dp.app.entity.snap.SnapReport;
import com.iyysoft.msdp.dp.app.entity.snap.SnapShot;
import com.iyysoft.msdp.dp.app.entity.snap.SnapShotTag;
import com.iyysoft.msdp.dp.app.entity.snap.SnapUser;
import com.iyysoft.msdp.dp.app.enums.WhetherEnum;
import com.iyysoft.msdp.dp.app.manager.SnapRdManager;
import com.iyysoft.msdp.dp.app.service.snap.SnapCommentService;
import com.iyysoft.msdp.dp.app.service.snap.SnapLikeRecordService;
import com.iyysoft.msdp.dp.app.service.snap.SnapMediaService;
import com.iyysoft.msdp.dp.app.service.snap.SnapReportService;
import com.iyysoft.msdp.dp.app.service.snap.SnapShotService;
import com.iyysoft.msdp.dp.app.service.snap.SnapShotTagService;
import com.iyysoft.msdp.dp.app.service.snap.SnapUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 随手拍-发布的随手拍
 *
 * @author iyysoft code generator
 * @date 2019-08-21 13:18:48
 */
@Slf4j
@Service
@AllArgsConstructor
public class SnapShotServiceImpl extends ServiceImpl<SnapShotMapper, SnapShot> implements SnapShotService {

    private final SnapShotTagService snapShotTagService;
    private final SnapMediaService snapMediaService;
    private final SnapUserService snapUserService;
    private final SnapCommentService snapCommentService;
    private final SnapLikeRecordService snapLikeRecordService;
    private final SnapReportService snapReportService;
    private final SnapShotMapper snapShotMapper;

    private final SnapRdManager redisManager;
    private final SnapBufferSender snapShotBufferSender;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R publishShot(ShotAddDto shotAddDto, String userId, String nickName) {

        //todo 敏感词检测
        //随手拍
        SnapShot snapShot = new SnapShot();
        snapShot.setContent(shotAddDto.getContent());
        snapShot.setAddress(shotAddDto.getAddress());
        snapShot.setLng(shotAddDto.getLng());
        snapShot.setLat(shotAddDto.getLat());
        snapShot.setCreateId(userId);
        snapShot.setUpdateId(userId);
        snapShot.setNickName(nickName);
        snapShot.setMedianum(shotAddDto.getMedia().size());
        snapShot.setTag(
                shotAddDto.getTag().stream().map(ShotTagEnum::getValue).collect(Collectors.joining(";"))
        );
        this.save(snapShot);
        //标签
        List<SnapShotTag> snapShotTagList = shotAddDto.getTag().stream().map(tag -> {
            SnapShotTag shotTag = new SnapShotTag();
            shotTag.setShotSid(snapShot.getSid());
            shotTag.setTag(tag);
            shotTag.setCreateId(userId);
            return shotTag;
        }).collect(Collectors.toList());

        snapShotTagService.saveBatch(snapShotTagList);
        //图片
        List<ShotAddDto.MediaDto> mediaList = shotAddDto.getMedia();
        List<SnapMedia> snapMediaList = mediaList.stream().map(media -> {
            SnapMedia snapMedia = new SnapMedia();
            snapMedia.setShotSid(snapShot.getSid());
            snapMedia.setUrl(media.getUrl());
            snapMedia.setHdUrl(media.getHdUrl());
            snapMedia.setCreateId(userId);
            return snapMedia;
        }).collect(Collectors.toList());
        snapMediaService.saveBatch(snapMediaList);
        //延迟30分钟的任务
        snapShotBufferSender.send(snapShot.getSid());
        return new R();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R like(String shotSid, String userId, String nickName) {
        SnapLikeRecord snapLikeRecord = snapLikeRecordService.getOne(
                Wrappers.<SnapLikeRecord>lambdaQuery()
                        .select(SnapLikeRecord::getSid)
                        .eq(SnapLikeRecord::getShotSid, shotSid)
                        .eq(SnapLikeRecord::getCreateId, userId)
        );
        SnapShot snapShot = snapShotMapper.selectOne(
                Wrappers.<SnapShot>lambdaQuery()
                        .select(SnapShot::getSid, SnapShot::getLikenum, SnapShot::getStatus, SnapShot::getVersion)
                        .eq(SnapShot::getSid, shotSid)
        );
        if (snapShot == null || snapShot.getStatus() == ShotStatusEnum.HIDE) {
            return new R(RAppREnum.SHOT_DELETED);
        }
        if (snapLikeRecord == null) {
            //点赞
            SnapShot record0 = new SnapShot();
            record0.setSid(snapShot.getSid());
            record0.setLikenum(snapShot.getLikenum() + 1);
            record0.setVersion(snapShot.getVersion());
            if (!this.updateById(record0)) {
                return new R<>(RAppREnum.CODE.FAILED);
            }
            SnapLikeRecord record1 = new SnapLikeRecord();
            record1.setShotSid(snapShot.getSid());
            record1.setCreateId(userId);
            record1.setNickName(nickName);
            boolean a = snapLikeRecordService.save(record1);
        } else {
            //取消点赞
            SnapShot record = new SnapShot();
            record.setSid(snapShot.getSid());
            record.setLikenum(snapShot.getLikenum() - 1);
            record.setVersion(snapShot.getVersion());
            if (!this.updateById(record)) {
                return new R<>(RAppREnum.CODE.FAILED);
            }
            boolean a = snapLikeRecordService.removeById(snapLikeRecord.getSid());
        }
        return new R<>();
    }

    @Override
    public R likef(String shotSid, int likenumf) {
        SnapShot snapShot = snapShotMapper.selectById(shotSid);
        if (snapShot == null) {
            return new R(RAppREnum.SHOT_DELETED);
        }
        SnapShot record = new SnapShot();
        record.setSid(snapShot.getSid());
        record.setLikenumf(snapShot.getLikenumf() + likenumf);
        record.setVersion(snapShot.getVersion());
        if (!this.updateById(record)) {
            return new R<>("点赞失败,请重试", RAppREnum.CODE.FAILED);
        }
        return new R<>();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R notComm(String commSid) {
        SnapComment snapComment = snapCommentService.getById(commSid);
        if (snapComment == null) {
            return new R<>(RAppREnum.CODE.FAILED);
        }
        SnapShot snapShot = this.getById(snapComment.getShotSid());
        SnapShot record = new SnapShot();
        record.setSid(snapShot.getSid());
        record.setCommnum(snapShot.getCommnum() - 1);
        record.setVersion(snapShot.getVersion());
        if (!this.updateById(record)) {
            return new R<>(RAppREnum.CODE.FAILED);
        }
        snapCommentService.removeById(commSid);
        return new R<>();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R comm(SnapShot snapShot, SnapUser snapUser, String comment) {
        SnapShot record = new SnapShot();
        record.setSid(snapShot.getSid());
        record.setCommnum(snapShot.getCommnum() + 1);
        record.setVersion(snapShot.getVersion());
        if (!this.updateById(record)) {
            return new R<>(RAppREnum.CODE.FAILED);
        }
        SnapComment snapComment = new SnapComment();
        snapComment.setShotSid(snapShot.getSid());
        snapComment.setComment(comment);
        snapComment.setNickName(snapUser.getNickName());
        snapComment.setCreateId(snapUser.getUserId());
        snapComment.setUpdateId(snapUser.getUserId());
        snapCommentService.save(snapComment);
        return new R<>();
    }

    @Override
    public IPage<SnapShot> selectBackShotList(ShotBackListDto dto, QPage qPage) {
        QueryWrapper<SnapShot> ew = new QueryWrapper<>();
        Page<SnapShot> page = new Page<>(qPage.getPageNum(), qPage.getPageSize());

        if (dto.getCheck() != null) {
            ew.eq("ss.`check`", dto.getCheck().getCode());
        }
        if (dto.getStatus() != null) {
            ew.eq("ss.`status`", dto.getStatus().getCode());
        }
        if (StrUtil.isNotBlank(dto.getNickName())) {
            ew.eq("ss.nick_name", dto.getNickName());
        }
        if (StrUtil.isNotBlank(dto.getPhone())) {
            ew.eq("su.phone", dto.getPhone());
        }
        ew.orderByDesc("ss.create_date");
        if (dto.getTag() == null) {
            snapShotMapper.selectBackShotList(page, ew);
        } else {
            snapShotMapper.selectBackShotListByTag(page, ew, dto.getTag().getCode());
        }
        return page;
    }

    @Override
    public IPage<SnapShotAppVo> selectAppShotList(Boolean onlyme, ShotTagEnum shotTag, QPage qPage, String userId) {

        QueryWrapper<SnapShot> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("t.status", ShotStatusEnum.SHOW.getCode());
        IPage<SnapShot> snapShotIPage = new Page<>();
        List<SnapShot> snapShotList = new ArrayList<>();
        //看全部 无标签
        if (!onlyme && shotTag == null) {
            snapShotList = snapShotMapper.selectAppShotList(queryWrapper, qPage.getBegin(), qPage.getLength(), userId);
        }
        //只看我的 无标签
        if (onlyme && shotTag == null) {
            queryWrapper.eq("t.create_id", userId);
            snapShotList = snapShotMapper.selectAppShotList(queryWrapper, qPage.getBegin(), qPage.getLength(), userId);
        }
        //看全部 有标签
        if (!onlyme && shotTag != null) {
            snapShotList = snapShotMapper.selectAppShotListByTag(queryWrapper, shotTag.getCode(), qPage.getBegin(), qPage.getLength(), userId);
        }
        //只看我的 有标签
        if (onlyme && shotTag != null) {
            queryWrapper.eq("t.create_id", userId);
            snapShotList = snapShotMapper.selectAppShotListByTag(queryWrapper, shotTag.getCode(), qPage.getBegin(), qPage.getLength(), userId);
        }
        snapShotIPage.setRecords(snapShotList);
        snapShotIPage.setSize(snapShotList.size());
        return snapShotIPage.convert(snapShot -> SnapShotAppVo.create(snapShot, userId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R report(SnapShot snapShot, ReportTypeEnum reportType, SnapUser repUser, SnapUser pubUser) {
        //24小时内不可重复举报
        SnapReport record= snapReportService.getOne(
                Wrappers.<SnapReport>lambdaQuery()
                        .select(SnapReport::getSid,SnapReport::getCreateDate)
                        .eq(SnapReport::getShotSid, snapShot.getSid())
                        .eq(SnapReport::getCreateId, repUser.getUserId())
        );
        if (record != null && record.getCreateDate().plusHours(24L).isAfter(LocalDateTime.now())) {
            return new R<>(RAppREnum.REPORT_REPEAT);
        }
        SnapReport snapReport = new SnapReport();
        snapReport.setShotSid(snapShot.getSid());
        snapReport.setType(reportType);
        snapReport.setNickName(repUser.getNickName());
        snapReport.setStatus(ReportStatusEnum.WAIT);
        snapReport.setCreateId(repUser.getUserId());
        snapReport.setUpdateId(repUser.getUserId());
        snapReport.setIgno(WhetherEnum.NO);
        snapReport.setDel(WhetherEnum.NO);
        snapReport.setPunish(WhetherEnum.NO);
        snapReport.setPubId(pubUser.getUserId());
        snapReport.setPubName(pubUser.getNickName());
        snapReport.setTag(snapShot.getTag());
        snapReportService.save(snapReport);
        this.update(
                Wrappers.<SnapShot>lambdaUpdate()
                        .set(SnapShot::getStatus, ShotStatusEnum.HIDE)
                        .eq(SnapShot::getSid, snapShot.getSid())
        );
        return new R();
    }


}
