package com.iyysoft.msdp.dp.app.dto.snap;

import com.iyysoft.msdp.dp.app.enums.snap.ShotCheckEnum;
import com.iyysoft.msdp.dp.app.enums.snap.ShotStatusEnum;
import com.iyysoft.msdp.dp.app.enums.snap.ShotTagEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * @Author: 码农
 * @Date: 2019/8/27 17:02
 */
@Data
@ApiModel("随手拍-后台随手拍列表查询Dto")
public class ShotBackListDto {

    @ApiParam("昵称")
    private String nickName;

    @ApiParam("手机号")
    private String phone;

    @ApiParam("待审核-WAIT, 未通过-FAIL, 通过-PASS, 超期未审核-OVERDUE")
    private ShotCheckEnum check;

    @ApiParam("显示-SHOW, 隐藏-HIDE")
    private ShotStatusEnum status;

    @ApiParam("#项目公告板-BOARD，#我要曝光-EXPOSURE，#看美女-GIRLS，#工地趣事-FUNNY" +
              "，#笑话段子-JOKE，#招工找活-EMP，#APP问题-PROBLEM，#生活小记-LIFE")
    private ShotTagEnum tag;

}
