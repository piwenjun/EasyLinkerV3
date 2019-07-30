package com.easylinker.v3.modules.scene.dao


import com.easylinker.v3.modules.scene.model.Scene
import com.easylinker.v3.modules.user.model.AppUser
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface SceneRepository extends JpaRepository<Scene, Long> {
    long countByAppUser(AppUser appUser)

    Page<Scene> findByAppUser(AppUser appUser, Pageable pageable)

    Scene findBySecurityId(String securityId)
}
