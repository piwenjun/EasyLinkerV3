package com.easylinker.v3.modules.device.service

import com.easylinker.framework.common.model.AbstractDevice
import com.easylinker.framework.common.model.DeviceProtocol
import com.easylinker.framework.common.model.DeviceType
import com.easylinker.framework.modules.user.model.AppUser
import com.easylinker.v3.modules.device.dao.COAPRepository
import com.easylinker.v3.modules.device.dao.HTTPRepository
import com.easylinker.v3.modules.device.dao.MQTTRepository
import com.easylinker.v3.modules.device.model.COAPDevice
import com.easylinker.v3.modules.device.model.HTTPDevice
import com.easylinker.v3.modules.device.model.MQTTDevice
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

/**
 * Java习惯了看这个不习惯？没事多看看就适应了，Groovy格式没那么强制，.groovy是文件而不是类了
 * @author wwhai* @date 2019/6/30 23:05
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@Service
class DeviceService {
    @Autowired
    COAPRepository coapRepository
    @Autowired
    HTTPRepository httpRepository
    @Autowired
    MQTTRepository mqttRepository


    /**
     * 添加
     * @param abstractDevice
     * @param deviceProtocol
     */
    void add(AbstractDevice abstractDevice, DeviceProtocol deviceProtocol) {
        switch (deviceProtocol) {
            case DeviceProtocol.HTTP:
                httpRepository.save(abstractDevice as HTTPDevice)
                break;
            case DeviceProtocol.COAP:
                coapRepository.save(abstractDevice as COAPDevice)
                break;
            case DeviceProtocol.MQTT:
                mqttRepository.save(abstractDevice as MQTTDevice)
                break;
            default: break
        }

    }

    /**
     * 添加设备
     * @param httpDevice
     */
    void addHttpDevice(HTTPDevice httpDevice) {

        httpRepository.save(httpDevice)
    }

    void addCoapDevice(COAPDevice coapDevice) {

        coapRepository.save(coapDevice)
    }

    void addMqttDevice(MQTTDevice mqttDevice) {

        mqttRepository.save(mqttDevice)
    }


    /**
     * 无条件查询
     * @param pageable
     * @return
     */

    Page<AbstractDevice> list(Pageable pageable, DeviceProtocol deviceProtocol) {
        switch (deviceProtocol) {
            case DeviceProtocol.HTTP:
                return httpRepository.findAll(pageable)
            case DeviceProtocol.COAP:
                return coapRepository.findAll(pageable)
            case DeviceProtocol.MQTT:
                return mqttRepository.findAll(pageable)
            default: break
        }
    }

    Page<HTTPDevice> listHttpDevice(Pageable pageable) {
        return httpRepository.findAll(pageable)

    }

    Page<COAPDevice> listCoapDevice(Pageable pageable) {
        return coapRepository.findAll(pageable)

    }

    Page<MQTTDevice> listMqttDevice(Pageable pageable) {
        return mqttRepository.findAll(pageable)

    }
    /**
     * 根据协议类型查询
     * @param Pageable
     * @param appUser
     * @param deviceProtocol
     * @return
     */

    Page<AbstractDevice> listByUser(Pageable pageable, AppUser appUser, DeviceProtocol deviceProtocol) {
        switch (deviceProtocol) {
            case DeviceProtocol.HTTP:
                return httpRepository.findAllByAppUser(pageable, appUser)
            case DeviceProtocol.COAP:
                return coapRepository.findAllByAppUser(pageable, appUser)
            case DeviceProtocol.MQTT:
                return mqttRepository.findAllByAppUser(pageable, appUser)
            default: break
        }

    }

    /**
     * 根据设备类型查找
     * @param pageable
     * @param appUser
     * @param deviceType
     * @return
     */
    Page<AbstractDevice> listByType(Pageable pageable, AppUser appUser, DeviceType deviceType) {
        switch (deviceType) {
            default: break
        }

    }

    /**
     * 根据ID查找
     * @param id
     * @return
     */

    AbstractDevice getById(Long id, DeviceProtocol deviceProtocol) {
        switch (deviceProtocol) {
            case DeviceProtocol.HTTP:
                return httpRepository.findById(id) as HTTPDevice
            case DeviceProtocol.COAP:
                return coapRepository.findById(id) as COAPDevice
            case DeviceProtocol.MQTT:
                return mqttRepository.findById(id) as MQTTDevice
            default: break
        }

    }

    HTTPDevice getHttpById(Long id) {
        return httpRepository.findById(id).get()

    }

    COAPDevice getCoapById(Long id) {
        return coapRepository.findById(id).get()

    }

    MQTTDevice getMqttById(Long id) {
        return mqttRepository.findById(id).get()

    }


}
