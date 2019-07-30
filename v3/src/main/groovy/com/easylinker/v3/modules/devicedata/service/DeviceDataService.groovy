package com.easylinker.v3.modules.devicedata.service


import com.easylinker.v3.modules.devicedata.model.DeviceData
import com.easylinker.v3.modules.model.DeviceType
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
    Page<DeviceData> list(String deviceSecurityId, DeviceType deviceType, Pageable pageable) {

        Query query = new Query()
        Criteria criteria = Criteria.where("deviceSecurityId").is(deviceSecurityId).and("deviceType").is(deviceType)
        query.addCriteria(criteria)
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")))
        query.with(pageable)
        List<DeviceData> deviceDataList = mongoTemplate.find(query, DeviceData.class, "DEVICE_DATA")
        long total = mongoTemplate.count(query, DeviceData.class)
        return new PageImpl(deviceDataList, pageable, total)


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

