package com.easylinker.v3.modules.devicedata.service

import com.easylinker.framework.common.model.DeviceType
import com.easylinker.v3.modules.devicedata.dao.DeviceDataRepository
import com.easylinker.v3.modules.devicedata.model.DeviceData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class DeviceDataService {
    @Autowired
    DeviceDataRepository deviceDataRepository

    /**
     * 查询数据
     * @param deviceSecurityId
     * @param deviceType
     * @param pageable
     * @return
     */
    Page<DeviceData> list(String deviceSecurityId, DeviceType deviceType, Pageable pageable) {

        return deviceDataRepository.findAllByDeviceSecurityIdAndDeviceType(deviceSecurityId,deviceType, pageable)

    }

/**
 *
 * @param deviceSecurityId
 * @param deviceType
 * @param pageable
 * @return
 */

    void save(DeviceData deviceData) {

        deviceDataRepository.save(deviceData)
    }
}

