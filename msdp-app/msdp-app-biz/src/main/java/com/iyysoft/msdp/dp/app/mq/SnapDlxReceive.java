package com.iyysoft.msdp.dp.app.mq;

import com.iyysoft.msdp.dp.app.enums.snap.ShotCheckEnum;
import com.iyysoft.msdp.dp.app.enums.snap.ShotStatusEnum;
import com.iyysoft.msdp.dp.app.entity.snap.SnapShot;
import com.iyysoft.msdp.dp.app.service.snap.SnapShotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: 码农
 * @Date: 2019/8/22 18:37
 */
@Slf4j
@Component
public class SnapDlxReceive {

    @Autowired
    private SnapShotService snapShotService;

    @RabbitListener(queues = MqConstant.SNAP_DLX_QUEUE)
    public void handleMessage(byte[] message){
        String shotSid = new String(message);
        //log.warn("SnapShotDlxReceive:{},",shotSid);
        SnapShot snapShot = snapShotService.getById(shotSid);
        if( snapShot == null){
            log.error("snapShot delay task error : can not find snapShot from db with sid = {}",shotSid);;
            return;
        }
        if(snapShot.getCheck() != ShotCheckEnum.PASS){
            SnapShot record = new SnapShot();
            record.setSid(shotSid);
            record.setStatus(ShotStatusEnum.HIDE);
            record.setCheck(ShotCheckEnum.OVERDUE);
            snapShotService.updateById(record);
        }
    }
}
