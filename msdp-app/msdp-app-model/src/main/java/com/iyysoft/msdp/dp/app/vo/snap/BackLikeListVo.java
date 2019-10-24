package com.iyysoft.msdp.dp.app.vo.snap;

import com.iyysoft.msdp.dp.app.entity.snap.SnapLikeRecord;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: 码农
 * @Date: 2019/9/2 14:06
 */
@ApiModel("随后拍-随手拍后台管理详情-评论列表vo")
@Data
public class BackLikeListVo {

    private String nickName;
    private LocalDateTime createDate;

    public static BackLikeListVo createVo(SnapLikeRecord snapLikeRecord){
        BackLikeListVo likeListVo = new BackLikeListVo();
        likeListVo.setNickName(snapLikeRecord.getNickName());
        likeListVo.setCreateDate(snapLikeRecord.getCreateDate());
        return likeListVo;
    }
}
