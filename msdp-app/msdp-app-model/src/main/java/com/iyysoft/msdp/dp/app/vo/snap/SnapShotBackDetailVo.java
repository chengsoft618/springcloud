package com.iyysoft.msdp.dp.app.vo.snap;

import com.iyysoft.msdp.dp.app.entity.snap.SnapMedia;
import com.iyysoft.msdp.dp.app.entity.snap.SnapShot;
import com.iyysoft.msdp.dp.app.entity.snap.SnapShotTag;
import com.iyysoft.msdp.dp.app.entity.snap.SnapUser;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 码农
 * @Date: 2019/8/29 13:21
 */
@ApiModel("随后拍-随手拍后台管理详情vo")
@Data
public class SnapShotBackDetailVo {

    private String shotSid;
    /**
     * 发布人昵称
     */
    private String nickName;
    /**
     * 发布人实名
     */
    private String realName;
    /**
     * 实名照片
     */
    private String realPhoto;
    /**
     * 发布内容
     */
    private String content;

    /**
     * 头像照片
     */
    private String head;

    /**
     * 手机号
     */
    private String phone;
    /**
     * 发布时间
     */
    private LocalDateTime createDate;
    /**
     * 照片
     */
    private List<String> mediaList;
    /**
     * 审核状态
     */
    private String check;
    private String checkVal;
    /**
     * 显示状态
     */
    private String status;
    private String statusVal;

    /**
     * 标签
     */
    private String tag;
    /**
     * 点赞数
     */
    private Integer likenum;
    /**
     * 评论数量
     */
    private Integer commnum;


    public static SnapShotBackDetailVo createVo(SnapShot snapShot, SnapUser snapUser, List<SnapShotTag> shotTagList, List<SnapMedia> snapMediaList) {
        SnapShotBackDetailVo vo = new SnapShotBackDetailVo();
        vo.setRealName(snapUser == null ? null : snapUser.getRealName());
        vo.setRealPhoto(snapUser == null ? null : snapUser.getRealPhoto());
        vo.setHead(snapUser == null ? null : snapUser.getHead());
        vo.setPhone(snapUser == null ? null : snapUser.getPhone());
        vo.setShotSid(snapShot.getSid());
        vo.setNickName(snapShot.getNickName());
        vo.setContent(snapShot.getContent());
        vo.setCreateDate(snapShot.getCreateDate());
        vo.setLikenum(snapShot.getLikenum() + snapShot.getLikenumf());
        vo.setCommnum(snapShot.getCommnum());
        vo.setStatus(snapShot.getStatus().name());
        vo.setStatusVal(snapShot.getStatus().getValue());
        vo.setCheck(snapShot.getCheck().name());
        vo.setCheckVal(snapShot.getCheck().getValue());
        vo.setTag(
                shotTagList.stream().map(snapShotTag -> snapShotTag.getTag().getValue()).collect(Collectors.joining(","))
        );
        vo.setMediaList(
                snapMediaList.stream().map(SnapMedia::getUrl).collect(Collectors.toList())
        );
        return vo;

    }


}
