package com.easylinker.v3.modules.devicedata.service

import com.alibaba.fastjson.JSONObject
import com.easylinker.framework.common.model.DeviceData
import com.easylinker.v3.common.model.DeviceType
import org.apache.commons.lang.StringUtils
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
class DeviceDataService {
    @Autowired
    MongoTemplate mongoTemplate

    /**
     * 查询数据
     * @param deviceSecurityId
     * @param deviceType
     * @param pageable
     * @return
     */
    Page<DeviceData> list(String deviceSecurityId, String startDate, String endDate, DeviceType deviceType, Pageable pageable) {

        Query query = new Query()
        //开始结束时间 ，都传

        if (StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
            query.addCriteria(Criteria.where("deviceSecurityId").is(deviceSecurityId)
                    .and("deviceType").is(deviceType)
                    .andOperator(Criteria.where("createTime").lt((endDate)
                    ), Criteria.where("createTime").gte((startDate))))
        }
        //只传了开始时间

        if (StringUtils.isNotEmpty(startDate) && !StringUtils.isNotEmpty(endDate)) {
            query.addCriteria(Criteria.where("deviceSecurityId").is(deviceSecurityId)
                    .and("deviceType").is(deviceType)
                    .andOperator(Criteria.where("createTime").gte((startDate))))
        }
        //只传了结束时间

        if (!StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
            query.addCriteria(Criteria.where("deviceSecurityId").is(deviceSecurityId)
                    .and("deviceType").is(deviceType)
                    .andOperator(Criteria.where("createTime").lt((endDate))))
        }
        //开始结束时间都没传
        if (!StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
            query.addCriteria(Criteria.where("deviceSecurityId").is(deviceSecurityId)
                    .and("deviceType").is(deviceType))
        }
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")))
        query.with(pageable)
        List<DeviceData> deviceDataList = mongoTemplate.find(query, DeviceData.class, "DEVICE_DATA")
        /**
         * [
         *{*       "dateTime":"Jan",
         *         "field":"temp",
         *         "value":7
         *},
         *{*       "dateTime":"2019",
         *         "field":"hum",
         *         "value":3.9
         *}* ]
         */
        List<JSONObject> resultData = new ArrayList<>()
        for (DeviceData data : deviceDataList) {
            JSONObject dataJson = data.getData()
            for (String k : dataJson.keySet()) {
                JSONObject resultJson = new JSONObject()
                resultJson.put("dateTime", data.getCreateTime())
                resultJson.put("field", k)
                resultJson.put("value", dataJson.get(k))
                resultData.add(resultJson)
            }
        }
        long total = mongoTemplate.count(query, DeviceData.class)
        return new PageImpl(resultData, pageable, total)


    }

/**
 *
 * @param deviceSecurityId
 * @param deviceType
 * @param pageable
 * @return
 */

    void save(DeviceData deviceData) {

        mongoTemplate.save(deviceData, "DEVICE_DATA")
    }
}

