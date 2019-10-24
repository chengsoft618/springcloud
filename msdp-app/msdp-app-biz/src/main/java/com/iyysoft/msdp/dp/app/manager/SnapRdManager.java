package com.iyysoft.msdp.dp.app.manager;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iyysoft.msdp.dp.app.entity.snap.SnapShot;
import com.iyysoft.msdp.dp.app.entity.snap.SnapShotTag;
import com.iyysoft.msdp.dp.app.entity.snap.SnapUser;
import com.iyysoft.msdp.dp.app.enums.snap.ShotTagEnum;
import com.iyysoft.msdp.dp.app.mapper.SnapShotMapper;
import com.iyysoft.msdp.dp.app.mapper.SnapShotTagMapper;
import com.iyysoft.msdp.dp.app.vo.snap.SnapShotAppVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 码农
 * @Date: 2019/8/27 10:52
 */
@Slf4j
@Component
@AllArgsConstructor
public class SnapRdManager {

    private final SnapShotMapper snapShotMapper;
    private final StringRedisTemplate redisTemplate;
    private final SnapShotTagMapper snapShotTagMapper;

    /**
     * redis类型：String
     * 随手拍用户
     */
    public SnapUser snapUserGet(String userId) {
        if (userId == null) {
            return null;
        }
        String str = redisTemplate.opsForValue().get(SnapRdKey.SNAP_USER + userId);
        if (str == null) {
            return null;
        }
        return JSONObject.parseObject(str, SnapUser.class);
    }
    public void snapUserSet(SnapUser snapUser) {
        if (snapUser == null) {
            return;
        }
        String key = SnapRdKey.SNAP_USER + snapUser.getUserId();
        redisTemplate.opsForValue().set(key, JSONObject.toJSONString(snapUser));
        redisTemplate.expire(key, 1, TimeUnit.HOURS);
    }
    public void snapUserDel(String userId) {
        if (StrUtil.isBlank(userId)) {
            return;
        }
        String key = SnapRdKey.SNAP_USER + userId;
        redisTemplate.delete(key);

    }

    /**
     * redis类型：String
     * 分享的随手拍
     */
    public String shareShotGet(String shotSid){
        return redisTemplate.opsForValue().get(SnapRdKey.SHARE_SHOT+shotSid);
    }
    public void shareShotSet(String shotSid,String jsonStr){
        redisTemplate.opsForValue().set(SnapRdKey.SHARE_SHOT+shotSid, jsonStr, Duration.ofMinutes(10L));
    }












    /**
     * 以下的先不管，在未来的随手拍新版本的中，可能会增加附近的随手拍功能，或随着访问量增大，加入缓存和地理位置功能
     */
    public void snapShotAdd(String shotSid) {
        if (shotSid == null) {
            return;
        }
        SnapShot snapShot = snapShotMapper.selectById(shotSid);
        redisTemplate.opsForZSet().add(SnapRdKey.SNAP_SHOT_ZSET, snapShot.getSid(), snapShot.getOrders());
        redisTemplate.opsForValue().set(SnapRdKey.SNAP_SHOT + snapShot.getSid(), JSON.toJSONString(SnapShotAppVo.create(snapShot)), 20, TimeUnit.DAYS);
    }
    public void snapShotZsetDel(String shotSid) {
        if (shotSid == null) {
            return;
        }
        redisTemplate.opsForZSet().remove(SnapRdKey.SNAP_SHOT_ZSET, shotSid);
    }
    public SnapShotAppVo snapShotListGet(String shotSid) {
        String jsonStr = redisTemplate.opsForValue().get(SnapRdKey.SNAP_SHOT + shotSid);
        if (jsonStr == null) {
            //Arrays.stream(shotSid).forEach(this::snapShotZsetDel);
            snapShotZsetDel(shotSid);
            return null;
        }
        return JSONObject.parseObject(jsonStr, SnapShotAppVo.class);

    }
    public List<SnapShotAppVo> snapShotZsetGet(long pageNum, long pageSize) {
        long start = pageNum > 0 ? (pageNum - 1) * pageSize : 0;
        long end = pageNum > 0 ? pageNum * pageSize - 1 : pageSize - 1;
        log.debug("snapShotGet,start:{},end:{}", start, end);
        Set<String> shotSidZset = redisTemplate.opsForZSet().reverseRange(SnapRdKey.SNAP_SHOT_ZSET, start, end);
        List<SnapShotAppVo> snapShotAppVoList = new ArrayList<>();
        Iterator<String> it = shotSidZset.iterator();
        while (it.hasNext()) {
            SnapShotAppVo snapShotAppVo = snapShotListGet(it.next());
            if (snapShotAppVo != null) {
                snapShotAppVoList.add(snapShotAppVo);
            } else {
                break;
            }
        }
        return snapShotAppVoList;
    }
    public List<SnapShotAppVo> snapShotZsetGet(long pageNum, long pageSize, ShotTagEnum shotTagEnum) {
        long start = pageNum > 0 ? (pageNum - 1) * pageSize : 0;
        long end = pageNum > 0 ? pageNum * pageSize - 1 : pageSize - 1;
        log.debug("snapShotGet,start:{},end:{}", start, end);
        Set<String> shotSidZset = redisTemplate.opsForZSet().reverseRange(SnapRdKey.SNAP_SHOT_ZSET + shotTagEnum.name(), start, end);
        List<SnapShotAppVo> snapShotAppVoList = new ArrayList<>();
        Iterator<String> it = shotSidZset.iterator();
        while (it.hasNext()) {
            SnapShotAppVo snapShotAppVo = snapShotListGet(it.next());
            if (snapShotAppVo != null) {
                snapShotAppVoList.add(snapShotAppVo);
            } else {
                break;
            }
        }
        return snapShotAppVoList;
    }


}
