package com.easylinker.v3.modules.deviceecho.service

import com.alibaba.fastjson.JSONObject
import com.easylinker.framework.common.service.AbstractService
import com.easylinker.v3.modules.deviceecho.model.DeviceOperateEcho
import com.easylinker.v3.modules.deviceecho.model.DeviceOperateLog
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
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

    /**
     * 操作日志  反馈
     * @param userSecurityId
     * @param deviceSecurityId
     * @param pageable
     * @return
     */
    Page<JSONObject> list(String deviceSecurityId, Pageable pageable) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("deviceSecurityId").is(deviceSecurityId)),
                Aggregation.project("securityId", "operate", "event", "data", "createTime"),
                Aggregation.sort(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime"))),
                Aggregation.skip((long) pageable.pageNumber * pageable.pageSize),
                Aggregation.limit(pageable.pageSize),

        )
        //改成聚合查询
        List<DeviceOperateLog> deviceDataList = mongoTemplate.aggregate(aggregation, "DEVICE_OPERATE_LOG", DeviceOperateLog.class).mappedResults

        //最终返回的结果
        List<JSONObject> dataList = new ArrayList<>()
        //遍历操作记录表
        for (DeviceOperateLog log : deviceDataList) {
            //构造一个JSON用来返回
            JSONObject jsonData = new JSONObject()
//            Query query = new Query()
//            query.addCriteria(Criteria.where("deviceOperateLogSecurityId").is(log.securityId))
//            query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime"))).limit(1)
            //
            Aggregation aggregation1 = Aggregation.newAggregation(
                    Aggregation.match(Criteria.where("deviceOperateLogSecurityId").is(log.securityId)),
                    Aggregation.project("data", "createTime"),
                    Aggregation.sort(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime"))),
                    Aggregation.limit(1)

            )
            //查询该操作记录的返回记录
            DeviceOperateEcho deviceOperateEcho = mongoTemplate.aggregate(aggregation1, "DEVICE_OPERATE_ECHO", DeviceOperateEcho.class).mappedResults[0]
            jsonData.put("operate", log)
            if (deviceOperateEcho) {
                jsonData.put("echo", deviceOperateEcho)
            } else {
                jsonData.put("echo", [])
            }
            dataList.add(jsonData)
        }
        //构造分页
        Query query = new Query()
        query.addCriteria(Criteria.where("deviceSecurityId").is(deviceSecurityId))
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")))
        query.with(pageable)
        long total = mongoTemplate.count(query, DeviceOperateLog.class)
        return new PageImpl<JSONObject>(dataList, pageable, total)


    }
}
