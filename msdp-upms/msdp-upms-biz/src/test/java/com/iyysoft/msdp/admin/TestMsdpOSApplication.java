package com.iyysoft.msdp.admin;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import com.iyysoft.msdp.common.security.util.Pkcs7Encoder;
import com.ulisesbocchio.jasyptspringboot.encryptor.DefaultLazyEncryptor;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * @author waylen.chi
 * @date 2019/10/7
 * <p>
 * 加解密单元测试
 */
public class TestMsdpOSApplication {


    @Test
    public void testJasypt() throws UnsupportedEncodingException {
        Locale aa = Locale.getDefault();
        aa.getLanguage();

        System.out.println(aa);

    }

}
