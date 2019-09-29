//package com.iyysoft.msdp.common.utils.token;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTDecodeException;
//import com.auth0.jwt.interfaces.DecodedJWT;
//
//import java.util.Date;
//
///**
// * 令牌类
// * @author chimao
// * @date 9:23 2018-06-23
// */
//public class JwtUtil2 {
//    // 过期时间5分钟
//    private static final long expire_time = 5*60*1000;
//
//    /*
//    iss: jwt签发者
//    sub: jwt所面向的用户
//    aud: 接收jwt的一方
//    exp: jwt的过期时间，这个过期时间必须要大于签发时间
//    nbf: 定义在什么时间之前，该jwt都是不可用的.
//    iat: jwt的签发时间
//    jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
//     */
//    /**
//     * 校验token是否正确
//     * @param token 密钥
//     * @param secret 用户的密码
//     * @return 是否正确
//     */
//    public static boolean verify(String token, String account, String secret) {
//        try {
//            Algorithm algorithm = Algorithm.HMAC256(secret);
//            JWTVerifier verifier = JWT.require(algorithm)
//                    .withClaim("account", account)
//                    .build();
//            //DecodedJWT jwt = verifier.verify(token);
//            verifier.verify(token);
//            return true;
//        } catch (Exception exception) {
//            return false;
//        }
//    }
//
//    /**
//     * 获得token中的信息无需secret解密也能获得
//     * @return token中包含的用户名
//     */
//    public static String getAccount(String token) {
//        try {
//            DecodedJWT jwt = JWT.decode(token);
//            return jwt.getClaim("account").asString();
//        } catch (JWTDecodeException e) {
//            return null;
//        }
//    }
//
//    /**
//     * 生成签名,5min后过期
//     * @param account 用户名
//     * @param secret 用户的密码
//     * @return 加密的token
//     */
//    public static String sign(String account, String secret) {
//        Date date = new Date(System.currentTimeMillis() + expire_time);
//        Algorithm algorithm = Algorithm.HMAC256(secret);
//        // 附带username信息
//        return JWT.create()
//                .withClaim("account", account)
//                .withExpiresAt(date)
//                .sign(algorithm);
//    }
//}
