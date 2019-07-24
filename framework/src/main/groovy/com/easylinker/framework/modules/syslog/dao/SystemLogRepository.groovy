package com.easylinker.framework.modules.syslog.dao

import com.easylinker.framework.modules.syslog.model.SystemLog
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface SystemLogRepository extends MongoRepository<SystemLog, Long> {
    Page<SystemLog> findByUserSecurityId(String userSecurityId, Pageable pageable)
}
