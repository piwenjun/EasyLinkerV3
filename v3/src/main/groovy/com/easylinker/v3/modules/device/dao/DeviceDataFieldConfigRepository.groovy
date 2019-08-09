package com.easylinker.v3.modules.device.dao

import com.easylinker.v3.modules.device.model.DeviceDataFieldConfig
import org.springframework.data.jpa.repository.JpaRepository

interface DeviceDataFieldConfigRepository extends JpaRepository<DeviceDataFieldConfig, Long> {
    DeviceDataFieldConfig findTopByDeviceSecurityId(String deviceSecurityId)
}
