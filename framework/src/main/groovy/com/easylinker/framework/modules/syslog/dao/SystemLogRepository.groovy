package com.easylinker.framework.modules.syslog.dao

import com.easylinker.framework.modules.syslog.model.SystemLog
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface SystemLogRepository extends JpaRepository<SystemLog, Long> {
    Page<SystemLog>findByUserSecurityId(String userSecurityId, Pageable pageable)
}
