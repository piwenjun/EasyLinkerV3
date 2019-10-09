package com.easylinker.v3.modules.devicedata.controller

import com.easylinker.framework.common.exception.XException
import com.easylinker.framework.common.model.DeviceType
import com.easylinker.framework.common.web.R
import com.easylinker.framework.utils.MongoDBDateUtils
import com.easylinker.v3.common.controller.AbstractController
import com.easylinker.v3.modules.devicedata.service.DeviceDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest
import java.text.SimpleDateFormat

/**
 * 设备数据入口:
 * 这里需要用微服务去实现
 */
@RestController
@RequestMapping("/deviceData")
class DeviceDataController extends AbstractController {
    @Autowired
    DeviceDataService deviceDataService

    DeviceDataController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }

    /**
     * 查询设备数据,加工成便于渲染的形式
     * @param page
     * @param size
     * @param dataQueryForm
     * @return
     */
    @GetMapping("/list")
    R list(@RequestParam int page,
           @RequestParam int size,
           @RequestParam(required = false) String startDate,
           @RequestParam(required = false) String endDate,
           @RequestParam String deviceSecurityId,
           @RequestParam DeviceType deviceType) {
        if (deviceType != DeviceType.VALUE) {
            throw new XException("只有Value格式的数据才支持渲染")
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")))
        if (startDate != null) {
            try {
                Date date = MongoDBDateUtils.formatD(startDate)
                println(date.toString())
            } catch (Exception e) {
                e.printStackTrace()
                logger.error(startDate + " 时间格式错误，正确格式：yyyy-MM-dd HH:mm:ss")
                throw new XException("时间格式错误，正确格式：yyyy-MM-dd HH:mm:ss")
            }
        }
        if (endDate != null) {
            try {
                Date date = MongoDBDateUtils.formatD(endDate)
                println(date.toString())

            } catch (Exception e) {
                e.printStackTrace()

                logger.error(endDate + " 时间格式错误，正确格式：yyyy-MM-dd HH:mm:ss")

                throw new XException("时间格式错误，正确格式：yyyy-MM-dd HH:mm:ss")

            }
        }
        //yyyy-MM-dd HH:mm:ss.000'Z'
        return R.okWithData(deviceDataService.list(deviceSecurityId,
                startDate,
                endDate,
                deviceType,
                PageRequest.of(page, size)))
    }


    /**
     * 加载原始数据
     * @param page
     * @param size
     * @param startDate
     * @param endDate
     * @param deviceSecurityId
     * @param deviceType
     * @return
     */
    @GetMapping("/listOriginData")
    R listOriginData(@RequestParam int page,
                     @RequestParam int size,
                     @RequestParam(required = false) String startDate,
                     @RequestParam(required = false) String endDate,
                     @RequestParam String deviceSecurityId,
                     @RequestParam DeviceType deviceType) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")))
        if (startDate != null) {
            try {
                Date date = MongoDBDateUtils.formatD(startDate)
                println(date.toString())
            } catch (Exception e) {
                e.printStackTrace()
                logger.error(startDate + " 时间格式错误，正确格式：yyyy-MM-dd HH:mm:ss")
                throw new XException("时间格式错误，正确格式：yyyy-MM-dd HH:mm:ss")
            }
        }
        if (endDate != null) {
            try {
                Date date = MongoDBDateUtils.formatD(endDate)
                println(date.toString())

            } catch (Exception e) {
                e.printStackTrace()

                logger.error(endDate + " 时间格式错误，正确格式：yyyy-MM-dd HH:mm:ss")

                throw new XException("时间格式错误，正确格式：yyyy-MM-dd HH:mm:ss")

            }
        }
        //yyyy-MM-dd HH:mm:ss.000'Z'
        return R.okWithData(deviceDataService.listOriginData(deviceSecurityId,
                startDate,
                endDate,
                deviceType,
                PageRequest.of(page, size)))
    }
}
