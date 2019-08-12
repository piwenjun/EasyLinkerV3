package com.easylinker.v3.modules.device.model


import com.easylinker.v3.common.model.AbstractDevice
import com.easylinker.v3.modules.scene.model.Scene
import com.easylinker.v3.modules.user.model.AppUser
import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToOne

/**
 * @author wwhai* @date 2019/6/29 22:58
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@Entity
class CoAPDevice extends AbstractDevice {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private AppUser appUser
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Scene scene

    Scene getScene() {
        return scene
    }


    void setScene(Scene scene) {
        this.scene = scene
    }

    AppUser getAppUser() {
        return appUser
    }

    void setAppUser(AppUser appUser) {
        this.appUser = appUser
    }

}
