package com.easylinker.v3.modules.devicedata.controller

import com.easylinker.framework.common.web.R
import com.easylinker.v3.common.controller.AbstractController
import com.easylinker.v3.modules.devicedata.service.DeviceDataService
import com.easylinker.v3.common.model.DeviceType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

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
     * 查询设备数据
     * @param page
     * @param size
     * @param dataQueryForm
     * @return
     */
    @GetMapping("/list")
    R list(@RequestParam int page,
           @RequestParam int size,
           @RequestParam   String deviceSecurityId,
           @RequestParam DeviceType deviceType) {

        return R.okWithData(deviceDataService.list(deviceSecurityId,
                deviceType, PageRequest.of(page, size)))
    }
}
