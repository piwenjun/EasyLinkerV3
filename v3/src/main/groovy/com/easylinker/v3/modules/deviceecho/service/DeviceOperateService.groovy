package com.easylinker.v3.modules.deviceecho.service

import com.easylinker.framework.common.service.AbstractService
import com.easylinker.v3.modules.deviceecho.dao.DeviceOperateEchoRepository
import com.easylinker.v3.modules.deviceecho.dao.DeviceOperateLogRepository
import com.easylinker.v3.modules.deviceecho.model.DeviceOperateEcho
import com.easylinker.v3.modules.deviceecho.model.DeviceOperateLog
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

/**
 * @author wwhai* @date 2019/7/23 23:06
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@Service
class DeviceOperateLogService extends AbstractService<DeviceOperateLog> {
    @Autowired
    DeviceOperateLogRepository deviceOperateLogRepository

    @Override
    void save(DeviceOperateLog deviceOperateLog) {

    }

    @Override
    Page<DeviceOperateLog> page(Pageable pageable) {
        return null
    }

    @Override
    DeviceOperateLog getById(long id) {
        return null
    }

    @Override
    void delete(DeviceOperateLog deviceOperateLog) {

    }

    @Override
    void deleteById(long id) {

    }

    Page<DeviceOperateLog> list(String v1, String v2, Pageable v3) {
        return deviceOperateLogRepository.findAllByUserSecurityIdAndDeviceSecurityId(v1, v2, v3)
    }
}

@Service
class DeviceOperateEchoService extends AbstractService<DeviceOperateEcho> {
    @Autowired
    DeviceOperateEchoRepository deviceOperateEchoRepository

    @Override
    void save(DeviceOperateEcho deviceOperateEcho) {
        deviceOperateEchoRepository.save(deviceOperateEcho)
    }

    @Override
    Page<DeviceOperateEcho> page(Pageable pageable) {
        return deviceOperateEchoRepository.findAll(pageable)
    }

    @Override
    DeviceOperateEcho getById(long id) {
        return deviceOperateEchoRepository.getOne(id)
    }

    @Override
    void delete(DeviceOperateEcho deviceOperateEcho) {

        deviceOperateEchoRepository.delete(deviceOperateEcho)
    }

    @Override
    void deleteById(long id) {
        deviceOperateEchoRepository.deleteById(id)
    }

    Page<DeviceOperateEcho> list(String var1, Pageable var2) {

        return deviceOperateEchoRepository.findAllByDeviceOperateLogSecurityId(var1, var2)
    }
}
