package com.easylinker.v3.modules.syslog.service

import com.easylinker.framework.common.service.AbstractService
import com.easylinker.v3.modules.syslog.model.SystemLog
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
class SystemLogService extends AbstractService<SystemLog> {
    @Autowired
    MongoTemplate mongoTemplate

    @Override
    void save(SystemLog systemLog) {
        mongoTemplate.save(systemLog, "SYSTEMLOG")
    }

    @Override
    Page<SystemLog> page(Pageable pageable) {
    }

    @Override
    SystemLog getById(long id) {
    }

    @Override
    void delete(SystemLog systemLog) {

    }

    @Override
    void deleteById(long id) {
    }


    /**
     * 查找用户的日志记录
     * @param userSecurityId
     * @param pageable
     * @return
     */
    Page<SystemLog> list(String userSecurityId, Pageable pageable) {

        Query query = new Query()
        Criteria criteria = Criteria.where("userSecurityId").is(userSecurityId)
        query.addCriteria(criteria)
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")))
        query.with(pageable)
        List<SystemLog> deviceDataList = mongoTemplate.find(query, SystemLog.class, "SYSTEMLOG")
        long total = mongoTemplate.count(query, SystemLog.class)
        return new PageImpl(deviceDataList, pageable, total)

    }
}
