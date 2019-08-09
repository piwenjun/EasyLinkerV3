package com.easylinker.v3.modules.device.service

import com.easylinker.framework.common.service.AbstractService
import com.easylinker.v3.common.model.AbstractDevice
import com.easylinker.v3.common.model.DeviceProtocol
import com.easylinker.v3.modules.device.dao.DeviceDataFieldConfigRepository
import com.easylinker.v3.modules.device.model.DeviceDataFieldConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class DeviceDataFieldConfigService extends AbstractService<DeviceDataFieldConfig> {
    @Autowired
    DeviceDataFieldConfigRepository dataFieldConfigRepository
    @Autowired
    DeviceService deviceService

    @Override
    void save(DeviceDataFieldConfig deviceDataFieldConfig) {
        dataFieldConfigRepository.save(deviceDataFieldConfig)
    }

    @Override
    Page<DeviceDataFieldConfig> page(Pageable pageable) {
        return null
    }

    @Override
    DeviceDataFieldConfig getById(long id) {
        return dataFieldConfigRepository.getOne(id)
    }

    @Override
    void delete(DeviceDataFieldConfig deviceDataFieldConfig) {
        dataFieldConfigRepository.delete(deviceDataFieldConfig)
    }

    @Override
    void deleteById(long id) {
        dataFieldConfigRepository.deleteById(id)
    }


    /**
     * 更新设备的字段配置
     * @param deviceSecureId
     * @param fields
     */
    Boolean updateByDevice(String deviceSecureId, DeviceProtocol deviceProtocol, List<Map<String, Object>> fields) {

        AbstractDevice device = deviceService.detail(deviceSecureId, deviceProtocol)
        if (device) {
            DeviceDataFieldConfig deviceDataFieldConfig = getByDevice(deviceSecureId)
            if (deviceDataFieldConfig) {
                deviceService.createFieldConfig(device, fields)
                return true
            }
        }
        return false
    }

    /**
     * 查找某设备的字段配置
     * @param deviceSecureId
     * @return
     */
    DeviceDataFieldConfig getByDevice(String deviceSecureId) {
        return dataFieldConfigRepository.findTopByDeviceSecurityId(deviceSecureId)
    }
}
