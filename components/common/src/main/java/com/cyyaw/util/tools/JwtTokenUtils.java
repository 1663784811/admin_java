package com.cyyaw.util.tools;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * iss: jwt签发者
 * sub: jwt所面向的用户
 * aud: 接收jwt的一方
 * exp: jwt的过期时间，这个过期时间必须要大于签发时间
 * nbf: 定义在什么时间之前，该jwt都是不可用的.
 * iat: jwt的签发时间
 * jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
 * secret : secret私钥
 */
@Slf4j
public class JwtTokenUtils {
    public static final String LOGIN_INFO_KEY = "loginInfo";
    public static final String TOKEN_HEADER = "token";
    public static final String TOKEN_PREFIX = "JWT:";
    // iss: jwt签发者
    public static final String ISS = "cyyaw";
    // sub: jwt所面向的用户
    public static final String SUB = "sso_";
    // 过期时间
    public static final long EXP = System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 365;
    // secret
    public static final String secret = "@#vws##35SW324";
    private static final Algorithm algorithm = Algorithm.HMAC256(secret);
    private static final JWTVerifier verifier;

    static {
        verifier = JWT.require(algorithm).withIssuer(ISS).build();
    }


    /**
     * 生成Token
     */
    public static String createToken(String id, String data) {
        String token = JWT.create()
                .withIssuer(ISS)
                .withSubject(SUB)
                .withExpiresAt(new Date(EXP))
                .withNotBefore(new Date())
                .withIssuedAt(new Date())
                .withJWTId(id)
                .withClaim("claim", data)
                .sign(algorithm);
        return TOKEN_PREFIX + token;
    }

    public static String getClaim(String token) {
        try {
            DecodedJWT jwt = verifier.verify(token);
            String claim = jwt.getClaim("claim").asString();
            return claim;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取id
     *
     * @param token
     * @return
     */
    public static String getId(String token) {
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getId();
    }

    /**
     * 验证token
     *
     * @param token
     * @return
     */
    public static boolean verifierToken(String token) {
        try {
            DecodedJWT jwt = verifier.verify(token);
            return jwt != null;
        } catch (Exception e) {
            return false;
        }
    }

}
