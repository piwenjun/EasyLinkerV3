package com.easylinker.framework.common.config.jwt

import lombok.Data
import org.apache.shiro.authc.AuthenticationToken

/**
 * Created by admin on 2019/6/5 10:13
 *
 */

@Data
class JWTToken implements AuthenticationToken{
    private String token

    JWTToken(String token) {
        this.token = token
    }

    @Override
    Object getPrincipal() {
        return token
    }

    @Override
    Object getCredentials() {
        return token
    }
}