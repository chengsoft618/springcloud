package com.iyysoft.msdp.dp.app.dto.snap;

import com.iyysoft.msdp.dp.app.enums.snap.ReportStatusEnum;
import com.iyysoft.msdp.dp.app.enums.snap.ReportTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * @Author: 码农
 * @Date: 2019/8/27 17:02
 */
@Data
@ApiModel("随手拍-后台随手拍列表查询Dto")
public class ShotReportBackListDto {

    @ApiParam("举报人")
    private String repName;

    @ApiParam("被举报人")
    private String pubName;

    @ApiParam("处理状态,待处理-WAIT,已处理-DONE")
    private ReportStatusEnum status;

    @ApiParam("举报原因类型,垃圾广告营销-RUBBISH, 言语辱骂、骚扰、不友善-CURSE, 淫秽色情信息-SEXY, 违法有害信息-ILLEGAL, 其他-OTHER")
    private ReportTypeEnum type;







}
