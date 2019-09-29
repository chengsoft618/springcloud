package com.iyysoft.msdp.dp.sys.controller;

import com.iyysoft.msdp.dp.sys.entity.SysClientServer;
import com.iyysoft.msdp.dp.sys.vo.ClientServerVo;
import com.iyysoft.msdp.dp.sys.service.SysClientServerService;
import com.iyysoft.msdp.common.core.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author xubinXie
 * @date 2019/5/6 0006
 */
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/clientServer")
@Api(value = "clientServer", tags = "客户服务器模块")
public class SysClientServerController {

    private SysClientServerService sysClientServerService;

    @ApiOperation("获取客户服务信息")
    @GetMapping("/{clientId}")
    public R<ClientServerVo> getClientServer(@PathVariable String clientId,
                                             Principal principal) {
        SysClientServer clientServer = sysClientServerService.getById(clientId);
        return new R<>(new ClientServerVo(clientServer));
    }
}
