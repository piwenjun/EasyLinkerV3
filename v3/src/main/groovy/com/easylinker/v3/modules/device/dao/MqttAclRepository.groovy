package com.easylinker.v3.modules.device.dao

import com.easylinker.v3.modules.device.model.TopicAcl
import org.springframework.data.jpa.repository.JpaRepository

interface TopicAclRepository extends JpaRepository<TopicAcl, Long> {
}
