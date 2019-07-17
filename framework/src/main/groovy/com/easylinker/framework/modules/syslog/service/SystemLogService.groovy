package com.easylinker.framework.modules.syslog.service

import com.easylinker.framework.common.service.AbstractService
import com.easylinker.framework.modules.syslog.dao.SystemLogRepository
import com.easylinker.framework.modules.syslog.model.SystemLog
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class SystemLogService extends AbstractService<SystemLog> {
    @Autowired
    SystemLogRepository systemLogRepository

    @Override
    void save(SystemLog systemLog) {
        systemLogRepository.save(systemLog)
    }

    @Override
    Page<SystemLog> page(Pageable pageable) {
        return systemLogRepository.findAll(pageable)
    }

    @Override
    SystemLog getById(long id) {
        return systemLogRepository.findById(id).get()
    }

    @Override
    void delete(SystemLog systemLog) {

        systemLogRepository.delete(systemLog)
    }

    @Override
    void deleteById(long id) {
        systemLogRepository.deleteById(id)
    }


    /**
     * 查找用户的日志记录
     * @param userSecurityId
     * @param pageable
     * @return
     */
    Page<SystemLog> listByUser(String userSecurityId, Pageable pageable) {
        return systemLogRepository.findByUserSecurityId(userSecurityId, pageable)
    }
}
