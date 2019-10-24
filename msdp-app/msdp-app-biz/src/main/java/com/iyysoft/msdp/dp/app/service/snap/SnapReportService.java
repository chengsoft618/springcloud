package com.iyysoft.msdp.dp.app.service.snap;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iyysoft.msdp.common.data.mybatis.QPage;
import com.iyysoft.msdp.dp.app.dto.snap.ShotReportBackListDto;
import com.iyysoft.msdp.dp.app.entity.snap.SnapReport;
import com.iyysoft.msdp.dp.app.enums.snap.ReportPunishObjectEnum;
import com.iyysoft.msdp.dp.app.enums.snap.ReportPunishTimeEnum;
import com.iyysoft.msdp.dp.app.enums.snap.UserStatusEnum;

/**
 * 随手拍-举报
 *
 * @author iyysoft code generator
 * @date 2019-08-21 13:18:55
 */
public interface SnapReportService extends IService<SnapReport> {

    /**
     *
     * @param page
     * @param dto
     * @return
     */
    IPage<SnapReport> selectReportShotList(QPage page, ShotReportBackListDto dto);

    /**
     *
     * @param shotSid
     * @param reportSid
     */
    void deleteReport(String shotSid, String reportSid);

    /**
     *
     * @param shotSid
     * @param reportSid
     */
    void ingoreReport(String shotSid, String reportSid);

    /**
     *
     * @param snapReport
     * @param ob
     * @param status
     * @param time
     */
    void dispoceReport(SnapReport snapReport, ReportPunishObjectEnum ob, UserStatusEnum status, ReportPunishTimeEnum time);


    /**
     *
     * @param reportSid 举报ID
     * @param userId 被取消处罚用户的ID
     */
    void cancalPunish(String reportSid, String userId);
}
