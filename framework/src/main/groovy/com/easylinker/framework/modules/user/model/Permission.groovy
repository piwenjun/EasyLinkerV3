package com.easylinker.framework.modules.user.model

import com.easylinker.framework.common.model.AbstractModel
import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToOne

@Entity
class Permission extends AbstractModel {
    private String name
    private String info
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    private Role role

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

    Role getRole() {
        return role
    }

    void setRole(Role role) {
        this.role = role
    }
}
