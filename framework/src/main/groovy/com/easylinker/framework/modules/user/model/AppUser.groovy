package com.easylinker.framework.modules.user.model

import com.easylinker.framework.common.model.AbstractModel
import com.easylinker.framework.modules.device.model.Device
import lombok.Data

import javax.persistence.*


@Entity
@Data
class AppUser extends AbstractModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id
    private String principle
    private String password
    private String email
    private String name
    private Integer state
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "appUser")
    private List<Role> roles
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "appUser")
    private List<Device> devices

    Long getId() {
        return id
    }

    void setId(Long id) {
        this.id = id
    }

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

    List<Device> getDevices() {
        return devices
    }

    void setDevices(List<Device> devices) {
        this.devices = devices
    }
}
