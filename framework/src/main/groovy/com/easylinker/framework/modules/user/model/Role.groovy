package com.easylinker.framework.modules.user.model

import lombok.Data

import javax.persistence.*

@Entity
@Data
class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id
    private String name
    private String info
    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser appUser

    Long getId() {
        return id
    }

    void setId(Long id) {
        this.id = id
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
