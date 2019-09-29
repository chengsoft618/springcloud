//package com.iyysoft.msdp.admin;
//
////import org.apache.commons.codec.binary.Base64;
//
//import cn.hutool.core.codec.Base64;
//import cn.hutool.crypto.Mode;
//import cn.hutool.crypto.Padding;
//import cn.hutool.crypto.symmetric.AES;
//import com.iyysoft.msdp.common.core.exception.CheckedException;
//import com.iyysoft.msdp.common.security.util.Pkcs7Encoder;
//import com.ulisesbocchio.jasyptspringboot.encryptor.DefaultLazyEncryptor;
//import org.jasypt.encryption.StringEncryptor;
//import org.junit.Test;
//import org.springframework.core.env.StandardEnvironment;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import sun.misc.BASE64Decoder;
//
//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//import java.io.UnsupportedEncodingException;
//import java.nio.charset.StandardCharsets;
//
///**
// * @author waylen.chi
// * @date 2018/10/7
// * <p>
// * 加解密单元测试
// */
//public class TestMsdpApplication {
//
//    private static final String PASSWORD = "password";
//    //private static final String KEY_ALGORITHM = "AES";
//    private static final String KEY_ALGORITHM = "AES/ECB/PKCS5Padding";
//    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
//    //private String encodeKey ="iyysoft.msdp.123";
//    private String encodeKey ="msdpmsdpmsdpmsdp";
//
//    // msdp     ZezpXbuNSd/gyPyd/TlXKw==
//    //-- msdp:msdp     sWiuQbJ+1EQr6GoSjSHV1vZh2imlx+fo
//    // msdp.upm
//    //msdp.web  BVhCYxh3rUVGsTfxSrRCjgY5MvMNMEhm
//    //msdp.type.web     Lml0QI+7bzNMpRqvsBEfcvob65y4Zk5D
//    //msdp.type.wx      5tm7p89IuR59JupxaAzLO1vmqHk+JuDi
//    //msdp.type.app     jxpidJYiQtpZV35m8mwZyK9JqwXrzf/z
//    //msdp.type.debug   +m9KfbLXHOPI/cVXc7RwgHGWE7UaeR4Z
//    //msdp.edu.app.notice
//    //msdp.edu.ehall        59TVYkyxItql80qzr0SiVEWl97srSq9t
//    //msdp.upm      XD/sAMOD7Uo2GtzM4Ns+0PgAsCEuroCV
//    //msdp.gen      lA91hcPyQH9gxUjvlIYqobjePiN0Hdd2
//    //msdp.act      b9MKPGjqbWelPh8nYouPyNbCCY9ExiZy
//
//    //文件管理 msdp.file        JLfJEYxQOEGKug7y5JuoQq+c0j5/P/vp       pwSTxIyjTmcVQFElGgdBbnz/CydLaYx4
//
//    @Test
//    public void testJasypt() throws UnsupportedEncodingException {
//        // 对应application-dev.yml 中配置的根密码
//        System.setProperty("jasypt.encryptor.password", "msdp");
//        StringEncryptor stringEncryptor = new DefaultLazyEncryptor(new StandardEnvironment());
///*
//        //加密方法
//        System.out.println(stringEncryptor.encrypt("msdp.web"));
//        System.out.println(stringEncryptor.encrypt("msdp.web123"));
//
//        System.out.println("iyysoft.file="+stringEncryptor.encrypt("iyysoft.file"));
//        System.out.println("iyysoft.file123="+stringEncryptor.encrypt("iyysoft.file123"));
//        System.out.println(encodeKey + "="+stringEncryptor.encrypt(encodeKey));
//
//        //解密方法
//        //System.out.println(stringEncryptor.decrypt("ltJPpR50wT0oIY9kfOe1Iw==="));
//        //System.out.println(stringEncryptor.decrypt("F+jfh1uWu0ypse95hQvSow=="));
//*/
//       // System.out.println("iyysoft:msdp.123="+stringEncryptor.encrypt("iyysoft:msdp.123"));
//
//       // System.out.println(stringEncryptor.decrypt("QNzJNmNyE7Mk0kkDxdKWaBUAzTjRFF8t"));
//
////        System.out.println("iyysoft.file="+stringEncryptor.encrypt("msdp.file"));
////        System.out.println("iyysoft.file123="+stringEncryptor.encrypt("msdp.file123"));
////
////        System.out.println("iyysoft.file="+stringEncryptor.encrypt("msdp"));
////        System.out.println("iyysoft.file="+stringEncryptor.encrypt("msdp.type:web"));
////        System.out.println("iyysoft.file="+stringEncryptor.encrypt("msdp.type:wx"));
//
//        System.out.println("msdp.gen.file="+stringEncryptor.encrypt("msdp.gen"));
//        System.out.println("msdp.act.file="+stringEncryptor.encrypt("msdp.act"));
//
//
//        //Pkcs7Encoder.decryptAES(password, encodeKey)
//
//       // System.out.println(Pkcs7Encoder.encryptAES("123456", encodeKey));
//        System.out.println(ENCODER.encode("123456"));
//
//        String aa = "34j+hIn17766m+Z6PkIyv/sGfda+IAFwj2DMWP+6/lU=";
//        byte[] base64Token = aa.getBytes("UTF-8");
//        byte[] decoded;
//        try {
//            decoded = Base64.decode(base64Token);
//        } catch (IllegalArgumentException e) {
//            throw new CheckedException(
//                    "Failed to decode basic authentication token");
//        }
//
//        String token = new String(decoded, StandardCharsets.UTF_8);
//
//        //byte[] result = Base64.decode("cGlnOnBpZw==");
//        System.out.println("token = "  + token);
//
////        String jcweb = "msdp.type:web";
////        System.out.println("web:"+ Base64.encode(jcweb.getBytes(StandardCharsets.UTF_8)));
////
////        String jcapp = "msdp.type:app";
////        System.out.println("app:"+ Base64.encode(jcapp.getBytes(StandardCharsets.UTF_8)));
////
////        String wxmini = "msdp.type:wx";
////        System.out.println("mini:"+ Base64.encode(wxmini.getBytes(StandardCharsets.UTF_8)));
//        //System.out.println(decryptAES("xG5RfALdyO2GzhMqQdFK5Q==","jcwl"));
//
//       // Pkcs7Encoder.decryptAES("/F7+KRoM7uIdzIN1qiDztw==", encodeKey);
//
//       // System.out.println(encryptAES("/F7+KRoM7uIdzIN1qiDztw==", encodeKey));
//       // System.out.println(encryptAES("N/erX9ZcQVTAGIjil/SxFw==", "msdpmsdpmsdpmsdp"));
//
//        System.out.println("=========================1========================");
//        System.out.println(encryptAES("N/erX9ZcQVTAGIjil/SxFw==", "N/erX9ZcQVTAGIjil/SxFw=="));
//
//        System.out.println("======================2===========================");
//        //System.out.println(decryptAES("N/erX9ZcQVTAGIjil/SxFw==","msdpmsdpmsdpmsdp"));
//        //System.out.println(Pkcs7Encoder.decryptAES("N/erX9ZcQVTAGIjil/SxFw==","msdpmsdpmsdpmsdp"));
//        //System.out.println(decryptAES1("KRWiQ/dT81XI9nushc31MA==","jcwljcwljcwljcwl"));
//        System.out.println("======================3=1==========================");
//        System.out.println(decryptAES("KRWiQ/dT81XI9nushc31MA==","jcwljcwljcwljcwl"));
//        //System.out.println(encryptAES("123456789123456789aaaaaa","jcwljcwljcwljcwl"));
//        System.out.println("======================3===========================");
//        System.out.println(Pkcs7Encoder.decryptAES("KRWiQ/dT81XI9nushc31MA==","jcwljcwljcwljcwl"));
//    }
//
//    private static String decryptAES1(String data, String pass) {
//        String b  = "1";
//        try {
//            byte[] decryptKey = new BASE64Decoder().decodeBuffer(data);
//            KeyGenerator kgen = KeyGenerator.getInstance("AES");
//            kgen.init(128);
//            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
//            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(pass.getBytes(), "AES/ECB/PKCS5Padding"));
//            byte[] decryptBytes = cipher.doFinal(decryptKey);
//            b  = new String(decryptBytes, StandardCharsets.UTF_8);
//        }catch(Exception ex){System.out.println("aaaerror");}
//
//
//        return b;
//    }
//    private String decryptAES(String data, String pass) {
//        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding,
//                new SecretKeySpec(pass.getBytes(), "AES/ECB/PKCS5Padding"),
//                new IvParameterSpec(pass.getBytes()));
//        byte[] result = aes.decrypt(Base64.decode(data.getBytes(StandardCharsets.UTF_8)));
//        return new String(result, StandardCharsets.UTF_8);
//    }
//
//    private String encryptAES(String data, String pass) {
//
//        AES aes = new AES(Mode.CBC,Padding.NoPadding,
//                new SecretKeySpec(pass.getBytes(), "AES"),
//                new IvParameterSpec(pass.getBytes()));
//
//        byte[] result = aes.encrypt(Base64.decode(data.getBytes(StandardCharsets.UTF_8)));
//        return new String(result, StandardCharsets.UTF_8);
//    }
//}
