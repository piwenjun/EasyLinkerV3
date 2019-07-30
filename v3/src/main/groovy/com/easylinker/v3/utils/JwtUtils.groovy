//

package com.easylinker.v3.utils

import com.easylinker.v3.modules.user.model.AppUser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.servlet.http.HttpServletRequest

/**
 * jwt工具类
 *
 */
class JwtUtils {
    private static Logger logger = LoggerFactory.getLogger(JwtUtils.class)

    private static String secret = "secret_key"

    /**
     * 生成jwt token
     */
    static String token(HashMap<String, Object> map) {
        Date nowDate = new Date()
        Date expireDate = new Date(nowDate.getTime() + 3_600_0000L)

        Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setClaims(map)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact()
    }

    /**
     * 校验token
     * @param token
     * @return
     */
    static boolean verify(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody()
            true
        } catch (Exception e) {

            false
        }
    }
    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    static Map<String, Object> getMap(String token) throws Exception {


        Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()


    }

    static Map<String, Object> getMap(HttpServletRequest httpServletRequest) throws Exception {


        Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(httpServletRequest.getHeader("token"))
                .getBody()


    }
    /**
     * 获取当前User
     * @param request
     * @return
     */
    static AppUser getCurrentUser(HttpServletRequest request)  throws Exception {

        String principle = getMap(request.getHeader("token")).get("principle") as String

        return new AppUser(principle: principle)
    }

}
