package com.easylinker.v3.modules.deviceecho.dao

import com.easylinker.v3.modules.deviceecho.model.DeviceOperateEcho
import com.easylinker.v3.modules.deviceecho.model.DeviceOperateLog
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

/**
 *
 * 日志数据，用MongoDB保存
 * @author wwhai* @date 2019/7/23 23:07
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
interface DeviceOperateLogRepository extends MongoRepository<DeviceOperateLog, Long> {
    Page<DeviceOperateLog> findAllByUserSecurityIdAndDeviceSecurityId(String var1, String var2, Pageable v3)
}

interface DeviceOperateEchoRepository extends MongoRepository<DeviceOperateEcho, Long> {
    Page<DeviceOperateEcho> findAllByDeviceOperateLogSecurityId(String var1, Pageable v2)
}
