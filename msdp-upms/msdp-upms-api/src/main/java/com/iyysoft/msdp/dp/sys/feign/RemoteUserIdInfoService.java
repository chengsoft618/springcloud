package com.iyysoft.msdp.dp.sys.feign;

import com.iyysoft.msdp.dp.sys.dto.UserIdInfoDto;
import com.iyysoft.msdp.common.core.constant.ServiceNameConstants;
import com.iyysoft.msdp.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * 远程调用用户身份信息
 */
@FeignClient(contextId = "RemoteUserIdInfoService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteUserIdInfoService {

    /**
     * 添加用户身份信息
     *
     * @param userIdInfoDto
     * @return
     */
    @PostMapping("/userIdInfos")
    R saveUserIdInfo(@Valid @RequestBody UserIdInfoDto userIdInfoDto);

}
