package com.easylinker.v3.modules.devicedata.controller

import com.easylinker.framework.common.controller.AbstractController
import com.easylinker.framework.common.web.R
import com.easylinker.v3.modules.devicedata.form.DataQueryForm
import com.easylinker.v3.modules.devicedata.service.DeviceDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*

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
    @PostMapping("/list/{page}/{size}")
    R list(@PathVariable int page, @PathVariable int size, @RequestBody @Valid DataQueryForm dataQueryForm) {

        return R.okWithData(deviceDataService.list(dataQueryForm.deviceSecurityId, dataQueryForm.deviceType, PageRequest.of(page, size)))
    }
}
