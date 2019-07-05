package com.easylinker.v3.modules.developer.dao

import com.easylinker.v3.modules.developer.model.DevelopApp
import com.easylinker.v3.modules.developer.model.Developer
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author wwhai* @date 2019/7/5 23:21
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
interface DeveloperAppRepository extends JpaRepository<DevelopApp, Long> {
    Page<DevelopApp> findAllByDeveloper(Developer developer, Pageable pageable)


}
