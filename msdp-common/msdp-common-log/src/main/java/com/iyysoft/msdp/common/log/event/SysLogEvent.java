package com.iyysoft.msdp.common.log.event;

import com.iyysoft.msdp.dp.sys.entity.SysLog;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author mao.chi
 * 系统日志事件
 */
@Getter
@AllArgsConstructor
public class SysLogEvent {
    private final SysLog sysLog;
}
