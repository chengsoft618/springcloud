package com.iyysoft.msdp.dp.app.vo.snap;

import com.iyysoft.msdp.dp.app.entity.snap.SnapComment;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: 码农
 * @Date: 2019/8/26 15:04
 */
@ApiModel("随后拍-APP随手拍详情Vo")
@Data
public class SnapShotDetailCommentVo {

    private String head;
    private String nickName;
    private String comment;
    private LocalDateTime createDate;

    public static SnapShotDetailCommentVo createVo(SnapComment snapComment) {
        SnapShotDetailCommentVo comment = new SnapShotDetailCommentVo();
        comment.setHead(snapComment.getSnapUser() == null ? "" : snapComment.getSnapUser().getHead());
        comment.setNickName(snapComment.getNickName());
        comment.setComment(snapComment.getComment());
        comment.setCreateDate(snapComment.getCreateDate());
        return comment;
    }
}
