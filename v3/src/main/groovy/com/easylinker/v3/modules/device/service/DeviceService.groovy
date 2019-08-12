package com.easylinker.v3.modules.device.service

import com.alibaba.fastjson.JSONObject
import com.easylinker.framework.utils.DeviceTokenUtils
import com.easylinker.v3.common.model.AbstractDevice
import com.easylinker.v3.common.model.DeviceProtocol
import com.easylinker.v3.common.model.DeviceType
import com.easylinker.v3.modules.device.dao.*
import com.easylinker.v3.modules.device.model.*
import com.easylinker.v3.modules.scene.dao.SceneRepository
import com.easylinker.v3.modules.scene.model.Scene
import com.easylinker.v3.modules.user.model.AppUser
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
    CoAPRepository coAPRepository
    @Autowired
    HTTPRepository httpRepository
    @Autowired
    MQTTRepository mqttRepository
    @Autowired
    TCPDeviceRepository tcpDeviceRepository
    @Autowired
    UDPDeviceRepository udpDeviceRepository
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
        data.put("CoAP", coAPRepository.count())
        data.put("HTTP", httpRepository.count())
        data.put("MQTT", mqttCount)
        return data
    }

    /**
     * 保存和更新
     * @param abstractDevice
     */
    @Transactional
    void save(AbstractDevice abstractDevice) {
        switch (abstractDevice.deviceProtocol) {
            case DeviceProtocol.HTTP:
                HTTPDevice device = abstractDevice as HTTPDevice
                if (device.scene) {
                    device.setSceneSecurityId(device.scene.securityId)
                }
                httpRepository.save(device)

                break
            case DeviceProtocol.CoAP:
                CoAPDevice device = abstractDevice as CoAPDevice
                if (device.scene) {
                    device.setSceneSecurityId(device.scene.securityId)
                }
                //保存的时候不用ACL
                coAPRepository.save(device)
                break
            case DeviceProtocol.MQTT:
                MQTTDevice device = abstractDevice as MQTTDevice
                if (device.scene) {
                    device.setSceneSecurityId(device.scene.securityId)
                }
                mqttRepository.save(device)

                break
            case DeviceProtocol.TCP:
                TCPDevice device = abstractDevice as TCPDevice
                if (device.scene) {
                    device.setSceneSecurityId(device.scene.securityId)
                }
                tcpDeviceRepository.save(device)

                break
            case DeviceProtocol.UDP:
                UDPDevice device = abstractDevice as UDPDevice
                if (device.scene) {
                    device.setSceneSecurityId(device.scene.securityId)
                }
                udpDeviceRepository.save(device)
                break
            default: break
        }

    }

    /**
     * 新建
     * @param abstractDevice
     */
    @Transactional
    void create(AbstractDevice abstractDevice) {
        //Token
        abstractDevice.setToken(DeviceTokenUtils.token(JSONObject.toJSONString([
                abstractDevice.securityId,
                abstractDevice.deviceType,
                JSONObject.toJSONString(abstractDevice.dataFields)
        ])))
        //入库
        switch (abstractDevice.deviceProtocol) {
            case DeviceProtocol.HTTP:
                HTTPDevice device = abstractDevice as HTTPDevice
                if (device.scene) {
                    device.setSceneSecurityId(device.scene.securityId)
                }

                httpRepository.save(device)

                break
            case DeviceProtocol.CoAP:
                CoAPDevice device = abstractDevice as CoAPDevice
                if (device.scene) {
                    device.setSceneSecurityId(device.scene.securityId)
                }
                // 新建的时候必须有默认ACL
                coAPRepository.save(device)

                break
            case DeviceProtocol.MQTT:
                MQTTDevice device = abstractDevice as MQTTDevice
                if (device.scene) {
                    device.setSceneSecurityId(device.scene.securityId)
                }
                mqttRepository.save(device)
                addDefaultAcls(device)

                break
            case DeviceProtocol.TCP:
                TCPDevice device = abstractDevice as TCPDevice
                if (device.scene) {
                    device.setSceneSecurityId(device.scene.securityId)
                }
                tcpDeviceRepository.save(device)

                break
            case DeviceProtocol.UDP:
                UDPDevice device = abstractDevice as UDPDevice
                if (device.scene) {
                    device.setSceneSecurityId(device.scene.securityId)
                }
                udpDeviceRepository.save(device)

                break
            default: break
        }

    }


    /**
     * 根据用户来选择统计报表
     * @param appUser
     * @return
     */
    @Autowired
    SceneRepository sceneRepository

    Map analyzeDeviceData(AppUser appUser) {
        Map<String, Object> deviceCountMap = new HashMap<>()
        Map mqttCount = new HashMap()
        mqttCount.put("online", mqttRepository.countByOnline(true))
        mqttCount.put("total", mqttRepository.countByAppUser(appUser))
        deviceCountMap.put("CoAP", coAPRepository.countByAppUser(appUser))
        deviceCountMap.put("HTTP", httpRepository.countByAppUser(appUser))
        deviceCountMap.put("MQTT", mqttCount)
        deviceCountMap.put("TCP", tcpDeviceRepository.countByAppUser(appUser))
        deviceCountMap.put("UDP", udpDeviceRepository.countByAppUser(appUser))
        deviceCountMap.put("scene", sceneRepository.countByAppUser(appUser))
        //统计各种类型的设备
        Map<String, Object> typeCountMap = new HashMap<>()
        typeCountMap.put("VALUE", mqttRepository.countByAppUserAndDeviceType(appUser, DeviceType.VALUE))
        typeCountMap.put("TEXT", mqttRepository.countByAppUserAndDeviceType(appUser, DeviceType.TEXT))
        typeCountMap.put("BOOLEAN", mqttRepository.countByAppUserAndDeviceType(appUser, DeviceType.BOOLEAN))
        typeCountMap.put("SWITCH", mqttRepository.countByAppUserAndDeviceType(appUser, DeviceType.SWITCH))
        typeCountMap.put("FILE", mqttRepository.countByAppUserAndDeviceType(appUser, DeviceType.FILE))

        Map<String, Object> countMap = new HashMap<>()
        countMap.put("deviceCount", deviceCountMap)
        countMap.put("typeCount", typeCountMap)

        return countMap
    }


    /**
     * 添加默认的ACL
     * @param mqttDevice
     */

    private void addDefaultAcls(MQTTDevice mqttDevice) {

        //auth.mysql.acl_query = select allow,ip AS ipaddr, username, client_id AS clientid, access, topic from topic_acl where  username = '%u' or username = '$all'  or client_id = '%c';
        //1: subscribe, 2: publish, 3: pubsub
        List<TopicAcl> topicAcls = new ArrayList<>()
        //接受数据的TOPIC
        TopicAcl inAcl = new TopicAcl(ip: "", access: 1, topic: "/device/" + mqttDevice.getSecurityId() + "/s2c", clientId: mqttDevice.clientId, username: mqttDevice.username, mqttDevice: mqttDevice)
        //发送数据的TOPIC
        TopicAcl outAcl = new TopicAcl(ip: "", access: 2, topic: "/device/" + mqttDevice.getSecurityId() + "/c2s", clientId: mqttDevice.clientId, username: mqttDevice.username, mqttDevice: mqttDevice)
        //上报状态的
        TopicAcl statusAcl = new TopicAcl(ip: "", access: 2, topic: "/device/" + mqttDevice.getSecurityId() + "/status", clientId: mqttDevice.clientId, username: mqttDevice.username, mqttDevice: mqttDevice)

        topicAcls.add(inAcl)
        topicAcls.add(outAcl)
        topicAcls.add(statusAcl)
        mqttDevice.setTopicAcls(topicAcls)
        topicAclRepository.save(inAcl)
        topicAclRepository.save(outAcl)
        topicAclRepository.save(statusAcl)

    }

    /**
     * 根据协议类型查询
     * @param Pageable
     * @param appUser
     * @param deviceProtocol
     * @return
     */

    Page<AbstractDevice> listByProtocol(Pageable pageable, AppUser appUser, DeviceProtocol deviceProtocol) {
        switch (deviceProtocol) {
            case DeviceProtocol.HTTP:
                return httpRepository.findAllByAppUser(pageable, appUser)
            case DeviceProtocol.CoAP:
                return coAPRepository.findAllByAppUser(pageable, appUser)
            case DeviceProtocol.MQTT:
                return mqttRepository.findAllByAppUser(pageable, appUser)
            case DeviceProtocol.TCP:
                return tcpDeviceRepository.findAllByAppUser(pageable, appUser)
            case DeviceProtocol.UDP:
                return udpDeviceRepository.findAllByAppUser(pageable, appUser)
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
            case DeviceProtocol.CoAP:
                return coAPRepository.findAllByAppUserAndDeviceType(appUser, deviceType, pageable)
            case DeviceProtocol.MQTT:
                return mqttRepository.findAllByAppUserAndDeviceType(appUser, deviceType, pageable)
            case DeviceProtocol.TCP:
                return tcpDeviceRepository.findAllByAppUserAndDeviceType(appUser, deviceType, pageable)
            case DeviceProtocol.UDP:
                return udpDeviceRepository.findAllByAppUserAndDeviceType(appUser, deviceType, pageable)
            default: return null
        }
    }


/**
 * 搜索Mqtt
 * ExampleMatcher 比较讲究，记录一下
 * withMatcher：参数是Entity的属性，不是表的字段
 * ExampleMatcher.GenericPropertyMatchers：有好几个值，对应了SQL的 AND OR 等等逻辑操作
 * Example.of：可以返回list和Page
 * @returnT
 */
    @Transactional
    Page<AbstractDevice> search(AbstractDevice abstractDevice, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("info", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnoreNullValues()
                .withIgnorePaths("id",
                        "online",
                        "token",
                        "isSuperUser",
                        "password",
                        "securityId",
                        "createTime",
                        "updateTime",
                        "isDelete",
                        "dataFields",
                        "lastActive")


        switch (abstractDevice.deviceProtocol) {
            case DeviceProtocol.MQTT:
                Example<MQTTDevice> example = Example.of(abstractDevice as MQTTDevice, matcher)

                return mqttRepository.findAll(example, pageable)

            case DeviceProtocol.TCP:
                Example<TCPDevice> example = Example.of(abstractDevice as TCPDevice, matcher)

                return tcpDeviceRepository.findAll(example, pageable)
            case DeviceProtocol.UDP:
                Example<UDPDevice> example = Example.of(abstractDevice as UDPDevice, matcher)

                return udpDeviceRepository.findAll(example, pageable)

            case DeviceProtocol.HTTP:
                Example<HTTPDevice> example = Example.of(abstractDevice as HTTPDevice, matcher)

                return httpRepository.findAll(example, pageable)

            case DeviceProtocol.CoAP:

                Example<CoAPDevice> example = Example.of(abstractDevice as CoAPDevice, matcher)

                return coAPRepository.findAll(example, pageable)
            default:
                return null


        }

    }


/**
 * 获取设备的详细资料
 * @param securityId
 * @return
 */

    AbstractDevice detail(String securityId, DeviceProtocol deviceProtocol) {
        switch (deviceProtocol) {
            case DeviceProtocol.MQTT:
                return mqttRepository.findBySecurityId(securityId) as MQTTDevice
            case DeviceProtocol.CoAP:
                return coAPRepository.findBySecurityId(securityId) as CoAPDevice
            case DeviceProtocol.HTTP:
                return httpRepository.findBySecurityId(securityId) as HTTPDevice
            case DeviceProtocol.TCP:
                return tcpDeviceRepository.findBySecurityId(securityId) as TCPDevice
            case DeviceProtocol.UDP:
                return udpDeviceRepository.findBySecurityId(securityId) as UDPDevice
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

            case DeviceProtocol.CoAP:
                return coAPRepository.findAllBySceneAndDeviceProtocol(scene, deviceProtocol, pageable)

            case DeviceProtocol.HTTP:
                return httpRepository.findAllBySceneAndDeviceProtocol(scene, deviceProtocol, pageable)

            case DeviceProtocol.TCP:
                return tcpDeviceRepository.findAllBySceneAndDeviceProtocol(scene, deviceProtocol, pageable)

            case DeviceProtocol.UDP:
                return udpDeviceRepository.findAllBySceneAndDeviceProtocol(scene, deviceProtocol, pageable)

            default: return null

        }
    }


    /**
     * 根据Sid查询
     * @param sceneSecurityId
     * @param deviceProtocol
     * @param pageable
     * @return
     */
    Page<AbstractDevice> listDeviceBySceneSecurityId(String sceneSecurityId, DeviceProtocol deviceProtocol, Pageable pageable) {
        switch (deviceProtocol) {
            case DeviceProtocol.MQTT:
                return mqttRepository.findAllBySceneSecurityIdAndDeviceProtocol(sceneSecurityId, deviceProtocol, pageable)

            case DeviceProtocol.CoAP:
                return coAPRepository.findAllBySceneSecurityIdAndDeviceProtocol(sceneSecurityId, deviceProtocol, pageable)

            case DeviceProtocol.HTTP:
                return httpRepository.findAllBySceneSecurityIdAndDeviceProtocol(sceneSecurityId, deviceProtocol, pageable)

            case DeviceProtocol.TCP:
                return tcpDeviceRepository.findAllBySceneSecurityIdAndDeviceProtocol(sceneSecurityId, deviceProtocol, pageable)

            case DeviceProtocol.UDP:
                return udpDeviceRepository.findAllBySceneSecurityIdAndDeviceProtocol(sceneSecurityId, deviceProtocol, pageable)

            default: return null

        }
    }


}

