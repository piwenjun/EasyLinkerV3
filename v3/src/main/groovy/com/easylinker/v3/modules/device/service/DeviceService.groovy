package com.easylinker.v3.modules.device.service

import com.easylinker.framework.common.model.AbstractDevice
import com.easylinker.framework.common.model.DeviceProtocol
import com.easylinker.framework.common.model.DeviceType
import com.easylinker.framework.modules.user.model.AppUser
import com.easylinker.v3.modules.device.dao.*
import com.easylinker.v3.modules.device.model.*
import com.easylinker.v3.modules.scene.dao.SceneRepository
import com.easylinker.v3.modules.scene.model.Scene
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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
    @Autowired
    TerminalHostDeviceRepository terminalHostDeviceRepository

    @Autowired
    TopicAclRepository topicAclRepository
    /**
     * 统计报表:ADMIN看的
     * @return
     */
    Map analyzeDeviceData() {
        Map data = new HashMap()
        Map mqttCount = new HashMap()
        mqttCount.put("online", mqttRepository.countByOnline(true))
        mqttCount.put("total", mqttRepository.count())
        data.put("coap", coapRepository.count())
        data.put("http", httpRepository.count())
        data.put("mqtt", mqttCount)
        return data
    }

    /**
     * 根据用户来选择统计报表
     * @param appUser
     * @return
     */
    @Autowired
    SceneRepository sceneRepository

    Map analyzeDeviceData(AppUser appUser) {
        Map data = new HashMap()
        data.put("coap", coapRepository.countByAppUser(appUser))
        data.put("http", httpRepository.countByAppUser(appUser))
        data.put("mqtt", mqttRepository.countByAppUser(appUser))
        data.put("scene", sceneRepository.countByAppUser(appUser))
        return data
    }


    /**
     * 添加设备
     * @param abstractDevice
     */
    void add(AbstractDevice abstractDevice) {
        switch (abstractDevice.deviceProtocol) {
            case DeviceProtocol.HTTP:
                httpRepository.save(abstractDevice as HTTPDevice)
                break
            case DeviceProtocol.COAP:
                coapRepository.save(abstractDevice as COAPDevice)
                break
            case DeviceProtocol.MQTT:
                mqttRepository.save(abstractDevice as MQTTDevice)
                break
            default: break
        }

    }

    /**
     * 添加默认的ACL
     * @param mqttDevice
     */

    private void addDefaultAcls(MQTTDevice mqttDevice) {

        //auth.mysql.acl_query = select allow,ip AS ipaddr, username, client_id AS clientid, access, topic from topic_acl where  username = '%u' or username = '$all'  or client_id = '%c';
        //1: subscribe, 2: publish, 3: pubsub
        List<TopicAcl> topicAcls = new ArrayList<>()
        TopicAcl inAcl = new TopicAcl(ip: "", access: 1, topic: "/device/" + mqttDevice.getSecurityId() + "/s2c", clientId: mqttDevice.clientId, username: mqttDevice.username, mqttDevice: mqttDevice)
        TopicAcl outAcl = new TopicAcl(ip: "", access: 2, topic: "/device/" + mqttDevice.getSecurityId() + "/c2s", clientId: mqttDevice.clientId, username: mqttDevice.username, mqttDevice: mqttDevice)
        topicAcls.add(inAcl)
        topicAcls.add(outAcl)
        mqttDevice.setTopicAcls(topicAcls)
        topicAclRepository.save(inAcl)
        topicAclRepository.save(outAcl)

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
        addDefaultAcls(mqttDevice)
    }

    void addTerminal(TerminalHostDevice terminalHostDevice) {
        terminalHostDeviceRepository.save(terminalHostDevice)
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
            default: return null
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
            default: return null
        }

    }

    /**
     * 根据设备类型查找
     * @param pageable
     * @param appUser
     * @param deviceType
     * @return
     */
    Page<AbstractDevice> listByType(AppUser appUser, DeviceType deviceType, Pageable pageable) {
        switch (deviceType) {
            case DeviceProtocol.HTTP:
                return httpRepository.findAllByAppUserAndDeviceType(appUser, deviceType, pageable)
            case DeviceProtocol.COAP:
                return coapRepository.findAllByAppUserAndDeviceType(appUser, deviceType, pageable)
            case DeviceProtocol.MQTT:
                return mqttRepository.findAllByAppUserAndDeviceType(appUser, deviceType, pageable)
            default: return null
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
            default: return null
        }

    }

    /**
     * 根据ID获取
     * @param id
     * @return
     */
    HTTPDevice getHttpById(Long id) {
        return httpRepository.findById(id).get()

    }

    COAPDevice getCoapById(Long id) {
        return coapRepository.findById(id).get()

    }

    MQTTDevice getMqttById(Long id) {
        return mqttRepository.findById(id).get()

    }


    /**
     * 搜索Mqtt
     * ExampleMatcher 比较讲究，记录一下
     * withMatcher：参数是Entity的属性，不是表的字段
     * ExampleMatcher.GenericPropertyMatchers：有好几个值，对应了SQL的 AND OR 等等逻辑操作
     * Example.of：可以返回list和Page
     * @return
     */
    @Transactional
    Page<MQTTDevice> searchMqtt(MQTTDevice mqttDevice, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("info", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("clientId", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnoreNullValues()
                .withIgnorePaths("id", "isSuperUser", "password", "securityId", "createTime", "updateTime")

        Example<MQTTDevice> example = Example.of(mqttDevice, matcher)

        return mqttRepository.findAll(example, pageable)

    }
/**
 * 搜索Http
 * @param httpDevice
 * @param pageable
 * @return
 */
    @Transactional
    Page<HTTPDevice> searchHttp(HTTPDevice httpDevice, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("info", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnoreNullValues()
                .withIgnorePaths("id", "securityId", "token", "clientId", "createTime", "updateTime")

        Example<HTTPDevice> example = Example.of(httpDevice, matcher)
        return httpRepository.findAll(example, pageable)

    }

    /**
     * 搜索COAP
     * @param coapDevice
     * @param pageable
     * @return
     */
    @Transactional
    Page<COAPDevice> searchCoap(COAPDevice coapDevice, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("info", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnoreNullValues()
                .withIgnorePaths("id", "securityId", "token", "clientId", "createTime", "updateTime")
        Example<COAPDevice> example = Example.of(coapDevice, matcher)
        return coapRepository.findAll(example, pageable)

    }


    /**
     * 获取设备的详细资料
     * @param securityId
     * @return
     */

    AbstractDevice detail(String securityId, DeviceProtocol deviceProtocol) {
        switch (deviceProtocol) {
            case DeviceProtocol.MQTT:
                return mqttRepository.findBySecurityId(securityId)
            case DeviceProtocol.COAP:
                return coapRepository.findBySecurityId(securityId)
            case DeviceProtocol.HTTP:
                return httpRepository.findBySecurityId(securityId)
            default: return null

        }
    }
    /**
     * 根据用户查找设备
     * @param appUser
     * @return
     */

    Page<AbstractDevice> listDeviceByAppUser(AppUser appUser, DeviceProtocol deviceProtocol, Pageable pageable) {
        switch (deviceProtocol) {
            case DeviceProtocol.MQTT:
                return mqttRepository.findAllByAppUser(pageable, appUser)
            case DeviceProtocol.COAP:
                return coapRepository.findAllByAppUser(pageable, appUser)
            case DeviceProtocol.HTTP:
                return httpRepository.findAllByAppUser(pageable, appUser)
            default: return null

        }
    }
    /**
     * 根据场景查找设备
     * @param appUser
     * @return
     */
    Page<AbstractDevice> listDeviceByScene(Scene scene, DeviceProtocol deviceProtocol, Pageable pageable) {
        switch (deviceProtocol) {
            case DeviceProtocol.MQTT:
                return mqttRepository.findAllBySceneAndDeviceProtocol(scene, deviceProtocol, pageable)

            case DeviceProtocol.COAP:
                return coapRepository.findAllBySceneAndDeviceProtocol(scene, deviceProtocol, pageable)

            case DeviceProtocol.HTTP:
                return httpRepository.findAllBySceneAndDeviceProtocol(scene, deviceProtocol, pageable)

            default: return null

        }
    }

}
