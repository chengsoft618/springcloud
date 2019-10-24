package com.iyysoft.msdp.dp.app.vo.snap;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iyysoft.msdp.common.core.jackson.LocalDateTime2LongSerializer;
import com.iyysoft.msdp.dp.app.entity.snap.SnapComment;
import com.iyysoft.msdp.dp.app.entity.snap.SnapMedia;
import com.iyysoft.msdp.dp.app.entity.snap.SnapShot;
import com.iyysoft.msdp.dp.app.entity.snap.SnapUser;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 码农
 * @Date: 2019/8/26 15:04
 */
@ApiModel("随后拍-APP随手拍详情Vo")
@Data
public class SnapShotShareVo {


    //发布人的昵称
    private String name;
    //头像
    private String head;
    //标签
    private String tag;
    //内容
    private String content;
    //图片
    private List<Media> mediaList;
    //点赞数
    private Integer likenum;
    //评论数
    private Integer commnum;
    //评论
    private List<Comment> commentList;
    //发布时间
    @JsonSerialize(using = LocalDateTime2LongSerializer.class)
    private LocalDateTime date;

    @Data
    private static class Comment {
        private String nickName;
        private String comment;
        private String head;
    }

    @Data
    private static class Media {
        private String url;
        private String hdUrl;
    }

    public static SnapShotShareVo createVo(SnapShot snapShot, SnapUser snapUser, List<SnapComment> snapCommentList, List<SnapMedia> snapMediaList) {
        SnapShotShareVo shareVo = new SnapShotShareVo();

        shareVo.setName(snapShot.getNickName());
        shareVo.setContent(snapShot.getContent());
        shareVo.setLikenum(snapShot.getLikenum());
        shareVo.setCommnum(snapShot.getCommnum());
        shareVo.setDate(snapShot.getCreateDate());
        shareVo.setHead(snapUser == null ? "" : snapUser.getHead());
        shareVo.setTag(snapShot.getTag());
        List<Comment> commentList = snapCommentList.stream().map(snapComm -> {
            Comment comm = new Comment();
            comm.setComment(snapComm.getComment());
            comm.setNickName(snapComm.getNickName());
            comm.setHead(snapComm.getSnapUser() == null ? "" : snapComm.getSnapUser().getHead());
            return comm;
        }).collect(Collectors.toList());
        shareVo.setCommentList(commentList);
        List<Media> medList = snapMediaList.stream().map(snapMedia -> {
            Media media = new Media();
            media.setUrl(snapMedia.getUrl());
            media.setHdUrl(snapMedia.getHdUrl());
            return media;
        }).collect(Collectors.toList());
        shareVo.setMediaList(medList);
        return shareVo;
    }
}
