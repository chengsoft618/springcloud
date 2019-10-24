package com.iyysoft.msdp.dp.app.controller.snap;

import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.dp.app.enums.snap.ReportPunishObjectEnum;
import com.iyysoft.msdp.dp.app.enums.snap.ReportPunishTimeEnum;
import com.iyysoft.msdp.dp.app.enums.snap.ReportPunishTypeEnum;
import com.iyysoft.msdp.dp.app.enums.snap.ReportTypeEnum;
import com.iyysoft.msdp.dp.app.enums.snap.ShotTagEnum;
import com.iyysoft.msdp.dp.app.vo.demo.EnumVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 码农
 * @Date: 2019/8/26 16:14
 */
@Api(tags = "随手拍-公共类", value = "")
@RestController
@AllArgsConstructor
@RequestMapping("/app/v1/snapcommon")
public class CommonController {


    /**
     *
     * @return
     */
    @ApiOperation("随后拍-举报类型")
    @GetMapping("/report")
    public R reportType() {
        List<EnumVo> list = Arrays.stream(ReportTypeEnum.values()).map(qenum -> {
            EnumVo vo = new EnumVo();
            vo.setName(qenum.name());
            vo.setValue(qenum.getValue());
            return vo;
        }).collect(Collectors.toList());
        return new R<>(list);
    }

    @ApiOperation("随后拍-标签类型")
    @GetMapping("/tag")
    public R shotTagType() {
        List<EnumVo> list = Arrays.stream(ShotTagEnum.values()).map(qenum -> {
            EnumVo vo = new EnumVo();
            vo.setName(qenum.name());
            vo.setValue(qenum.getValue());
            return vo;
        }).collect(Collectors.toList());
        return new R<>(list);
    }

    @ApiOperation("随手拍-处理结果")
    @GetMapping("/dispose")
    public R shotDispose(){
        List<EnumVo> list =Arrays.stream(ReportPunishTypeEnum.values()).map(reportPunishTypeEnum -> {
            EnumVo vo =new EnumVo();
            vo.setName(reportPunishTypeEnum.name());
            vo.setValue(reportPunishTypeEnum.getValue());
            return vo;
        }).collect(Collectors.toList());
        return new R<>(list);
    }

    @ApiOperation("随手拍-处罚时间")
    @GetMapping("/time")
    public R punishTime(){
        List<EnumVo> list =Arrays.stream(ReportPunishTimeEnum.values()).map(qenum -> {
            EnumVo vo =new EnumVo();
            vo.setName(qenum.name());
            vo.setValue(qenum.getValue());
            return vo;
        }).collect(Collectors.toList());
        return new R<>(list);
    }

    @ApiOperation("随手拍-处罚对象")
    @GetMapping("/ob")
    public R relation(){
        List<EnumVo> list =Arrays.stream(ReportPunishObjectEnum.values()).map(qenum -> {
            EnumVo vo =new EnumVo();
            vo.setValue(qenum.name());
            vo.setValue(qenum.getValue());
            return vo;
        }).collect(Collectors.toList());
        return new R<>(list);
    }
}
