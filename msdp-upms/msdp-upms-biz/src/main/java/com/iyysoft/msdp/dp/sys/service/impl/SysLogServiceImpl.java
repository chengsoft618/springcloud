package com.iyysoft.msdp.dp.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iyysoft.msdp.dp.sys.entity.SysLog;
import com.iyysoft.msdp.dp.sys.vo.PreLogVo;
import com.iyysoft.msdp.dp.sys.mapper.SysLogMapper;
import com.iyysoft.msdp.dp.sys.service.SysLogService;
import com.iyysoft.msdp.common.core.constant.CommonConstants;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 日志表 服务实现类
 * </p>
 *
 * @author mao.chi
 * @since 2017-11-20
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    /**
     * 批量插入前端错误日志
     *
     * @param preLogVoList 日志信息
     * @return true/false
     */
    @Override
    public Boolean saveBatchLogs(List<PreLogVo> preLogVoList) {
        List<SysLog> sysLogs = preLogVoList.stream()
                .map(pre -> {
                    SysLog log = new SysLog();
                    log.setType(CommonConstants.STATUS_LOCK);
                    log.setTitle(pre.getInfo());
                    log.setException(pre.getStack());
                    log.setParams(pre.getMessage());
                    log.setCreateTime(LocalDateTime.now());
                    log.setRequestUri(pre.getUrl());
                    log.setCreateBy(pre.getUser());
                    return log;
                })
                .collect(Collectors.toList());
        return this.saveBatch(sysLogs);
    }
}
