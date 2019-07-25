package com.easylinker.v3.modules.devicedata.dao

import com.easylinker.framework.common.model.DeviceType
import com.easylinker.v3.modules.devicedata.model.DeviceData
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface DeviceDataRepository extends MongoRepository<DeviceData, Long> {
    Page<DeviceData> findAllByDeviceSecurityIdAndDeviceType(String deviceSecurityId, DeviceType deviceType, Pageable pageable)
}


