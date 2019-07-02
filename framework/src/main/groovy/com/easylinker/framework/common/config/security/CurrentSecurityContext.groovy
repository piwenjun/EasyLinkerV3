package com.easylinker.framework.common.config.security

class CurrentSecurityContext  extends SecurityContext{
    @Override
    boolean login(String principle, String password) {
        return false
    }

    @Override
    void logOut() {

    }
}
