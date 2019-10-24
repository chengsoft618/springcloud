package com.iyysoft.msdp.dp.app.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iyysoft.msdp.dp.app.entity.snap.SnapShot;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 随手拍-发布的随手拍
 *
 * @author iyysoft code generator
 * @date 2019-08-21 13:18:48
 */
public interface SnapShotMapper extends BaseMapper<SnapShot> {

    /**
     *
     * @param page
     * @param wrapper
     * @return
     */
    IPage<SnapShot> selectBackShotList(Page page, @Param("ew") Wrapper wrapper);

    /**
     * 后台管理-随手拍列表
     *
     * @param wrapper
     * @return
     */
    IPage<SnapShot> selectBackShotListByTag(Page page, @Param("ew") Wrapper wrapper, @Param("tag") String tag);

    /**
     * APP-随后拍列表
     * @param wrapper
     * @param begin
     * @param length
     * @return
     */
    List<SnapShot> selectAppShotList(@Param("ew") Wrapper wrapper,
                                     @Param("begin") Long begin,
                                     @Param("length") Long length,
                                     @Param("createId") String createId);

    /**
     * APP-随后拍列表,带标签
     * @param wrapper
     * @param tag
     * @param begin
     * @param length
     * @return
     */
    List<SnapShot> selectAppShotListByTag(@Param("ew") Wrapper wrapper,
                                          @Param("tag") String tag,
                                          @Param("begin") Long begin,
                                          @Param("length") Long length,
                                          @Param("createId") String createId);

}
