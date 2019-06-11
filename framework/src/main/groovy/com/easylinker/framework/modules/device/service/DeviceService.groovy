package com.easylinker.framework.modules.device.service

import com.easylinker.framework.common.service.AbstractService
import com.easylinker.framework.modules.device.dao.DeviceRepository
import com.easylinker.framework.modules.device.model.Device
import com.easylinker.framework.modules.user.model.AppUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

/**
 * @author wwhai* @date 2019/6/7 10:35
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@Service
class DeviceService extends AbstractService<Device> {
    @Autowired
    DeviceRepository deviceRepository

    @Override
    void save(Device device) {
        deviceRepository.save(device)

    }

    @Override
    Page<Device> page(Pageable pageable) {
        return deviceRepository.findAll(pageable)
    }

    Page<Device> findAllByAppUser(AppUser user, Pageable pageable) {
        return deviceRepository.findAllByAppUser(user, pageable)
    }

    @Override
    Device getById(long id) {
        return deviceRepository.findTop1ById(id)
    }

    @Override
    void deleteById(long id) {
        deviceRepository.deleteById(id)

    }
}
