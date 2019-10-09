package com.easylinker.v3.modules.devicedata.service

import com.easylinker.framework.common.model.DeviceData
import com.easylinker.framework.common.model.DeviceType
import com.easylinker.framework.utils.MongoDBDateUtils
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

import java.text.SimpleDateFormat

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
    Map<String, Object> list(String deviceSecurityId, String startDate, String endDate, DeviceType deviceType, Pageable pageable) {

        Query query = new Query()
        //开始结束时间 ，都传

        if (StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
            query.addCriteria(Criteria.where("deviceSecurityId").is(deviceSecurityId)
                    .and("deviceType").is(deviceType)
                    .and("createTime").lt(MongoDBDateUtils.formatD(endDate)).gte((MongoDBDateUtils.formatD(startDate))))
        }
        //只传了开始时间

        if (StringUtils.isNotEmpty(startDate) && !StringUtils.isNotEmpty(endDate)) {
            query.addCriteria(Criteria.where("deviceSecurityId").is(deviceSecurityId)
                    .and("deviceType").is(deviceType)
                    .and("createTime").gte((MongoDBDateUtils.formatD(startDate))))
        }
        //只传了结束时间

        if (!StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
            query.addCriteria(Criteria.where("deviceSecurityId").is(deviceSecurityId)
                    .and("deviceType").is(deviceType)
                    .and("createTime").lt((MongoDBDateUtils.formatD(endDate))))
        }
        //开始结束时间都没传
        if (!StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
            query.addCriteria(Criteria.where("deviceSecurityId").is(deviceSecurityId)
                    .and("deviceType").is(deviceType))
        }
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")))
        query.with(pageable)
        List<DeviceData> deviceDataList = mongoTemplate.find(query, DeviceData.class, "DEVICE_DATA")
        //构建返回结果Map
        Map<String, Object> resultMap = new HashMap<>()
        //先提取时间 构成X轴
        List<String> xSideList = new ArrayList<>()
        //提取字段
        List<Map<String, Object>> dataMapList = new ArrayList<>()

        for (DeviceData deviceData : deviceDataList) {
            xSideList.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(deviceData.createTime))
            dataMapList.add(deviceData.data)
        }

        resultMap.put("xSide", xSideList)
        resultMap.put("value", genData(dataMapList))

        return resultMap


    }

    /**
     * 原始数据
     * @param deviceSecurityId
     * @param startDate
     * @param endDate
     * @param deviceType
     * @param pageable
     * @return
     */

    Page<DeviceData> listOriginData(String deviceSecurityId, String startDate, String endDate, DeviceType deviceType, Pageable pageable) {

        Query query = new Query()
        //开始结束时间 ，都传

        if (StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
            query.addCriteria(Criteria.where("deviceSecurityId").is(deviceSecurityId)
                    .and("deviceType").is(deviceType)
                    .and("createTime").lt(MongoDBDateUtils.formatD(endDate)).gte((MongoDBDateUtils.formatD(startDate))))
        }
        //只传了开始时间

        if (StringUtils.isNotEmpty(startDate) && !StringUtils.isNotEmpty(endDate)) {
            query.addCriteria(Criteria.where("deviceSecurityId").is(deviceSecurityId)
                    .and("deviceType").is(deviceType)
                    .and("createTime").gte((MongoDBDateUtils.formatD(startDate))))
        }
        //只传了结束时间

        if (!StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
            query.addCriteria(Criteria.where("deviceSecurityId").is(deviceSecurityId)
                    .and("deviceType").is(deviceType)
                    .and("createTime").lt((MongoDBDateUtils.formatD(endDate))))
        }
        //开始结束时间都没传
        if (!StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
            query.addCriteria(Criteria.where("deviceSecurityId").is(deviceSecurityId)
                    .and("deviceType").is(deviceType))
        }
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")))
        query.with(pageable)
        List<DeviceData> deviceDataList = mongoTemplate.find(query, DeviceData.class, "DEVICE_DATA")
        long total = mongoTemplate.count(query, DeviceData.class)

        return new PageImpl<DeviceData>(deviceDataList, pageable, total)


    }


    /**
     * 加工数据
     * @param list
     * @return
     */
    static Map<String, List> genData(List<Map<String, Object>> list) {
        Map<String, List> valueListMap = new HashMap<>()
        if (list.size() > 0) {
            for (String K : list.get(0).keySet()) {
                valueListMap.put(K, new ArrayList<>())
            }
            for (int i = 0; i < list.size(); i++) {
                for (String K : list.get(i).keySet()) {
                    Object value = list.get(i).get(K)
                    valueListMap.get(K).add(value)
//                    println("字段:" + K + " Value:" + value)
                }
            }

        }
        return valueListMap
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

