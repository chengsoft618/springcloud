package com.iyysoft.msdp.dp.app.dto.snap;

import com.iyysoft.msdp.dp.app.enums.snap.ShotTagEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: 码农
 * @Date: 2019/8/22 8:44
 */
@Data
@ApiModel("随手拍的发布的Dto")
public class ShotAddDto {

    @ApiModelProperty(value = "描述")
    @NotBlank(message = "【描述】不能为空.")
    @Length(max = 200, message = "【描述】长度太长")
    private String content;

    @ApiModelProperty(value = "地点")
    // @NotBlank(message = "【地点】不能为空.")
    @Length(max = 100, message = "【地点】长度太长")
    private String address;

    @ApiModelProperty(value = "经度")
    // @NotNull(message = "【经度】不能为空.")
    private BigDecimal lng;

    @ApiModelProperty(value = "纬度")
    // @NotNull(message = "【纬度】不能为空.")
    private BigDecimal lat;

    @ApiModelProperty("标签，string的数组，#项目公告板-BOARD，#我要曝光-EXPOSURE，#看美女-GIRLS，#工地趣事-FUNNY，#笑话段子-JOKE，#招工找活-EMP，#APP问题-PROBLEM，#生活小记-LIFE")
    @NotNull(message = "【标签】不能为null")
    @Size(min = 1, max = 3, message = "【标签】长度1-3")
    private List<ShotTagEnum> tag;

    @Valid
    @ApiModelProperty(value = "图片地址数组")
    @NotNull(message = "【图片】不能为null")
    @Size(min = 1, max = 9, message = "【图片】长度0-9")
    private List<MediaDto> media;

    @Data
    public static class MediaDto{
        @NotBlank(message = "【url】不能为空.")
        private String url;
        private String hdUrl;
    }
}
