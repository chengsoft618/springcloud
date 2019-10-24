package com.iyysoft.msdp.dp.app.service.snap;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iyysoft.msdp.dp.app.entity.snap.SnapComment;

import java.util.List;

/**
 * 随手拍-评论
 *
 * @author iyysoft code generator
 * @date 2019-08-21 13:18:52
 */
public interface SnapCommentService extends IService<SnapComment> {

    /**
     *
     * @param page
     * @param ew
     * @return
     */
    IPage<SnapComment> selectBackCommList(Page page, Wrapper ew);


    List<SnapComment> selectOpenCommList(Wrapper ew);
}
