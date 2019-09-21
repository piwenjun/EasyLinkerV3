package com.easylinker.v3.modules.device.dao

import com.easylinker.v3.common.model.DeviceProtocol
import com.easylinker.v3.common.model.DeviceStatus
import com.easylinker.v3.common.model.DeviceType
import com.easylinker.v3.modules.device.model.*
import com.easylinker.v3.modules.scene.model.Scene
import com.easylinker.v3.modules.user.model.AppUser
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

/**
 * 这种写法看起来是不是不舒服？蛋疼就对了，这是Groovy的风格，没有Java那么强制性，一个文件可写很多个类
 * @author wwhai* @date 2019/6/30 23:07
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */

/**
 * 公共基础DAO
 */
interface CommonRepository<T> {

    /**
     * 考虑到以后要脱离外键，后期 根据SceneSID 查询
     * @param appUser
     * @param sceneSecurityId
     * @return
     */

    Page<T> findAllBySceneSecurityIdAndDeviceProtocol(String sceneSecurityId, DeviceProtocol deviceProtocol, Pageable pageable)

    /**
     * 获取某个产品下挂的所有设备
     * @param productSecurityId
     * @param deviceProtocol
     * @param pageable
     * @return
     */
    Page<T> findAllByProductSecurityIdAndDeviceProtocol(String productSecurityId, DeviceProtocol deviceProtocol, Pageable pageable)

    /**
     * 统计各种设备类型
     * @param appUser
     * @param deviceType
     * @return
     */
    long countByAppUserAndDeviceType(AppUser appUser, DeviceType deviceType)

    long countByAppUser(AppUser appUser)

}
/**
 * HTTP
 */
interface HTTPRepository extends JpaRepository<HTTPDevice, Long>, CommonRepository<HTTPDevice> {


    Page<HTTPDevice> findAllByAppUser(Pageable page, AppUser appUser)

    Page<HTTPDevice> findAllBySceneAndDeviceProtocol(Scene scene, DeviceProtocol deviceProtocol, Pageable page)

    HTTPDevice findBySecurityId(String securityId)

    Page<HTTPDevice> findAllByAppUserAndDeviceType(AppUser appUser, DeviceType deviceType, Pageable pageable)


}
/**
 * CoAP
 */
interface CoAPRepository extends JpaRepository<CoAPDevice, Long>, CommonRepository<CoAPDevice> {

    CoAPDevice findBySecurityId(String securityId)

    Page<CoAPDevice> findAllBySceneAndDeviceProtocol(Scene scene, DeviceProtocol deviceProtocol, Pageable page)

    Page<CoAPDevice> findAllByAppUser(Pageable page, AppUser appUser)

    Page<CoAPDevice> findAllByAppUserAndDeviceType(AppUser appUser, DeviceType deviceType, Pageable pageable)


}
/**
 * Mqtt
 */
interface MQTTRepository extends JpaRepository<MQTTDevice, Long>, JpaSpecificationExecutor<MQTTDevice>, CommonRepository<MQTTDevice> {

    MQTTDevice findBySecurityId(String securityId)

    Page<MQTTDevice> findAllBySceneAndDeviceProtocol(Scene scene, DeviceProtocol deviceProtocol, Pageable page)

    long countByOnline(Boolean online)

    Page<MQTTDevice> findAllByAppUser(Pageable page, AppUser appUser)
    //deviceType

    Page<MQTTDevice> findAllByAppUserAndDeviceType(AppUser appUser, DeviceType deviceType, Pageable page)

}
/**
 * Host
 */
interface TerminalHostDeviceRepository extends JpaRepository<TerminalHostDevice, Long>, CommonRepository<TerminalHostDevice> {

    TerminalHostDevice findBySecurityId(String securityId)

    Page<TerminalHostDevice> findAllBySceneAndDeviceProtocol(Scene scene, DeviceProtocol deviceProtocol, Pageable page)

    Page<TerminalHostDevice> findAllByAppUser(Pageable page, AppUser appUser)

    Page<TerminalHostDevice> findAllByAppUserAndDeviceType(AppUser appUser, DeviceType deviceType, Pageable page)

    Page<TerminalHostDevice> findAllByAppUserAndDeviceProtocolAndDeviceStatusOrDeviceTypeLikeOrNameLikeOrInfoLikeOrSnLike(AppUser appUser, DeviceProtocol deviceProtocol, DeviceStatus status, DeviceType deviceType, String name, String info, String sn, Pageable pageable)


    Page<TerminalHostDevice> findAllByAppUserAndDeviceProtocolAndSceneAndDeviceStatusOrDeviceTypeLikeOrNameLikeOrInfoLikeOrSnLike(AppUser appUser, DeviceProtocol deviceProtocol, Scene scene, DeviceStatus status, DeviceType deviceType, String name, String info, String sn, Pageable pageable)

}

/**
 * TCP
 */
interface TCPDeviceRepository extends JpaRepository<TCPDevice, Long>, CommonRepository<TCPDevice> {

    TCPDevice findBySecurityId(String securityId)

    Page<TCPDevice> findAllBySceneAndDeviceProtocol(Scene scene, DeviceProtocol deviceProtocol, Pageable page)

    Page<TCPDevice> findAllByAppUser(Pageable page, AppUser appUser)

    Page<TCPDevice> findAllByAppUserAndDeviceType(AppUser appUser, DeviceType deviceType, Pageable page)


}

/**
 * Udp
 */
interface UDPDeviceRepository extends JpaRepository<UDPDevice, Long>, CommonRepository<UDPDevice> {

    UDPDevice findBySecurityId(String securityId)

    Page<UDPDevice> findAllBySceneAndDeviceProtocol(Scene scene, DeviceProtocol deviceProtocol, Pageable page)

    Page<UDPDevice> findAllByAppUser(Pageable page, AppUser appUser)

    Page<UDPDevice> findAllByAppUserAndDeviceType(AppUser appUser, DeviceType deviceType, Pageable page)

}

