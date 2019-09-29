package com.iyysoft.msdp.common.core.util.codec.spring5;

import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

/**
 * 使用SCrypt强哈希方法来加密密码
 *
 * @author chimao
 * @date 16:55 2018-07-25
 */
public class SCryptUtil {
    /**
     * 对密码进行加密
     *
     * @param passwd
     * @return
     */
    public static String encode(String passwd) {
        SCryptPasswordEncoder encoder = new SCryptPasswordEncoder();
        return encoder.encode(passwd);
    }

    /**
     * 对原密码和已加密的密码进行匹配，判断是否相等
     *
     * @param passwd
     * @param passwd
     * @return
     */
    public static boolean isMatch(String passwd, String encodePasswd) {
        SCryptPasswordEncoder sEncoder = new SCryptPasswordEncoder();
        return sEncoder.matches(passwd, encodePasswd);
    }


    /*public static void main(String[] args) {
        String hashPass = encode("123456");
        System.out.println(hashPass);
        boolean pass1 = isMatch("123456", "$e0801$vwsKF/VTyqNowm1xH/FgnS44D+s3LboMr0n2RPbq7v74YEYpTum76dnoT0KqoLfVBbhodETMAKb4dP34VFFL6A==$8PF1BR8fxSgewK1FUaahpAzqdR2asoHXBy/VMfdbCds=");
        System.out.println(pass1);
    }*/

}
