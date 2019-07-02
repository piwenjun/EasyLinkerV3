package com.easylinker.framework.common.config.security

class AUthInfo {

}

abstract class SecurityContext {

    protected abstract boolean login(String principle, String password)

    protected abstract void logOut()
}

/**
 * 认证规则实现类
 */
abstract class SecurityPrinciple {


}
