package com.iyysoft.msdp.dp.sys.vo;

import com.iyysoft.msdp.dp.sys.entity.SysLog;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mao.chi
 * @date 2017/11/20
 */
@Data
public class LogVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private SysLog sysLog;
    private String username;
}
