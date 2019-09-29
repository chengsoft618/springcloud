
package com.iyysoft.msdp.common.security.component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.iyysoft.msdp.common.core.constant.enums.REnum;
import com.iyysoft.msdp.common.security.exception.MsdpAuth2Exception;
import lombok.SneakyThrows;

/**
 * @author mao.chi
 * @date 2018/11/16
 * <p>
 * OAuth2 异常格式化
 */
public class MsdpAuth2ExceptionSerializer extends StdSerializer<MsdpAuth2Exception> {
    public MsdpAuth2ExceptionSerializer() {
        super(MsdpAuth2Exception.class);
    }

    @Override
    @SneakyThrows
    public void serialize(MsdpAuth2Exception value, JsonGenerator gen, SerializerProvider provider) {
        gen.writeStartObject();
        gen.writeObjectField("result", false);
        gen.writeObjectField("code", REnum.CODE.FAILED);
        gen.writeStringField("msg", value.getMessage());
        gen.writeStringField("data", value.getErrorCode());
        gen.writeEndObject();
    }
}
