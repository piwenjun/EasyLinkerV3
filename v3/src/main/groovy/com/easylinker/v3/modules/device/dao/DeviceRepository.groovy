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
 * 公共基础DAO
 */
interface CommonRepository<T> {

    /**
     * 模糊查询
     * @param appUser
     * @param deviceProtocol
     * @param scene
     * @param status
     * @param deviceType
     * @param name
     * @param info
     * @param sn
     * @param pageable
     * @return
     */
    Page<T> findAllByAppUserAndDeviceProtocolAndSceneAndDeviceStatusAndDeviceTypeLikeAndNameLikeAndInfoLikeAndSnLike(AppUser appUser,
                                                                                                                     DeviceProtocol deviceProtocol,
                                                                                                                     Scene scene, DeviceStatus status,
                                                                                                                     DeviceType deviceType,
                                                                                                                     String name,
                                                                                                                     String info,
                                                                                                                     String sn,
                                                                                                                     Pageable pageable)

    /**
     * 带 DeviceType查询
     * @param appUser
     * @param deviceProtocol
     * @param status
     * @param deviceType
     * @param name
     * @param info
     * @param sn
     * @param pageable
     * @return
     */
    Page<T> findAllByAppUserAndDeviceProtocolAndDeviceStatusAndDeviceTypeLikeAndNameLikeAndInfoLikeAndSnLike(AppUser appUser,
                                                                                                             DeviceProtocol deviceProtocol,
                                                                                                             DeviceStatus status,
                                                                                                             DeviceType deviceType,
                                                                                                             String name,
                                                                                                             String info,
                                                                                                             String sn,

                                                                                                             Pageable pageable)

    /**
     * 不带DeviceType查询
     * @param appUser
     * @param deviceProtocol
     * @param status
     * @param name
     * @param info
     * @param sn
     * @param pageable
     * @return
     */
    Page<T> findAllByAppUserAndDeviceProtocolAndDeviceStatusAndNameLikeAndInfoLikeAndSnLike(AppUser appUser,
                                                                                            DeviceProtocol deviceProtocol,
                                                                                            DeviceStatus status,
                                                                                            String name,
                                                                                            String info,
                                                                                            String sn,
                                                                                            Pageable pageable)


}
/**
 * HTTP
 */
interface HTTPRepository extends JpaRepository<HTTPDevice, Long>, CommonRepository<HTTPDevice> {

    long countByAppUser(AppUser appUser)

    Page<HTTPDevice> findAllByAppUser(Pageable page, AppUser appUser)

    Page<HTTPDevice> findAllBySceneAndDeviceProtocol(Scene scene, DeviceProtocol deviceProtocol, Pageable page)

    HTTPDevice findBySecurityId(String securityId)

    Page<HTTPDevice> findAllByAppUserAndDeviceType(AppUser appUser, DeviceType deviceType, Pageable pageable)


}
/**
 * CoAP
 */
interface CoAPRepository extends JpaRepository<CoAPDevice, Long>, CommonRepository<CoAPDevice> {
    long countByAppUser(AppUser appUser)

    CoAPDevice findBySecurityId(String securityId)

    Page<CoAPDevice> findAllBySceneAndDeviceProtocol(Scene scene, DeviceProtocol deviceProtocol, Pageable page)

    Page<CoAPDevice> findAllByAppUser(Pageable page, AppUser appUser)

    Page<CoAPDevice> findAllByAppUserAndDeviceType(AppUser appUser, DeviceType deviceType, Pageable pageable)


}
/**
 * Mqtt
 */
interface MQTTRepository extends JpaRepository<MQTTDevice, Long>, JpaSpecificationExecutor<MQTTDevice>, CommonRepository<MQTTDevice> {
    long countByAppUser(AppUser appUser)

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
    long countByAppUser(AppUser appUser)

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
    long countByAppUser(AppUser appUser)

    TCPDevice findBySecurityId(String securityId)

    Page<TCPDevice> findAllBySceneAndDeviceProtocol(Scene scene, DeviceProtocol deviceProtocol, Pageable page)

    Page<TCPDevice> findAllByAppUser(Pageable page, AppUser appUser)

    Page<TCPDevice> findAllByAppUserAndDeviceType(AppUser appUser, DeviceType deviceType, Pageable page)


}

/**
 * Udp
 */
interface UDPDeviceRepository extends JpaRepository<UDPDevice, Long>, CommonRepository<UDPDevice> {
    long countByAppUser(AppUser appUser)

    UDPDevice findBySecurityId(String securityId)

    Page<UDPDevice> findAllBySceneAndDeviceProtocol(Scene scene, DeviceProtocol deviceProtocol, Pageable page)

    Page<UDPDevice> findAllByAppUser(Pageable page, AppUser appUser)

    Page<UDPDevice> findAllByAppUserAndDeviceType(AppUser appUser, DeviceType deviceType, Pageable page)

}

