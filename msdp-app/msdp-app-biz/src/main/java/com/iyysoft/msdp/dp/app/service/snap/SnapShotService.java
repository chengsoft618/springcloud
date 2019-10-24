package com.iyysoft.msdp.dp.app.service.snap;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.common.data.mybatis.QPage;
import com.iyysoft.msdp.dp.app.dto.snap.ShotAddDto;
import com.iyysoft.msdp.dp.app.dto.snap.ShotBackListDto;
import com.iyysoft.msdp.dp.app.entity.snap.SnapShot;
import com.iyysoft.msdp.dp.app.entity.snap.SnapUser;
import com.iyysoft.msdp.dp.app.enums.snap.ReportTypeEnum;
import com.iyysoft.msdp.dp.app.enums.snap.ShotTagEnum;
import com.iyysoft.msdp.dp.app.vo.snap.SnapShotAppVo;

/**
 * 随手拍-发布的随手拍
 *
 * @author iyysoft code generator
 * @date 2019-08-21 13:18:48
 */
public interface SnapShotService extends IService<SnapShot> {

    /**
     * 发布随手拍
     * @param shotAddDto
     * @return
     */
    R publishShot(ShotAddDto shotAddDto, String userId, String nickName);

    /**
     * 点赞/取消点赞
     * @return
     */
    R like(String shotSid, String userId, String nickName);

    /**
     * 添加假的点赞
     * @return
     */
    R likef(String shotSid, int likenumf);

    /**
     * 评论
     * @param snapShot
     * @param snapUser
     * @param comment
     * @return
     */
    R comm(SnapShot snapShot, SnapUser snapUser, String comment);

    /**
     * 取消评论
     * @param commSid
     * @return
     */
    R notComm(String commSid);

    /**
     * 随手拍后台管理-获取列表
     * @param dto
     * @param page
     * @return
     */
    IPage<SnapShot> selectBackShotList(ShotBackListDto dto, QPage page);

    /**
     * APP页面，获取列表
     * @param onlyme
     * @param shotTag
     * @param page
     * @return
     */
    IPage<SnapShotAppVo> selectAppShotList(Boolean onlyme, ShotTagEnum shotTag, QPage page, String userId);

    R report(SnapShot snapShot, ReportTypeEnum reportType, SnapUser snapUser, SnapUser pubUser);
}
