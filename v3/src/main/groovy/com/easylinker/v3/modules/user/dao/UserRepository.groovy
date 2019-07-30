package com.easylinker.v3.modules.user.dao


import com.easylinker.v3.modules.user.model.AppUser
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findTopByPrinciple(String principle)
    AppUser findTopByEmail(String email)

    AppUser findTopBySecurityId(String securityId)

}
