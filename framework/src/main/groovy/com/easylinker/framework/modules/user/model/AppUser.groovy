package com.easylinker.framework.modules.user.model

import com.easylinker.framework.common.model.AbstractModel

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany

@Entity
class AppUser extends AbstractModel {

    private String principle
    private String password
    private String email
    private String name
    private Integer state
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "appUser")
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

    Integer getState() {
        return state
    }

    void setState(Integer state) {
        this.state = state
    }

    List<Role> getRoles() {
        return roles
    }

    void setRoles(List<Role> roles) {
        this.roles = roles
    }
}
