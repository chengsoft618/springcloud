package com.iyysoft.msdp.dp.app.service.snap;

/**
 * @Author: 码农
 * @Date: 2019/8/30 8:53
 */
public interface SnapAsycnService {

    /**
     * 删除随手拍的后续异步任务
     * @param shotSid
     */
    void delShot(String shotSid);
}
