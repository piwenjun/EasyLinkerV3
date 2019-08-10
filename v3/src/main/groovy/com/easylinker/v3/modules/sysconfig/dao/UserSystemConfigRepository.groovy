package com.easylinker.v3.modules.sysconfig.dao

import com.easylinker.v3.modules.sysconfig.model.UserSystemConfig
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author wwhai* @date 2019/8/10 22:45
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
interface UserSystemConfigRepository extends JpaRepository<UserSystemConfig, Long> {
    UserSystemConfig findTopByUserSecurityId(String userSecurityId)
}
