package com.iyysoft.msdp.dp.app.vo.snap;

import com.iyysoft.msdp.dp.app.entity.snap.SnapShot;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 码农
 * @Date: 2019/8/30 15:12
 */

@ApiModel("随后拍-APP端随手拍Vo")
@Data
public class SnapShotAppVo {

    private String shotSid;
    //头像照片
    private String head;
    //发布人的随手拍账号的昵称
    private String nickName;
    //创建时间
    private LocalDateTime createDate;
    //内容
    private String content;
    //图片
    private List<Media> mediaList;
    //点赞数
    private Integer likenum;
    //评论数量
    private Integer commnum;
    //是否对这条朋友圈点过赞
    private Boolean isLike;
    //是否是本人发的
    private Boolean isMine;
    //标签
    private String tag;

    @Data
    private static class Media {
        private String url;
        private String hdUrl;
    }


    public static SnapShotAppVo create(SnapShot snapShot, String userJid) {
        SnapShotAppVo snapShotAppVo = new SnapShotAppVo();
        snapShotAppVo.setHead(snapShot.getSnapUser() == null ? "" : snapShot.getSnapUser().getHead());
        snapShotAppVo.setContent(snapShot.getContent());
        List<Media> mediaList = snapShot.getSnapMediaList().stream().map(snapMedia -> {
            Media media = new Media();
            media.setUrl(snapMedia.getUrl());
            media.setHdUrl(snapMedia.getHdUrl());
            return media;
        }).collect(Collectors.toList());
        snapShotAppVo.setMediaList(mediaList);
        snapShotAppVo.setShotSid(snapShot.getSid());
        snapShotAppVo.setNickName(snapShot.getNickName());
        snapShotAppVo.setCreateDate(snapShot.getCreateDate());
        snapShotAppVo.setLikenum(snapShot.getLikenum() + snapShot.getLikenumf());
        snapShotAppVo.setCommnum(snapShot.getCommnum());
        snapShotAppVo.setIsMine(snapShot.getCreateId().equals(userJid));
        snapShotAppVo.setIsLike(snapShot.getSnapLikeRecord() != null);
        snapShotAppVo.setTag(snapShot.getTag());
        return snapShotAppVo;
    }

    public static SnapShotAppVo create(SnapShot snapShot) {
        SnapShotAppVo snapShotAppVo = new SnapShotAppVo();
        snapShotAppVo.setHead(snapShot.getSnapUser() == null ? "" : snapShot.getSnapUser().getHead());
        snapShotAppVo.setShotSid(snapShot.getSid());
        snapShotAppVo.setNickName(snapShot.getNickName());
        snapShotAppVo.setCreateDate(snapShot.getCreateDate());
        snapShotAppVo.setLikenum(snapShot.getLikenum() + snapShot.getLikenumf());
        snapShotAppVo.setCommnum(snapShot.getCommnum());
        snapShotAppVo.setTag(snapShot.getTag());
        return snapShotAppVo;
    }
}
