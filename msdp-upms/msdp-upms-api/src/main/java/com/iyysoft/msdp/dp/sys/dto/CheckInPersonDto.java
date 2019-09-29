package com.iyysoft.msdp.dp.sys.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("入住人信息集合")
public class CheckInPersonDto implements Serializable {

    @Valid
    @ApiModelProperty("入住人集合")
    private List<CheckInPerson> checkInPersonList;

    @Data
    public static class CheckInPerson implements Serializable {
        private static final long serialVersionUID = 1L;

        @NotEmpty(message = "手机号不能为空")
        @ApiModelProperty("手机号")
        private String mobile;

        @NotEmpty(message = "证件号不能为空")
        @ApiModelProperty("证件号")
        private String idno;

        @NotEmpty(message = "姓名不能为空")
        @ApiModelProperty("姓名")
        private String name;
    }
}
