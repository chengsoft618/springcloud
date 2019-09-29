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

/**
 * @author waylen.chi
 * @date 2019/10/7
 * <p>
 * 加解密单元测试
 */
public class TestMsdp2Application {

    private static final String PASSWORD = "password";
    //private static final String KEY_ALGORITHM = "AES";
    private static final String KEY_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    private static String encodeKey ="iyysoft1msdp1123";
    //private String encodeKey ="msdpmsdpmsdpmsdp1";
    /*
        iyysoft.file=VB7z4nTk65ZNG5zT4t+w/iR97K8RsTt0
        iyysoft.file123=dJ6vHXQLIwVmW0HT85Dya03VSFsVWy4E
      msdp.act=e/ZbgAm4zoj/6UK/wKLbPsvhGtRgAiWn
      msdp.upm=4m6/gdBmfWo7fC4tABpRjhGMwyU7aV7u
      msdp.gen=xWjNKnfz9I/d+1640zpQWEpjSse1EE8q
      msdp.daemon=LUU1A3v41gQGS0Mz1J0Asq9MvFqAuFF0
      msdp.type.app=4L8S12llN47kqIufQI+vUdEYDYnssU/z
      msdp.type.debug=Oa36GEOekusSaXFKoTT/f33qaNqQZAio
      msdp.type.web=BdFmMnrTF7Bmjpx/Z7pYOJf+D8TjhZO+
      msdp.edu.ehall=psbhciamLaNOMoRCFIVDDVIW+7JJBmyi
      msdp.base.workmanager=/YVaiCq7QuKssAIbC9Y40TQ+ch5e+ddbi3UE0FeiNjw=
      msdp.app=BsKbGmys/fkTFXmG/IiI7unj+IO7sRzU

      db
        t_upm=w7Wf2el9+CdHNglsKc2SKA==
        base.C1upm=tExkneOk2oveLQsQ473T7AL+dJi483eU

        ms_act=CKgBwj57yynLkRg7/7ofQw==
        base.C1act=mb/OJ/podc01JpZjwa1t6YmuF+iWpp75

        server_wkmanage=QMHDxveqa+UGa8u/fbsay7kvOGJ6PC2f
        123!QWEasd=+UFYLP4qyzTE6YHQspJUJeVMjpsiPwTF

        ms_app=MGfc30MwmdNOAYjHswrqzg==
        base.C1app=LY5B+O1cuhNe1WSgQSe7QxtkXIulK1Xy

    */
    //文件管理 msdp.file        JLfJEYxQOEGKug7y5JuoQq+c0j5/P/vp       pwSTxIyjTmcVQFElGgdBbnz/CydLaYx4

    @Test
    public void testJasypt() throws UnsupportedEncodingException {
        // 对应application-dev.yml 中配置的根密码
        System.setProperty("jasypt.encryptor.password", "msdp");
        StringEncryptor stringEncryptor = new DefaultLazyEncryptor(new StandardEnvironment());
/*

        //解密方法
        //System.out.println(stringEncryptor.decrypt("ltJPpR50wT0oIY9kfOe1Iw==="));
        //System.out.println(stringEncryptor.decrypt("F+jfh1uWu0ypse95hQvSow=="));
*/
        //加密方法
        /*
        System.out.println("iyysoft.file="+stringEncryptor.encrypt("msdp.file"));
        System.out.println("iyysoft.file123="+stringEncryptor.encrypt("msdp.file123"));
        System.out.println("msdp.act="+stringEncryptor.encrypt("msdp.act"));
        System.out.println("msdp.upm="+stringEncryptor.encrypt("msdp.upm"));
        System.out.println("msdp.gen="+stringEncryptor.encrypt("msdp.gen"));
        System.out.println("msdp.type.app="+stringEncryptor.encrypt("msdp.type.app"));
        System.out.println("msdp.type.debug="+stringEncryptor.encrypt("msdp.type.debug"));
        System.out.println("msdp.type.web="+stringEncryptor.encrypt("msdp.type.web"));
        System.out.println("msdp.edu.ehall="+stringEncryptor.encrypt("msdp.edu.ehall"));
        System.out.println("msdp.base.workmanager="+stringEncryptor.encrypt("msdp.base.workmanager"));

        System.out.println("t_upm="+stringEncryptor.encrypt("t_upm"));
        System.out.println("base.C1upm="+stringEncryptor.encrypt("base.C1upm"));
        System.out.println("ms_act="+stringEncryptor.encrypt("ms_act"));
        System.out.println("base.C1act="+stringEncryptor.encrypt("base.C1act"));
        */

        //System.out.println("ms_act="+stringEncryptor.encrypt("ms_act"));
        //System.out.println("base.C1act="+stringEncryptor.encrypt("base.C1act"));

        //System.out.println("server_wkmanage="+stringEncryptor.encrypt("server_wkmanage"));
       // System.out.println("123!QWEasd="+stringEncryptor.encrypt("123!QWEasd"));

        System.out.println("msdp.app="+stringEncryptor.encrypt("msdp.app"));
        System.out.println("ms_app="+stringEncryptor.encrypt("ms_app"));
        System.out.println("base.C1app="+stringEncryptor.encrypt("base.C1app"));

        //System.out.println("base.C1act="+stringEncryptor.encrypt("base.C1act"));

//
//        System.out.println("iyysoft.file="+stringEncryptor.encrypt("msdp"));
//        System.out.println("iyysoft.file="+stringEncryptor.encrypt("msdp.type:web"));

      //  System.out.println("msdp.gen.file="+stringEncryptor.encrypt("msdp.gen"));
     //   System.out.println("msdp.123456="+stringEncryptor.encrypt("123456"));//XNFQ0dqdOdOu7zqM9vyrZw==
      //  System.out.println("msdp.msdp="+stringEncryptor.encrypt("msdpmsdpmsdpmsdp"));//MLAdVARL4M7dGM9x/MbBrtIGGBZUTTpqdk6DOZoexg0=


        //Pkcs7Encoder.decryptAES(password, encodeKey)

       // System.out.println(Pkcs7Encoder.encryptAES("123456", encodeKey));
       // System.out.println("m123456=" + ENCODER.encode("123456"));
/*
        String aa = "34j+hIn17766m+Z6PkIyv/sGfda+IAFwj2DMWP+6/lU=";
        byte[] base64Token = aa.getBytes("UTF-8");
        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new CheckedException(
                    "Failed to decode basic authentication token");
        }

        String token = new String(decoded, StandardCharsets.UTF_8);

        //byte[] result = Base64.decode("cGlnOnBpZw==");
        //System.out.println("token = "  + token);
       // System.out.println(encryptAES(Base64.encode("123456789"), encodeKey));
*/
        System.out.println("======================3-0===================thanks,msdp4cloud========");
        //System.out.println(encryptAES("1234567812345678","msdpmsdpmsdpmsdp"));
        //System.out.println(Pkcs7Encoder.decryptAES("nMOBQqwYOlgCExQiUFQYuw==", encodeKey));
        System.out.println("======================3=1==========================");

        System.out.println(Pkcs7Encoder.decryptAES("j1RFjySYJUv/ZfWSrfqZOw==", encodeKey));
        //System.out.println(decryptAES("KRWiQ/dT81XI9nushc31MA==","jcwljcwljcwljcwl"));
        //123456
        System.out.println("======================3===========================");
        System.out.println(Pkcs7Encoder.decryptAES("KRWiQ/dT81XI9nushc31MA==","jcwljcwljcwljcwl"));
        //IV：jcwljcwljcwljcwl
        //123456
        System.out.println("======================4===================thanks,msdp4cloud===[/F7+KRoM7uIdzIN1qiDztw==]====[iujjBrRQRoOfCHgU9SLuww==]=");
        //System.out.println(decryptAES("iujjBrRQRoOfCHgU9SLuww==","msdpmsdpmsdpmsdp"));
        //System.out.println("2222=" + Pkcs7Encoder.decryptAES("c9UZaye5NztXZEMY2rz2xQ==", encodeKey));

    }

    private static String decryptAES1(String data, String pass) {
        String b  = "1";
        try {
            byte[] decryptKey = new BASE64Decoder().decodeBuffer(data);
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(pass.getBytes(), "AES/ECB/PKCS5Padding"));
            byte[] decryptBytes = cipher.doFinal(decryptKey);
            b  = new String(decryptBytes, StandardCharsets.UTF_8);
        }catch(Exception ex){System.out.println("aaaerror");}


        return b;
    }
    private String decryptAES(String data, String pass) {
        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding,
                new SecretKeySpec(pass.getBytes(), "AES/ECB/PKCS5Padding"),
                new IvParameterSpec(pass.getBytes()));
        byte[] result = aes.decrypt(Base64.decode(data.getBytes(StandardCharsets.UTF_8)));
        return new String(result, StandardCharsets.UTF_8);
    }

    private String encryptAES(String data, String pass) {

        AES aes = new AES(Mode.CBC,Padding.NoPadding,
                new SecretKeySpec(pass.getBytes(), "AES"),
                new IvParameterSpec(pass.getBytes()));

        byte[] result = aes.encrypt(Base64.decode(data.getBytes(StandardCharsets.UTF_8)));
        return new String(result, StandardCharsets.UTF_8);
    }
}
