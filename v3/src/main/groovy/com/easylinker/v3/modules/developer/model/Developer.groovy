package com.easylinker.v3.modules.developer.model

import com.easylinker.framework.common.model.AbstractModel
import com.easylinker.framework.modules.user.model.AppUser
import org.apache.commons.codec.digest.DigestUtils

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToOne

/**
 * 开发者功能
 */
@Entity
class Developer extends AbstractModel {
    // 开发者等级
    private int level
    // 密钥
    private String secretKey = DigestUtils.sha256Hex(UUID.randomUUID().toString())

    String getSecretKey() {
        return secretKey
    }

    void setSecretKey(String secretKey) {
        this.secretKey = secretKey
    }
    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser appUser

    int getLevel() {
        return level
    }

    void setLevel(int level) {
        this.level = level
    }

    AppUser getAppUser() {
        return appUser
    }

    void setAppUser(AppUser appUser) {
        this.appUser = appUser
    }
}
/**
 * 开发者开通的APP
 */
@Entity
class DevelopApp extends AbstractModel {
    private String appName
    private String appInfo
    private String appKey = DigestUtils.sha256Hex(UUID.randomUUID().toString())

    @ManyToOne(fetch = FetchType.LAZY)
    private Developer developer

    String getAppKey() {
        return appKey
    }

    void setAppKey(String appKey) {
        this.appKey = appKey
    }

    Developer getDeveloper() {
        return developer
    }

    void setDeveloper(Developer developer) {
        this.developer = developer
    }

    String getAppName() {
        return appName
    }

    void setAppName(String appName) {
        this.appName = appName
    }

    String getAppInfo() {
        return appInfo
    }

    void setAppInfo(String appInfo) {
        this.appInfo = appInfo
    }
}