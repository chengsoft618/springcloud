package com.iyysoft.msdp.dp.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p class="detail">
 * 功能:实名认证状态
 * </p>
 *
 * @author cm
 * @ClassName User real status dto.
 * @Version V1.0.
 * @date 2019.05.14 15:45:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "UserRealStatusDto", description = "用户实名认证状态")
public class UserRealStatusDto implements Serializable {
//	/**
//	 * 是否实名认证
//	 */
//	@ApiModelProperty(value = "是否实名认证", dataType="String", example="0: 未实名认证  1：实名认证" )
//	private String realStatus;

//	@ApiModelProperty(value = "实名认证人", dataType="String", example="张三" )
//	private String userId;

    /**
     * 手机
     */
//	//@NotNull(message = "移动手机号码不能为空。")
//	@ApiModelProperty(value = "手机", required = true, dataType = "String", example = "12345678901")
//	private String mobile;

    /**
     * 姓名
     */
    @NotNull(message = "姓名不能为空。")
    @ApiModelProperty(value = "姓名", required = true, dataType = "String", example = "张三")
    private String userName;

    /**
     * 身份证号
     */
    @NotNull(message = "18位身份证件号不能为空。")
    @ApiModelProperty(value = "身份证号", required = true, dataType = "String", example = "11204416541220243X")
    private String number;
}
