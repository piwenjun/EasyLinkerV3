package com.easylinker.framework.modules.user.dao

import com.easylinker.framework.modules.user.model.AppUser
import com.easylinker.framework.modules.user.model.Role
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author wwhai* @date 2019/6/6 21:43
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAllByAppUser(AppUser user)
}
