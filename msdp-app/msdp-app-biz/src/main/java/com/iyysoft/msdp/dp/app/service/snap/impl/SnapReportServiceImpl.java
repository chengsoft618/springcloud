package com.iyysoft.msdp.dp.app.service.snap.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iyysoft.msdp.common.data.mybatis.QPage;
import com.iyysoft.msdp.dp.app.dto.snap.ShotReportBackListDto;
import com.iyysoft.msdp.dp.app.entity.snap.SnapReport;
import com.iyysoft.msdp.dp.app.entity.snap.SnapShot;
import com.iyysoft.msdp.dp.app.entity.snap.SnapUser;
import com.iyysoft.msdp.dp.app.mapper.SnapReportMapper;
import com.iyysoft.msdp.dp.app.mapper.SnapShotMapper;
import com.iyysoft.msdp.dp.app.mapper.SnapUserMapper;
import com.iyysoft.msdp.dp.app.enums.WhetherEnum;
import com.iyysoft.msdp.dp.app.enums.snap.ReportPunishObjectEnum;
import com.iyysoft.msdp.dp.app.enums.snap.ReportPunishTimeEnum;
import com.iyysoft.msdp.dp.app.enums.snap.ReportStatusEnum;
import com.iyysoft.msdp.dp.app.enums.snap.ShotStatusEnum;
import com.iyysoft.msdp.dp.app.enums.snap.UserStatusEnum;
import com.iyysoft.msdp.dp.app.manager.SnapRdManager;
import com.iyysoft.msdp.dp.app.service.snap.SnapAsycnService;
import com.iyysoft.msdp.dp.app.service.snap.SnapReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 随手拍-举报
 *
 * @author iyysoft code generator
 * @date 2019-08-21 13:18:55
 */
@Service
@AllArgsConstructor
public class SnapReportServiceImpl extends ServiceImpl<SnapReportMapper, SnapReport> implements SnapReportService {

    private final SnapReportMapper snapReportMapper;
    private final SnapShotMapper snapShotMapper;
    private final SnapUserMapper snapUserMapper;
    private final SnapRdManager snapRdManager;
    private final SnapAsycnService snapAsycnService;

    @Override
    public IPage<SnapReport> selectReportShotList(QPage qPage, ShotReportBackListDto dto) {
        QueryWrapper<SnapReport> wrapper = new QueryWrapper<>();
        Page<SnapReport> page = new Page<>(qPage.getPageNum(), qPage.getPageSize());
        if (dto.getType() != null) {
            wrapper.eq("sr.type", dto.getType().getCode());
        }
        if (dto.getStatus() != null) {
            wrapper.eq("sr.status", dto.getStatus().getCode());
        }
        if (StrUtil.isNotBlank(dto.getRepName())) {
            wrapper.eq("sr.nick_name", dto.getRepName());
        }
        if (StrUtil.isNotBlank(dto.getPubName())) {
            wrapper.eq("sr.pub_name", dto.getPubName());
        }
        snapReportMapper.selectReportShotList(page, wrapper);
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteReport(String shotSid, String reportSid) {
        snapShotMapper.deleteById(shotSid);
        this.update(
                Wrappers.<SnapReport>lambdaUpdate()
                        .set(SnapReport::getStatus, ReportStatusEnum.DONE)
                        .set(SnapReport::getDel, WhetherEnum.YES)
                        .eq(SnapReport::getSid, reportSid)
        );
        snapAsycnService.delShot(shotSid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void ingoreReport(String shotSid, String reportSid) {
        this.update(
                Wrappers.<SnapReport>lambdaUpdate()
                        .set(SnapReport::getStatus, ReportStatusEnum.DONE)
                        .set(SnapReport::getIgno, WhetherEnum.YES)
                        .eq(SnapReport::getSid, reportSid)
        );
        SnapShot record = new SnapShot();
        record.setSid(shotSid);
        record.setStatus(ShotStatusEnum.SHOW);
        snapShotMapper.updateById(record);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dispoceReport(SnapReport snapReport, ReportPunishObjectEnum ob, UserStatusEnum status, ReportPunishTimeEnum time) {
        String userId = null;
        if (ReportPunishObjectEnum.PLAINTIFF.equals(ob)) {
            //举报人
            userId = snapReport.getCreateId();
        } else {
            //被举报人
            SnapShot snapShot = snapShotMapper.selectById(snapReport.getShotSid());
            userId = snapShot.getCreateId();
        }

        SnapUser record = new SnapUser();
        record.setStatus(status);
        record.setFreeTime(LocalDateTime.now().plusDays(time.getCode()));
        snapUserMapper.update(
                record,
                Wrappers.<SnapUser>lambdaUpdate()
                        .eq(SnapUser::getUserId, userId)
        );
        snapRdManager.snapUserDel(userId);

        this.update(
                Wrappers.<SnapReport>lambdaUpdate()
                        .set(SnapReport::getStatus, ReportStatusEnum.DONE)
                        .set(SnapReport::getPunish, WhetherEnum.YES)
                        .set(SnapReport::getPunishObject, ob)
                        .eq(SnapReport::getSid, snapReport.getSid())
        );

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancalPunish(String reportSid, String userId) {
        this.update(
                Wrappers.<SnapReport>lambdaUpdate()
                        .set(SnapReport::getPunish, WhetherEnum.NO)
                        .eq(SnapReport::getSid, reportSid)
        );
        SnapUser record = new SnapUser();
        record.setStatus(UserStatusEnum.NORMAL);
        snapUserMapper.update(
                record,
                Wrappers.<SnapUser>lambdaUpdate()
                        .set(SnapUser::getStatus, UserStatusEnum.NORMAL)
                        .eq(SnapUser::getUserId, userId)
        );
        snapRdManager.snapUserDel(userId);
    }
}
