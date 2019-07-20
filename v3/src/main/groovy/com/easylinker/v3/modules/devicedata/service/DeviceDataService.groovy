package com.easylinker.v3.modules.devicedata.service

import com.easylinker.framework.common.model.DeviceType
import com.easylinker.v3.modules.devicedata.dao.*
import com.easylinker.v3.modules.devicedata.model.*
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
    @Autowired
    SwitchDeviceDataRepository switchDeviceDataRepository

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
            case DeviceType.SWITCH:
                return switchDeviceDataRepository.findAllByDeviceSecurityId(deviceSecurityId, pageable)
            default: return null
        }

    }
    /**
     *
     * @param deviceSecurityId
     * @param deviceType
     * @param pageable
     * @return
     */

    void save(DeviceData deviceData, DeviceType deviceType) {

        switch (deviceType) {
            case DeviceType.VALUE:
                valueDeviceDataRepository.save(deviceData as ValueData)
                break
            case DeviceType.TEXT:
                textDeviceDataRepository.save(deviceData as TextData)
                break
            case DeviceType.BOOLEAN:
                booleanDeviceDataRepository.save(deviceData as BooleanData)
                break
            case DeviceType.FILE:
                fileDeviceDataRepository.save(deviceData as FileData)
                break
            case DeviceType.SWITCH:
                switchDeviceDataRepository.save(deviceData as SwitchData)
                break
            default: break
        }

    }

}
