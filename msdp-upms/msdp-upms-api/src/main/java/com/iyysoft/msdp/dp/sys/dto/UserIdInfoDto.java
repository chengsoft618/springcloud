package com.iyysoft.msdp.dp.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author xubinXie
 * @date 2019/6/10 0010
 */
@Data
@NoArgsConstructor
@ApiModel(value = "userIdInfoDto", description = "用户身份信息")
public class UserIdInfoDto {
    //	@NotEmpty
    @ApiModelProperty("证件类型")
    private String idType;

    //	@NotEmpty(message = "护照证件号不能为空")
    @ApiModelProperty(value = "护照证件号")
    private String idNo;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty(value = "证件持有人的姓")
    private String lastName;

    @ApiModelProperty(value = "证件持有人的名")
    private String firstName;

    //	@NotNull(message = "证件A面不能为空")
    @ApiModelProperty(value = "证件A面 身份证正面 护照首页")
    private String urlA;

    //	@NotNull(message = "证件B面不能为空")
    @ApiModelProperty(value = "证件B面 身份证反面 护照签证叶")
    private String urlB;

    @ApiModelProperty(value = "脸部正面照")
    private String faceImgUrl;

    @ApiModelProperty(value = "信息更新用户id")
    private String updateId;

    @Builder
    public UserIdInfoDto(@NotEmpty String idType, @NotEmpty(message = "护照证件号不能为空") String idNo,
                         String userName, String lastName, String firstName,
                         @NotNull(message = "证件A面不能为空") String urlA,
                         @NotNull(message = "证件B面不能为空") String urlB,
                         String faceImgUrl,
                         String updateId) {
        this.idType = idType;
        this.idNo = idNo;
        this.userName = userName;
        this.lastName = lastName;
        this.firstName = firstName;
        this.urlA = urlA;
        this.urlB = urlB;
        this.faceImgUrl = faceImgUrl;
        this.updateId = updateId;
    }
}
