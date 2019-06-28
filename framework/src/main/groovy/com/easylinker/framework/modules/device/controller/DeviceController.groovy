package com.easylinker.framework.modules.device.controller

import com.easylinker.framework.common.controller.AbstractController
import com.easylinker.framework.common.web.R
import com.easylinker.framework.modules.device.form.AddDeviceForm
import com.easylinker.framework.modules.device.model.Device
import com.easylinker.framework.modules.device.service.DeviceService
import com.easylinker.framework.modules.user.model.AppUser
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

/**
 * Created by admin on 2019/6/5 17:06
 */


@RestController
@RequestMapping("/device")
class DeviceController extends AbstractController {
    @Autowired
    DeviceService deviceService

    DeviceController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }

    @Override
    @GetMapping("/getById")
    R getById(@RequestParam int id) {
        R.okWithData(deviceService.getById(id))
    }

    @Override
    @DeleteMapping("/deleteById")

    R deleteById(@RequestParam long id) {
        deviceService.deleteById(id)
        R.ok()
    }

    @Override
    @GetMapping(value = "/list")

    R list(@RequestParam int page, @RequestParam int size) {
        R.okWithData(deviceService.page(PageRequest.of(page, size)))
    }

    @GetMapping(value = "/findAllByAppUser")
    R findAllByAppUser(@RequestParam int page, @RequestParam int size) {
        R.okWithData(deviceService.findAllByAppUser(getCurrentUser(), PageRequest.of(page, size, Sort.Direction.DESC, "id")))
    }

    @PostMapping(value = "/add")
    R add(@Valid @RequestBody AddDeviceForm addDeviceForm) {

        R.ok()
    }
}
