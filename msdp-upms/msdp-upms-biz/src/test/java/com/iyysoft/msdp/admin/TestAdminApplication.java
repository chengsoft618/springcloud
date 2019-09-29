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
//public class TestAdminApplication {
//
//	private static final String PASSWORD = "password";
//	//private static final String KEY_ALGORITHM = "AES";
//	private static final String KEY_ALGORITHM = "AES/ECB/PKCS5Padding";
//	private String encodeKey ="jcwljcwljcwljcwl";
//
//	@Test
//	public void testJasypt() throws UnsupportedEncodingException {
//		// 对应application-dev.yml 中配置的根密码
//		System.setProperty("jasypt.encryptor.password", "jcwl");
//		StringEncryptor stringEncryptor = new DefaultLazyEncryptor(new StandardEnvironment());
//
//		//加密方法
//		System.out.println(stringEncryptor.encrypt("jcweb"));
//		System.out.println(stringEncryptor.encrypt("jcweb123"));
//
//		System.out.println("jcwl="+stringEncryptor.encrypt("jcwl"));
//		System.out.println("jcwl1234="+stringEncryptor.encrypt("jcwl1234"));
//		System.out.println(encodeKey + "="+stringEncryptor.encrypt(encodeKey));
//
//		//解密方法
//		//System.out.println(stringEncryptor.decrypt("ltJPpR50wT0oIY9kfOe1Iw==="));
//
//		System.out.println(stringEncryptor.decrypt("nv3yw7lmX0AysZqgrI7SSA=="));
//		System.out.println(stringEncryptor.decrypt("F+jfh1uWu0ypse95hQvSow=="));
//		System.out.println(stringEncryptor.decrypt("bmg1BKLy+hE9gAY9jRDeKA=="));
//		System.out.println(stringEncryptor.decrypt("5GG8w2c3AVqBSqeG/fpoGkRF1zkGWxaC"));
//
//
//		String aa = "amN3bDpqY3ds";
//		byte[] base64Token = aa.getBytes("UTF-8");
//		byte[] decoded;
//		try {
//			decoded = Base64.decode(base64Token);
//		} catch (IllegalArgumentException e) {
//			throw new CheckedException(
//					"Failed to decode basic authentication token");
//		}
//
//		String token = new String(decoded, StandardCharsets.UTF_8);
//
//		//byte[] result = Base64.decode("cGlnOnBpZw==");
//		System.out.println("token = "  + token);
//
//		String jcweb = "jcweb:jcweb";
//		System.out.println("jcweb:"+ Base64.encode(jcweb.getBytes(StandardCharsets.UTF_8)));
//
//		String jcapp = "jcapp:jcapp";
//		System.out.println("jcapp:"+ Base64.encode(jcapp.getBytes(StandardCharsets.UTF_8)));
//
//		String wxmini = "wxmini:wxmini";
//		System.out.println("wxmini:"+ Base64.encode(wxmini.getBytes(StandardCharsets.UTF_8)));
//		//System.out.println(decryptAES("xG5RfALdyO2GzhMqQdFK5Q==","jcwl"));
//
//
//		System.out.println(decryptAES("KRWiQ/dT81XI9nushc31MA==","jcwljcwljcwljcwl"));
//		//System.out.println(encryptAES("123456789123456789aaaaaa","jcwljcwljcwljcwl"));
//		System.out.println("=================================================");
//		System.out.println(Pkcs7Encoder.decryptAES("KRWiQ/dT81XI9nushc31MA==","jcwljcwljcwljcwl"));
//	}
//
//	private static String decryptAES1(String data, String pass) {
//		String b  = "1";
//		try {
//			byte[] decryptKey = new BASE64Decoder().decodeBuffer(data);
//			KeyGenerator kgen = KeyGenerator.getInstance("AES");
//			kgen.init(128);
//			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
//			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(pass.getBytes(), "AES/ECB/PKCS5Padding"));
//			byte[] decryptBytes = cipher.doFinal(decryptKey);
//			b  = new String(decryptBytes, StandardCharsets.UTF_8);
//		}catch(Exception ex){System.out.println("aaaerror");}
//		return b;
//	}
//	private String decryptAES(String data, String pass) {
//		AES aes = new AES(Mode.CBC, Padding.PKCS5Padding,
//				new SecretKeySpec(pass.getBytes(), "AES/ECB/PKCS5Padding"),
//				new IvParameterSpec(pass.getBytes()));
//		byte[] result = aes.decrypt(Base64.decode(data.getBytes(StandardCharsets.UTF_8)));
//		return new String(result, StandardCharsets.UTF_8);
//	}
//
//	private String encryptAES(String data, String pass) {
//
//		AES aes = new AES(Mode.CBC,Padding.NoPadding,
//				new SecretKeySpec(pass.getBytes(), "AES"),
//				new IvParameterSpec(pass.getBytes()));
//
//		byte[] result = aes.encrypt(Base64.decode(data.getBytes(StandardCharsets.UTF_8)));
//		return new String(result, StandardCharsets.UTF_8);
//	}
//}
