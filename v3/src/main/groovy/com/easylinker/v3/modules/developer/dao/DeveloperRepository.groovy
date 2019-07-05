package com.easylinker.v3.modules.developer.dao

import com.easylinker.framework.modules.user.model.AppUser
import com.easylinker.v3.modules.developer.model.Developer
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author wwhai* @date 2019/7/5 22:44
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
interface DeveloperRepository extends JpaRepository<Developer, Long> {
    Developer findTopByAppUser(AppUser appUser)

}