package com.iyysoft.msdp.dp.app.vo.snap;

import com.iyysoft.msdp.dp.app.entity.snap.SnapUser;
import lombok.Data;

/**
 * @Author: 码农
 * @Date: 2019/8/23 14:34
 */
@Data
public class SnapUserVo {

    private String snapUserSid;

    private String nickName;

    private String head;


    public static SnapUserVo createVo(SnapUser snapUser){
        SnapUserVo snapUserVo = new SnapUserVo();
        snapUserVo.setSnapUserSid(snapUser.getSid());
        snapUserVo.setNickName(snapUser.getNickName());
        snapUserVo.setHead(snapUser.getHead());
        return snapUserVo;
    }

}
