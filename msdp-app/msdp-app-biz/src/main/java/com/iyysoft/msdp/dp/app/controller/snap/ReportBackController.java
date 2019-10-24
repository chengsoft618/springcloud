package com.iyysoft.msdp.dp.app.controller.snap;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iyysoft.msdp.common.core.constant.enums.REnum;
import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.common.data.mybatis.QPage;
import com.iyysoft.msdp.common.log.annotation.SysLog;
import com.iyysoft.msdp.dp.app.dto.snap.ShotReportBackListDto;
import com.iyysoft.msdp.dp.app.entity.snap.SnapShot;
import com.iyysoft.msdp.dp.app.enums.RAppREnum;
import com.iyysoft.msdp.dp.app.enums.snap.ReportPunishObjectEnum;
import com.iyysoft.msdp.dp.app.enums.snap.ReportPunishTimeEnum;
import com.iyysoft.msdp.dp.app.enums.snap.ReportStatusEnum;
import com.iyysoft.msdp.dp.app.enums.snap.UserStatusEnum;
import com.iyysoft.msdp.dp.app.vo.snap.SnapShotReportBackDetailVo;
import com.iyysoft.msdp.dp.app.vo.snap.SnapShotReportBackListVo;
import com.iyysoft.msdp.dp.app.entity.snap.SnapMedia;
import com.iyysoft.msdp.dp.app.entity.snap.SnapReport;
import com.iyysoft.msdp.dp.app.entity.snap.SnapUser;
import com.iyysoft.msdp.dp.app.enums.WhetherEnum;
import com.iyysoft.msdp.dp.app.manager.SnapRdManager;
import com.iyysoft.msdp.dp.app.service.snap.SnapMediaService;
import com.iyysoft.msdp.dp.app.service.snap.SnapReportService;
import com.iyysoft.msdp.dp.app.service.snap.SnapShotService;
import com.iyysoft.msdp.dp.app.service.snap.SnapShotTagService;
import com.iyysoft.msdp.dp.app.service.snap.SnapUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: 码农
 * @Date: 2019/8/27 15:06
 */
@Api(tags = "随手拍-被举报的随手拍后台管理", value = "")
@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/app/v1/reportback")
public class ReportBackController {

    private final SnapShotService snapShotService;
    private final SnapReportService snapReportService;
    private final SnapMediaService snapMediaService;
    private final SnapUserService snapUserService;
    private final SnapShotTagService snapShotTagService;
    private final SnapRdManager snapRdManager;

    @ApiOperation("被举报的随手拍列表")
    @GetMapping
    public R list(@ApiParam("分页对象") QPage page, ShotReportBackListDto dto) {
        IPage<SnapReport> iPage = snapReportService.selectReportShotList(page, dto);
        return new R<>(iPage.convert(SnapShotReportBackListVo::createVo));
    }

    @ApiOperation("随手拍详情")
    @GetMapping("/detail/{reportSid}")
    public R detail(@PathVariable String reportSid) {
        SnapReport snapReport = snapReportService.getById(reportSid);
        if (snapReport == null || snapReport.getDel() == WhetherEnum.YES) {
            return new R(REnum.CODE.FAILED);
        }
        String shotSid = snapReport.getShotSid();
        SnapShot snapShot = snapShotService.getById(shotSid);
        if (snapShot == null) {
            return new R(REnum.CODE.FAILED);
        }
        //被举报人
        SnapUser pubUser = (SnapUser) snapUserService.getSnapUser(snapReport.getPubId()).getData();
        //举报人
        SnapUser repUser = (SnapUser) snapUserService.getSnapUser(snapReport.getCreateId()).getData();
        //媒体资源
        List<SnapMedia> snapMediaList = snapMediaService.list(
                Wrappers.<SnapMedia>lambdaQuery()
                        .eq(SnapMedia::getShotSid, shotSid)
        );
        return new R<>(SnapShotReportBackDetailVo.createVo(snapShot, snapReport, pubUser, repUser, snapMediaList));
    }

    @SysLog("删除随手拍")
    @ApiOperation("删除随手拍")
    @DeleteMapping("/{reportSid}")
    //@PreAuthorize("@pms.hasPermission('generator_snapshot_del') or hasRole('msdp')")
    public R delete(@PathVariable String reportSid) {
        SnapReport snapReport = snapReportService.getById(reportSid);
        if (snapReport == null) {
            return new R(REnum.CODE.FAILED);
        }
        String shotSid = snapReport.getShotSid();
        snapReportService.deleteReport(shotSid, reportSid);
        return new R();
    }

    @SysLog("忽略举报")
    @ApiOperation("忽略举报")
    @PutMapping("/ignore/{reportSid}")
    //@PreAuthorize("@pms.hasPermission('generator_snapshot_add') or hasRole('msdp'))")
    public R like(@PathVariable String reportSid) {
        SnapReport snapReport = snapReportService.getById(reportSid);
        if(snapReport == null){
            return new R(REnum.CODE.FAILED);
        }
        snapReportService.ingoreReport(snapReport.getShotSid(), reportSid);
        return new R();
    }

    @SysLog("处理举报")
    @ApiOperation("处理举报")
    @PutMapping("/dispose/{reportSid}")
    public R dispose(@PathVariable String reportSid,
                     @ApiParam("处罚举报人-PLAINTIFF，处罚被举报人-ACCUSED")@RequestParam ReportPunishObjectEnum ob,
                     @ApiParam("封号-DISABLE，禁言-BAN")@RequestParam UserStatusEnum status,
                     @ApiParam("24小时-ONEDAY，7天-WEEK，一个月-MONTH，永久-FOREVER")@RequestParam ReportPunishTimeEnum time) {

        SnapReport snapReport = snapReportService.getById(reportSid);
        if (snapReport == null ||  snapReport.getDel() == WhetherEnum.YES) {
            return new R(REnum.CODE.FAILED);
        }
        snapReportService.dispoceReport(snapReport, ob, status, time);
        return new R<>();
    }

    @SysLog("取消处罚")
    @ApiOperation("取消处罚")
    @PutMapping("/cancal/{reportSid}")
    public R cancal(@PathVariable String reportSid) {

        SnapReport snapReport = snapReportService.getById(reportSid);
        if (snapReport == null || snapReport.getPunish() == WhetherEnum.NO ||  snapReport.getDel() == WhetherEnum.YES) {
            return new R<>(RAppREnum.PUNISH_CANCEL);
        }
        String userId;
        if (snapReport.getPunishObject() == ReportPunishObjectEnum.PLAINTIFF) {
            userId = snapReport.getCreateId();
        } else {
            SnapShot snapShot = snapShotService.getById(snapReport.getShotSid());
            userId = snapShot.getCreateId();
        }
        snapReportService.cancalPunish(reportSid, userId);
        return new R();
    }


}
