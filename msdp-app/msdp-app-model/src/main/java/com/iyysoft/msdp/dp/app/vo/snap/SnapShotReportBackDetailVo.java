package com.iyysoft.msdp.dp.app.vo.snap;

import com.iyysoft.msdp.dp.app.entity.snap.SnapMedia;
import com.iyysoft.msdp.dp.app.entity.snap.SnapReport;
import com.iyysoft.msdp.dp.app.entity.snap.SnapShot;
import com.iyysoft.msdp.dp.app.entity.snap.SnapUser;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 码农
 * @Date: 2019/8/29 13:21
 */
@ApiModel("随后拍-被举报的随手拍后台管理详情vo")
@Data
public class SnapShotReportBackDetailVo {

    private String shotSid;
    private String reportSid;
    /**
     * 发布人昵称
     */
    private String pubName;
    /**
     * 发布人实名
     */
    private String pubRealName;
    /**
     * 实名照片
     */
    private String pubRealPhoto;
    /**
     * 发布内容
     */
    private String content;
    /**
     * 头像照片
     */
    private String pubHead;
    /**
     * 手机号
     */
    private String pubPhone;
    /**
     * 发布时间
     */
    private LocalDateTime createDate;
    /**
     * 照片
     */
    private List<String> mediaList;
    /**
     * 随手拍-审核状态
     */
    private String shotCheck;
    private String shotCheckVal;
    /**
     * 随手拍-显示状态
     */
    private String shotStatus;
    private String shotStatusVal;
    /**
     *  处理状态
     */
    private String status;
    private String statusVal;
    /**
     * 标签
     */
    private String tag;

    //举报人姓名
    private String repName;
    //举报人 电话
    private String repPhone;
    // 举报原因
    private String repType;
    //举报时间
    private LocalDateTime reportTime;
    /**
     * 处理结果
     */
    private String delete;
    private String ingore;
    private String punish;

    public static SnapShotReportBackDetailVo createVo(SnapShot snapShot, SnapReport snapReport, SnapUser pubUser, SnapUser repUser, List<SnapMedia> snapMediaList) {
        SnapShotReportBackDetailVo vo = new SnapShotReportBackDetailVo();
        vo.setShotSid(snapShot.getSid());
        vo.setReportSid(snapReport.getSid());
        vo.setStatus(snapReport.getStatus().name());
        vo.setStatusVal(snapReport.getStatus().getValue());
        vo.setPubName(snapShot.getNickName());
        if(pubUser != null){
            vo.setPubRealName(pubUser.getRealName());
            vo.setPubRealPhoto(pubUser.getRealPhoto());
            vo.setPubHead(pubUser.getHead());
            vo.setPubPhone(pubUser.getPhone());
        }
        if(repUser != null){
            vo.setRepName(repUser.getRealName());
            vo.setRepPhone(repUser.getPhone());
        }
        vo.setCreateDate(snapShot.getCreateDate());
        vo.setContent(snapShot.getContent());
        vo.setShotStatus(snapShot.getStatus().name());
        vo.setShotStatusVal(snapShot.getStatus().getValue());
        vo.setShotCheck(snapShot.getCheck().name());
        vo.setShotCheckVal(snapShot.getCheck().getValue());
        vo.setTag(snapReport.getTag());
        vo.setMediaList(
                snapMediaList.stream().map(SnapMedia::getUrl).collect(Collectors.toList())
        );
        vo.setRepType(snapReport.getType().getValue());
        vo.setReportTime(snapReport.getCreateDate());
        vo.setDelete(snapReport.getDel().getCode());
        vo.setIngore(snapReport.getIgno().getCode());
        vo.setPunish(snapReport.getPunish().getCode());
        return vo;

    }


}
