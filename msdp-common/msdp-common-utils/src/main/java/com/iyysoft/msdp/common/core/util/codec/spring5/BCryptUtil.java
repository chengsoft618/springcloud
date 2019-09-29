package com.iyysoft.msdp.common.core.util.codec.spring5;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 使用BCrypt强哈希方法来加密密码
 *
 * @author chimao
 * @date 16:55 2018-07-25
 */
public class BCryptUtil {
    /**
     * 对密码进行加密
     *
     * @param passwd
     * @return
     */
    public static String encode(String passwd) {
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        return bcryptPasswordEncoder.encode(passwd);
    }

    /**
     * 对原密码和已加密的密码进行匹配，判断是否相等
     *
     * @param passwd
     * @param passwd
     * @return
     */
    public static boolean isMatch(String passwd, String encodePasswd) {
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        return bcryptPasswordEncoder.matches(passwd, encodePasswd);
    }

    /*
    public static void main(String[] args) {
        String hashPass = encode("123456");
        System.out.println(hashPass);
        boolean pass1 = isMatch("123456", "$2a$10$68..925QsSrH3P6GIX8uHemqu/irizehgHByX6Wqc0agNF8PVl6Py");
        System.out.println(pass1);
    }
    */

}
