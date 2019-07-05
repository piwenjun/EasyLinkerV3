package com.easylinker.v3.modules.devicedata.service

import com.easylinker.framework.common.model.DeviceType
import com.easylinker.v3.modules.devicedata.dao.BooleanDeviceDataRepository
import com.easylinker.v3.modules.devicedata.dao.FileDeviceDataRepository
import com.easylinker.v3.modules.devicedata.dao.TextDeviceDataRepository
import com.easylinker.v3.modules.devicedata.dao.ValueDeviceDataRepository
import com.easylinker.v3.modules.devicedata.model.DeviceData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class DeviceDataService {
    @Autowired
    BooleanDeviceDataRepository booleanDeviceDataRepository
    @Autowired
    FileDeviceDataRepository fileDeviceDataRepository
    @Autowired
    TextDeviceDataRepository textDeviceDataRepository
    @Autowired
    ValueDeviceDataRepository valueDeviceDataRepository

    /**
     * 查询数据
     * @param deviceSecurityId
     * @param deviceType
     * @param pageable
     * @return
     */
    Page<DeviceData> list(String deviceSecurityId, DeviceType deviceType, Pageable pageable) {

        switch (deviceType) {
            case DeviceType.VALUE:
                return valueDeviceDataRepository.findAllByDeviceSecurityId(deviceSecurityId, pageable)
            case DeviceType.TEXT:
                return textDeviceDataRepository.findAllByDeviceSecurityId(deviceSecurityId, pageable)
            case DeviceType.BOOLEAN:
                return booleanDeviceDataRepository.findAllByDeviceSecurityId(deviceSecurityId, pageable)
            case DeviceType.FILE:
                return fileDeviceDataRepository.findAllByDeviceSecurityId(deviceSecurityId, pageable)
            default: break
        }

    }
}
