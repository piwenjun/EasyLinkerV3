package com.easylinker.v3.modules.device.dao

import com.easylinker.framework.modules.user.model.AppUser
import com.easylinker.v3.modules.device.model.COAPDevice
import com.easylinker.v3.modules.device.model.HTTPDevice
import com.easylinker.v3.modules.device.model.MQTTDevice
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 这种写法看起来是不是不舒服？蛋疼就对了，这是Groovy的风格，没有Java那么强制性，一个文件可写很多个类
 * @author wwhai* @date 2019/6/30 23:07
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */

interface HTTPRepository extends JpaRepository<HTTPDevice, Long> {

    Page<HTTPDevice> findAllByAppUser(Pageable page, AppUser appUser)
}

interface COAPRepository extends JpaRepository<COAPDevice, Long> {
    Page<COAPDevice> findAllByAppUser(Pageable page, AppUser appUser)

}

interface MQTTRepository extends JpaRepository<MQTTDevice, Long> {

    Page<MQTTDevice> findAllByAppUser(Pageable page, AppUser appUser)

}


