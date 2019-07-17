package com.easylinker.v3.modules.device.dao

import com.easylinker.framework.common.model.DeviceProtocol
import com.easylinker.framework.common.model.DeviceStatus
import com.easylinker.framework.common.model.DeviceType
import com.easylinker.framework.modules.user.model.AppUser
import com.easylinker.v3.modules.device.model.*
import com.easylinker.v3.modules.scene.model.Scene
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
 * HTTP
 */
interface HTTPRepository extends JpaRepository<HTTPDevice, Long> {

    long countByAppUser(AppUser appUser)

    Page<HTTPDevice> findAllByAppUser(Pageable page, AppUser appUser)

    Page<HTTPDevice> findAllBySceneAndDeviceProtocol(Scene scene, DeviceProtocol deviceProtocol, Pageable page)

    HTTPDevice findBySecurityId(String securityId)

    Page<HTTPDevice> findAllByAppUserAndDeviceType(AppUser appUser, DeviceType deviceType, Pageable pageable)

    //
    Page<HTTPDevice> findAllByAppUserAndDeviceProtocolAndDeviceStatusOrDeviceTypeLikeOrNameLikeOrInfoLikeOrSnLike(AppUser appUser, DeviceProtocol deviceProtocol, DeviceStatus status, DeviceType deviceType, String name, String info, String sn, Pageable pageable)

    //
    Page<HTTPDevice> findAllByAppUserAndDeviceProtocolAndSceneAndDeviceStatusOrDeviceTypeLikeOrNameLikeOrInfoLikeOrSnLike(AppUser appUser, DeviceProtocol deviceProtocol,Scene scene,DeviceStatus status,  DeviceType deviceType, String name, String info, String sn, Pageable pageable)

}
/**
 * CoAP
 */
interface CoAPRepository extends JpaRepository<CoAPDevice, Long> {
    long countByAppUser(AppUser appUser)

    CoAPDevice findBySecurityId(String securityId)

    Page<CoAPDevice> findAllBySceneAndDeviceProtocol(Scene scene, DeviceProtocol deviceProtocol, Pageable page)

    Page<CoAPDevice> findAllByAppUser(Pageable page, AppUser appUser)

    Page<CoAPDevice> findAllByAppUserAndDeviceType(AppUser appUser, DeviceType deviceType, Pageable pageable)

    Page<CoAPDevice> findAllByAppUserAndDeviceProtocolAndDeviceStatusOrDeviceTypeLikeOrNameLikeOrInfoLikeOrSnLike(AppUser appUser, DeviceProtocol deviceProtocol, DeviceStatus status, DeviceType deviceType, String name, String info, String sn, Pageable pageable)


    Page<CoAPDevice> findAllByAppUserAndDeviceProtocolAndSceneAndDeviceStatusOrDeviceTypeLikeOrNameLikeOrInfoLikeOrSnLike(AppUser appUser, DeviceProtocol deviceProtocol,Scene scene,DeviceStatus status,  DeviceType deviceType, String name, String info, String sn, Pageable pageable)

}
/**
 * Mqtt
 */
interface MQTTRepository extends JpaRepository<MQTTDevice, Long>, JpaSpecificationExecutor<MQTTDevice> {
    long countByAppUser(AppUser appUser)

    MQTTDevice findBySecurityId(String securityId)

    Page<MQTTDevice> findAllBySceneAndDeviceProtocol(Scene scene, DeviceProtocol deviceProtocol, Pageable page)

    long countByOnline(Boolean online)

    Page<MQTTDevice> findAllByAppUser(Pageable page, AppUser appUser)
    //deviceType

    Page<MQTTDevice> findAllByAppUserAndDeviceType(AppUser appUser, DeviceType deviceType, Pageable page)

    Page<MQTTDevice> findAllByAppUserAndDeviceProtocolAndDeviceStatusOrDeviceTypeLikeOrNameLikeOrInfoLikeOrSnLike(AppUser appUser, DeviceProtocol deviceProtocol, DeviceStatus status, DeviceType deviceType, String name, String info, String sn, Pageable pageable)


    Page<MQTTDevice>  findAllByAppUserAndDeviceProtocolAndSceneAndDeviceStatusOrDeviceTypeLikeOrNameLikeOrInfoLikeOrSnLike(AppUser appUser, DeviceProtocol deviceProtocol,Scene scene,DeviceStatus status,  DeviceType deviceType, String name, String info, String sn, Pageable pageable)

}
/**
 * Host
 */
interface TerminalHostDeviceRepository extends JpaRepository<TerminalHostDevice, Long> {
    long countByAppUser(AppUser appUser)

    TerminalHostDevice findBySecurityId(String securityId)

    Page<TerminalHostDevice> findAllBySceneAndDeviceProtocol(Scene scene, DeviceProtocol deviceProtocol, Pageable page)

    Page<TerminalHostDevice> findAllByAppUser(Pageable page, AppUser appUser)

    Page<TerminalHostDevice> findAllByAppUserAndDeviceType(AppUser appUser, DeviceType deviceType, Pageable page)

    Page<TerminalHostDevice>findAllByAppUserAndDeviceProtocolAndDeviceStatusOrDeviceTypeLikeOrNameLikeOrInfoLikeOrSnLike(AppUser appUser, DeviceProtocol deviceProtocol, DeviceStatus status, DeviceType deviceType, String name, String info, String sn, Pageable pageable)


    Page<TerminalHostDevice>  findAllByAppUserAndDeviceProtocolAndSceneAndDeviceStatusOrDeviceTypeLikeOrNameLikeOrInfoLikeOrSnLike(AppUser appUser, DeviceProtocol deviceProtocol,Scene scene,DeviceStatus status,  DeviceType deviceType, String name, String info, String sn, Pageable pageable)

}

/**
 * TCP
 */
interface TCPDeviceRepository extends JpaRepository<TCPDevice, Long> {
    long countByAppUser(AppUser appUser)

    TCPDevice findBySecurityId(String securityId)

    Page<TCPDevice> findAllBySceneAndDeviceProtocol(Scene scene, DeviceProtocol deviceProtocol, Pageable page)

    Page<TCPDevice> findAllByAppUser(Pageable page, AppUser appUser)

    Page<TCPDevice> findAllByAppUserAndDeviceType(AppUser appUser, DeviceType deviceType, Pageable page)

    Page<TCPDevice>findAllByAppUserAndDeviceProtocolAndDeviceStatusOrDeviceTypeLikeOrNameLikeOrInfoLikeOrSnLike(AppUser appUser, DeviceProtocol deviceProtocol, DeviceStatus status, DeviceType deviceType, String name, String info, String sn, Pageable pageable)


    Page<TCPDevice>  findAllByAppUserAndDeviceProtocolAndSceneAndDeviceStatusOrDeviceTypeLikeOrNameLikeOrInfoLikeOrSnLike(AppUser appUser, DeviceProtocol deviceProtocol,Scene scene,DeviceStatus status,  DeviceType deviceType, String name, String info, String sn, Pageable pageable)

}

/**
 * Udp
 */
interface UDPDeviceRepository extends JpaRepository<UDPDevice, Long> {
    long countByAppUser(AppUser appUser)

    UDPDevice findBySecurityId(String securityId)

    Page<UDPDevice> findAllBySceneAndDeviceProtocol(Scene scene, DeviceProtocol deviceProtocol, Pageable page)

    Page<UDPDevice> findAllByAppUser(Pageable page, AppUser appUser)

    Page<UDPDevice> findAllByAppUserAndDeviceType(AppUser appUser, DeviceType deviceType, Pageable page)

    Page<UDPDevice> findAllByAppUserAndDeviceProtocolAndDeviceStatusOrDeviceTypeLikeOrNameLikeOrInfoLikeOrSnLike(AppUser appUser, DeviceProtocol deviceProtocol, DeviceStatus status, DeviceType deviceType, String name, String info, String sn, Pageable pageable)

    Page<UDPDevice>  findAllByAppUserAndDeviceProtocolAndSceneAndDeviceStatusOrDeviceTypeLikeOrNameLikeOrInfoLikeOrSnLike(AppUser appUser, DeviceProtocol deviceProtocol,Scene scene,DeviceStatus status,  DeviceType deviceType, String name, String info, String sn, Pageable pageable)

}

