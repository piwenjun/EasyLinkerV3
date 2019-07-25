package com.easylinker.v3.modules.deviceecho.service

import com.easylinker.framework.common.service.AbstractService
import com.easylinker.v3.modules.deviceecho.model.DeviceOperateEcho
import com.easylinker.v3.modules.deviceecho.model.DeviceOperateLog
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

/**
 * @author wwhai* @date 2019/7/23 23:06
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */

/**
 * 设备操作日志
 */
@Service
class DeviceOperateLogService extends AbstractService<DeviceOperateLog> {
    @Autowired
    MongoTemplate mongoTemplate

    @Override
    void save(DeviceOperateLog deviceOperateLog) {

        mongoTemplate.save(deviceOperateLog, "DEVICE_OPERATE_LOG")
    }

    @Override
    Page<DeviceOperateLog> page(Pageable pageable) {
        return null
    }

    @Override
    DeviceOperateLog getById(long id) {
        return null
    }

    @Override
    void delete(DeviceOperateLog deviceOperateLog) {

    }

    @Override
    void deleteById(long id) {

    }

    Page<DeviceOperateLog> list(String userSecurityId, String deviceSecurityId, Pageable pageable) {
        Query query = new Query()
        Criteria criteria = Criteria.where("userSecurityId").is(userSecurityId).and("deviceSecurityId").is(deviceSecurityId)
        query.addCriteria(criteria)
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")))
        query.with(pageable)
        List<DeviceOperateLog> deviceDataList = mongoTemplate.find(query, DeviceOperateLog.class, "DEVICE_OPERATE_ECHO")
        long total = mongoTemplate.count(query, DeviceOperateLog.class)
        return new PageImpl(deviceDataList, pageable, total)


    }
}
/**
 * 设备操作反馈，和操作记录是 一对一的关系
 */
@Service
class DeviceOperateEchoService extends AbstractService<DeviceOperateEcho> {
    @Autowired
    MongoTemplate mongoTemplate

    @Override
    void save(DeviceOperateEcho deviceOperateEcho) {
        mongoTemplate.save(deviceOperateEcho, "DEVICE_OPERATE_ECHO")
    }

    @Override
    Page<DeviceOperateEcho> page(Pageable pageable) {
    }

    @Override
    DeviceOperateEcho getById(long id) {
    }

    @Override
    void delete(DeviceOperateEcho deviceOperateEcho) {

    }

    @Override
    void deleteById(long id) {
    }

    Page<DeviceOperateEcho> list(String deviceOperateSecurityId, Pageable pageable) {
        Query query = new Query()
        Criteria criteria = Criteria.where("deviceOperateSecurityId").is(deviceOperateSecurityId)
        query.addCriteria(criteria)
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")))
        query.with(pageable)
        List<DeviceOperateEcho> deviceDataList = mongoTemplate.find(query, DeviceOperateEcho.class, "DEVICE_OPERATE_ECHO")
        long total = mongoTemplate.count(query, DeviceOperateEcho.class)
        return new PageImpl(deviceDataList, pageable, total)


    }
}
