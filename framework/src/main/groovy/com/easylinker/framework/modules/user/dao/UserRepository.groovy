package com.easylinker.framework.modules.user.dao

import com.easylinker.framework.modules.user.model.AppUser
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findTopByPrinciple(String principle)
    AppUser findTopByEmail(String email)

}
