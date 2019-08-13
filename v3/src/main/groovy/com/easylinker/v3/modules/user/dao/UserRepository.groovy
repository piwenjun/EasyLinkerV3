package com.easylinker.v3.modules.user.dao


import com.easylinker.v3.modules.user.model.AppUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findTopByPrinciple(String principle)
    AppUser findTopByEmail(String email)

    AppUser findTopBySecurityId(String securityId)

    /* Ìõ¼þ²éÑ¯
    *
    * @param keyWords
    * @param pageable
    * @return
    */

    @Query("select appuser from AppUser appuser where  appuser.principle like %:keyWords%  or appuser.name  like %:keyWords% or appuser.email  like %:keyWords% ")
    Page<AppUser> search(@Param(value = "keyWords") String keyWords, Pageable pageable);

}
