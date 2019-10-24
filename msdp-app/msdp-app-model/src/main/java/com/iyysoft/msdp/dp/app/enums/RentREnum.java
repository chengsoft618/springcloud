package com.iyysoft.msdp.dp.app.enums;

import com.iyysoft.msdp.common.core.constant.enums.REnum;

/**
 * 操作返回码
 *
 * @author mao.chi
 * @date 2019/8/20 0010
 */
public enum RentREnum implements REnum {

	//操作成功
	SUCCESS(0),

	//添加成功
	ADD_SUCCESS(1221),

	//删除成功
	DEL_SUCCESS(1222),

	//修改成功
	MODIFY_SUCCESS(1223),

	//查询成功
	QUERY_SUCCESS(1224),

	//错误标兵
	ERROR_HEAD(1299),

	//锁已经绑定了,请勿重复绑定
	LOCK_REPEAT_BINDING(1307),

	//小区信息不存在
	RENT_ESTATE_NOT_EXIST(1310),

	//承租结束时间不能再承租开始时间之前
	RENT_DATE_ERROR(1311),

	//当前房源已下架
	RENT_ROOM_DOWN(1312),

	//当前房源不是空房源
	RENT_ROOM_NOTOK(1313),

	//创建租约失败
	RENT_CREATE_ERROR(1314),

	//租约信息不存在
	RENT_NOT_EXIST(1315),

	//入住人数不能超过系统设置
	RENT_PSN_DONT_MAX(1316),

	//租客信息已存在
	RENT_USER_IS_EXIST(1317),

	//订单金额不正确
	ORDER_AMNT_ERROR(1410),

	//订单已取消
	ORDER_IS_CANCEL(1414),

    //订单不存在
    ORDER_DOES_NOT_EXIST(1415),

	//提现金额错误
	OUTCASH_AMNT_ERROR(1416),

	//提现金额不足
	BALANCE_NOT_ENOUGH(1417),

	//预约不存在
	RESERVE_NOT_EXIST(1470),

	//预约状态不允许确认
	RESERVE_STATE_CONFIRM_ERROR(1471),

	//预约状态不允许删除
	RESERVE_STATE_DELETE_ERROR(1472),

	//预约状态不允许操作动态密码
	RESERVE_STATE_DYNAMIC_ERROR(1473),

	//预约动态密码失败
	RESERVE_QUERY_DYNAMIC_ERROR(1474),

	//不在预约的时间段内
	RESERVE_NOT_IN_TIME(1475),

	//租约申请不存在
	LEASE_APPLY_NOT_EXIST(1480),

	//入住申请异常
	LEASE_APPLY_CHECKIN_ERROR(1481),

	//续租申请异常
	LEASE_APPLY_RELET_ERROR(1482),

	//退租申请异常
	LEASE_APPLY_CHECKOUT_ERROR(1483),

	//租约申请删除失败
	RENT_APPLY_DELETE_ERROR(1484),

	//租约申请不存在
	LEASE_NOT_EXIST(1485),

	//入住申请异常
	LEASE_CHECKIN_ERROR(1486),

	//更新申请异常（续租退租）
	LEASE_UPDATE_ERROR(1487),

	//执行退租异常
	LEASE_THROW_ERROR(1488),

	//租赁状态异常
	ABNORMAL_LEASE_STATUS(1489),

	//重复申请
	APPLY_DUPLICATE_ERROR(1490),

	//不在申请时间范围内
	APPLY_OUT_TIME_ERROR(1491),

	//无权操作此租约
	RENT_NO_AUTH(1492),

	//租约用户已经绑定IC卡
	RENT_USER_BOUND_IC(1494),

	//设备平台消息处理异常
	DEVICE_DEAL_MSG(1495),

	//设备未绑定
	DEVICE_NOT_BIND(1496),

	//设备接口调用异常
	DEVICE_API_ERROR(1497),

	//设备信息异常
	DEVICE_INFO_ERROR(1498),

	//房间设备信息不存在
	DEVICE_ROOM_INFO_NOT_EXIST(1499),

	//无权操作此设备
	DEVICE_NO_AUTH(1500),

	//有新的租金生成
	RENT_GENERATE_NEW(1501),

	//房屋信息不完整
	HOUSING_INFORMATION_IS_INCOMPLETE(1600),

	//预约已存在
	RESERVATION_ALREADY_EXISTS(1601),

	//房间解约异常
	ROOM_CANCEL_AGREEMENT_ERROR(1602),

	//设备已经被其他房子绑定
	DEVICE_BIND_OTHER_ROOM(1603),

	//租约状态不允许删除
	RENT_STATE_DELETE_ERROR(1604),

	//户号和房号不能同时为空
	HOUSE_ROOM_NOT_SPACE(1605),

	//房间信息不存在
	ROOM_NOT_EXIST(1606),

	//照片超过上限
	CONTRACT_PHOTOS_ERROR(1700),

    //图片不能为空
    PICTURE_CANNOT_BE_EMPTY(1701),

    //请填入正确的预计入住时间
    NOT_BEFORE_THE_CURRENT_TIME(1702),

	//操作过于频繁
	OPERATION_TOO_FREQUENTLY(1990),

	//租约其他错误
	RENT_OTHER_ERROR(1999),

	//连接异常
	CONNECTION_FAILED(4001),

	//未认证
	UNABLE_IDENTIFY(4002),

	/**
	 * Photo service error r code.
	 */
	PHOTO_SERVICE_ERROR(4003),
	/**
	 * Encoding error r code.
	 */
	ENCODING_ERROR(4004),
	/**
	 * Download timeout r code.
	 */
	DOWNLOAD_TIMEOUT(4005),
	/**
	 * Download error r code.
	 */
	DOWNLOAD_ERROR(4006),
	/**
	 * Image id not exist r code.
	 */
	IMAGE_ID_NOT_EXIST(4007),
	/**
	 * No face detected r code.
	 */
	NO_FACE_DETECTED(4008),
	/**
	 * Corrupt image r code.
	 */
	CORRUPT_IMAGE(4009),
	/**
	 * Invalid image format or size r code.
	 */
	INVALID_IMAGE_FORMAT_OR_SIZE(4010),
	/**
	 * 请求参数错误
	 */
	INVALID_ARGUMENT(4011),
	/**
	 * Unauthorized r code.
	 */
	UNAUTHORIZED(4012),
	/**
	 * Key expired r code.
	 */
	KEY_EXPIRED(4013),
	/**
	 * Rate limit exceeded r code.
	 */
	RATE_LIMIT_EXCEEDED(4014),
	/**
	 * No permission r code.
	 */
	NO_PERMISSION(4015),
	/**
	 * Out of quota r code.
	 */
	OUT_OF_QUOTA(4016),
	/**
	 * Not found r code.
	 */
	NOT_FOUND(4017),
	/**
	 * Internal error r code.
	 */
	INTERNAL_ERROR(4018),

	//反面无法识别
	UNRECOGNIZED_IDENTIFY(4019),

	//证件照无法识别
	UNR_IDENTIFY(4021);


	private int code;

	RentREnum(int code) {
		this.code = code;
	}

	@Override
     public int getCode() {
		return code;
	}

	@Override
	public boolean isSuccess() {
		return code < ERROR_HEAD.code;
	}
}
