package com.iyysoft.msdp.dp.app.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iyysoft.msdp.dp.app.entity.snap.SnapReport;
import org.apache.ibatis.annotations.Param;

/**
 * 随手拍-举报
 *
 * @author iyysoft code generator
 * @date 2019-08-21 13:18:55
 */
public interface SnapReportMapper extends BaseMapper<SnapReport> {

    /**
     *
     * @param page
     * @param wrapper
     * @return
     */
    IPage<SnapReport> selectReportShotList(Page page, @Param("ew") Wrapper wrapper);

}
