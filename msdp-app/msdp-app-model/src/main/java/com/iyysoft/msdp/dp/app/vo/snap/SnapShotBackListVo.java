package com.iyysoft.msdp.dp.app.vo.snap;

import cn.hutool.core.util.StrUtil;
import com.iyysoft.msdp.dp.app.entity.snap.SnapShot;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: 码农
 * @Date: 2019/8/29 13:21
 */
@ApiModel("随后拍-随手拍后台管理列表vo")
@Data
public class SnapShotBackListVo {


    /**
     * 主键
     */
    private String shotSid;
    /**
     * 标签
     */
    private String tag;
    /**
     * 发布人的昵称
     */
    private String nickName;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 内容简略
     */
    private String content;
    /**
     * 图片张数
     */
    private Integer medianum;
    /**
     * 审核状态
     */
    private String check;
    /**
     * 显示状态
     */
    private String status;
    /**
     * 发布时间
     */
    private LocalDateTime createDate;


    public static SnapShotBackListVo createVo(SnapShot snapShot) {
        SnapShotBackListVo vo = new SnapShotBackListVo();
        //String tag = snapShot.getSnapShotTagList().stream().map(snapShotTag -> snapShotTag.getTag().getValue()).collect(Collectors.joining(","));
        vo.setTag(snapShot.getTag());
        vo.setShotSid(snapShot.getSid());
        vo.setNickName(snapShot.getNickName());
        vo.setContent(StrUtil.maxLength(snapShot.getContent(), 10));
        vo.setMedianum(snapShot.getMedianum());
        vo.setCreateDate(snapShot.getCreateDate());
        vo.setStatus(snapShot.getStatus().getValue());
        vo.setCheck(snapShot.getCheck().getValue());
        vo.setPhone(snapShot.getSnapUser() == null ? null : snapShot.getSnapUser().getPhone());
        return vo;
    }


}
