package com.easylinker.framework.modules.user.model

import com.easylinker.framework.common.model.AbstractModel
import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.*

@Entity
class AppUser extends AbstractModel {

    private String principle
    private String password
    private String email
    private String name
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, mappedBy = "appUser")
    private List<Role> roles


    String getPrinciple() {
        return principle
    }

    void setPrinciple(String principle) {
        this.principle = principle
    }

    String getPassword() {
        return password
    }

    void setPassword(String password) {
        this.password = password
    }

    String getEmail() {
        return email
    }

    void setEmail(String email) {
        this.email = email
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    UserStatus getUserStatus() {
        return userStatus
    }

    void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus
    }

    List<Role> getRoles() {
        return roles
    }

    void setRoles(List<Role> roles) {
        this.roles = roles
    }
}

enum UserStatus {
    NORMAL,
    FORBID,
    FREEZE,

}