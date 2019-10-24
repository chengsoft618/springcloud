package com.iyysoft.msdp.dp.app.vo.snap;

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
public class SnapShotDetailVo {


    //发布人的昵称
    private String nickName;
    //主键
    private String shotSid;
    //内容
    private String content;
    //点赞数
    private Integer likenum;
    //评论数
    private Integer commnum;
    //发布日期
    private LocalDateTime createDate;
    //评论
    private List<Comment> commentList;
    //
    private List<String> mediaList;


    @Data
    private static class Comment{
        private String nickName;
        private String comment;
        private String head;
    }
    // @Data
    // private static class Media{
    //     private String url;
    // }

    public static SnapShotDetailVo createVo(SnapShot snapShot , SnapUser snapUser, List<SnapComment> snapCommentList, List<SnapMedia> snapMediaList){
        SnapShotDetailVo snapShotDetailVo = new SnapShotDetailVo();

        snapShotDetailVo.setNickName(snapUser.getNickName());

        snapShotDetailVo.setShotSid(snapShot.getSid());
        snapShotDetailVo.setContent(snapShot.getContent());
        snapShotDetailVo.setLikenum(snapShot.getLikenum());
        snapShotDetailVo.setCommnum(snapShot.getCommnum());
        snapShotDetailVo.setCreateDate(snapShot.getCreateDate());

        List<Comment> commentList = snapCommentList.stream().map(snap ->{
            Comment comm = new Comment();
            comm.setComment(snap.getComment());
            comm.setNickName(snap.getNickName());
            return comm;
        }).collect(Collectors.toList());
        snapShotDetailVo.setCommentList(commentList);

        List<String> medList = snapMediaList.stream().map(SnapMedia::getUrl).collect(Collectors.toList());
        snapShotDetailVo.setMediaList(medList);

        return snapShotDetailVo;
    }
}
