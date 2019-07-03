package com.easylinker.framework.modules.user.model

import com.easylinker.framework.common.model.AbstractModel
import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.*

@Entity
class Role extends AbstractModel {
    private String name
    private String info
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private AppUser appUser

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, mappedBy = "role")
    private List<Permission> permissions

    List<Permission> getPermissions() {
        return permissions
    }

    void setPermissions(List<Permission> permissions) {
        this.permissions = permissions
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    String getInfo() {
        return info
    }

    void setInfo(String info) {
        this.info = info
    }

    AppUser getAppUser() {
        return appUser
    }

    void setAppUser(AppUser appUser) {
        this.appUser = appUser
    }
}
