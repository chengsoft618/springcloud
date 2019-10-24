package com.iyysoft.msdp.dp.app.vo.snap;

import com.iyysoft.msdp.dp.app.entity.snap.SnapComment;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: 码农
 * @Date: 2019/9/2 14:06
 */
@ApiModel("随后拍-随手拍后台管理详情-点赞列表vo")
@Data
public class BackCommListVo {
    private String commSid;
    private String head;
    private String nickName;
    private String comment;
    private LocalDateTime createDate;

    public static BackCommListVo createVo(SnapComment snapComment) {
        BackCommListVo comment = new BackCommListVo();
        comment.setCommSid(snapComment.getSid());
        comment.setHead(snapComment.getSnapUser() == null ? "" : snapComment.getSnapUser().getHead());
        comment.setNickName(snapComment.getNickName());
        comment.setComment(snapComment.getComment());
        comment.setCreateDate(snapComment.getCreateDate());
        return comment;
    }

}
