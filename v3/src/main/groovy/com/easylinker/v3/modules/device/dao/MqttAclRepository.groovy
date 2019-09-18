package com.easylinker.v3.modules.device.dao

import com.easylinker.v3.modules.device.model.MQTTDevice
import com.easylinker.v3.modules.device.model.TerminalHostDevice
import com.easylinker.v3.modules.device.model.TerminalTopicAcl
import com.easylinker.v3.modules.device.model.TopicAcl
import org.springframework.data.jpa.repository.JpaRepository

interface TopicAclRepository extends JpaRepository<TopicAcl, Long> {
    List<TopicAcl> findAllByMqttDevice(MQTTDevice QTTDevice)
}
/*
终端设备
 */

interface TerminalHostTopicAclRepository extends JpaRepository<TerminalTopicAcl, Long> {
    List<TerminalTopicAcl> findAllByTerminalHostDevice(TerminalHostDevice terminalHostDevice)
}
