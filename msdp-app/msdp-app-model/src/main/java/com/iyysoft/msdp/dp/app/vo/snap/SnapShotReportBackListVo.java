package com.iyysoft.msdp.dp.app.vo.snap;

import com.iyysoft.msdp.dp.app.entity.snap.SnapReport;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: 码农
 * @Date: 2019/8/29 13:21
 */
@ApiModel("随后拍-被举报的随手拍后台管理列表vo")
@Data
public class SnapShotReportBackListVo {


    private String reportSid;
    // private String shotSid;

    /**
     * 举报人昵称
     */
    private String repName;
    /**
     * 举报人手机号
     */
    private String repPhone;
    /**
     * 发布人（被举报人）昵称
     */
    private String pubName;

    /**
     * 标签
     */
    private String tag;
    /**
     * 举报原因类型
     */
    private String type;
    /**
     * 举报处理类型
     */
    private String status;
    private String statusVal;
    /**
     * 举报时间
     */
    private LocalDateTime reportDate;

    /**
     * 是否已经删除
     */
    private String delete;


    public static SnapShotReportBackListVo createVo(SnapReport snapReport) {
        SnapShotReportBackListVo vo = new SnapShotReportBackListVo();
        //举报人的用户信息
        vo.setReportSid(snapReport.getSid());
        vo.setRepPhone(snapReport.getRepUser() == null ? null : snapReport.getRepUser().getPhone());
        vo.setType(snapReport.getType().getValue());
        vo.setStatus(snapReport.getStatus().name());
        vo.setStatusVal(snapReport.getStatus().getValue());
        vo.setPubName(snapReport.getPubName());
        vo.setTag(snapReport.getTag());
        vo.setRepName(snapReport.getNickName());
        vo.setReportDate(snapReport.getCreateDate());
        vo.setDelete(snapReport.getDel().getCode());
        return vo;

    }


}
