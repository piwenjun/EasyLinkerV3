package com.easylinker.framework.modules.device.dao

import com.easylinker.framework.modules.device.model.Device
import com.easylinker.framework.modules.user.model.AppUser
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author wwhai* @date 2019/6/7 10:34
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
interface DeviceRepository extends JpaRepository<Device, Long> {

    Device findTop1ById(long id)

}
