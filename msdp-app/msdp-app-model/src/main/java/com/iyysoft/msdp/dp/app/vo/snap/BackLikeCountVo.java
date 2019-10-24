package com.iyysoft.msdp.dp.app.vo.snap;

import com.iyysoft.msdp.dp.app.entity.snap.SnapShot;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Author: 码农
 * @Date: 2019/9/16 15:11
 */
@ApiModel("随后拍-随手拍后台管理详情-点赞数量反馈VO")
@Data
public class BackLikeCountVo {
    /**
     * 总数
     */
    int totalNum;
    /**
     * 真正的点赞数
     */
    int realNum;
    /**
     * 虚假增加的点赞数
     */
    int falseNum;

    public static BackLikeCountVo createVo(SnapShot snapShot){
        BackLikeCountVo backLikeCountVo = new BackLikeCountVo();
        backLikeCountVo.setTotalNum(snapShot.getLikenum() + snapShot.getLikenumf());
        backLikeCountVo.setRealNum(snapShot.getLikenum());
        backLikeCountVo.setFalseNum(snapShot.getLikenumf());
        return backLikeCountVo;
    }
}
