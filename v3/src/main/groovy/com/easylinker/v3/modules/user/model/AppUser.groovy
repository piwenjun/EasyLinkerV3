package com.easylinker.v3.modules.user.model


import com.easylinker.v3.common.model.AbstractModel
import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.*

@Entity
class AppUser extends AbstractModel {

    private String principle
    private String password
    private String email
    private String name
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus = UserStatus.NORMAL
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.MERGE, CascadeType.REMOVE], mappedBy = "appUser")
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
/**
 * 用户的状态标识
 */
enum UserStatus {
    /**
     * 正常
     */
    NORMAL,
    /**
     * 禁止
     */
    FORBID,
    /**
     * 冻结
     */
    FREEZE,

}