package com.iyysoft.msdp.common.core.util.codec.spring5;

import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

/**
 * 实现使用PBKDF2算法哈希密码
 *
 * @author chimao
 * @date 16:55 2018-07-25
 */
public class Pbkdf2Util {
    /**
     * 对密码进行加密
     *
     * @param passwd
     * @return
     */
    public static String encode(String passwd) {
        Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder();
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
        Pbkdf2PasswordEncoder bEncoder = new Pbkdf2PasswordEncoder();
        return bEncoder.matches(passwd, encodePasswd);
    }

    /*
    public static void main(String[] args) {
        String hashPass = encode("123456");
        System.out.println(hashPass);
        boolean pass1 = isMatch("123456", "802544d2bf8d261fb0e25c6505ac88a4e57b961fd9d77be866efac5e852f654e6146bfeeac27bce6");
        System.out.println(pass1);
    }
    */

}
