package com.easylinker.v3.modules.deviceecho.controller


import com.easylinker.framework.common.web.R
import com.easylinker.v3.modules.controller.AbstractController
import com.easylinker.v3.modules.deviceecho.service.DeviceOperateLogService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest

/**
 * @author wwhai* @date 2019/7/23 23:40
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@RestController
@RequestMapping("/deviceOperate")
class DeviceOperateController extends AbstractController {
    @Autowired
    DeviceOperateLogService deviceOperateLogService

    DeviceOperateController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }

    @GetMapping("/listLog")
    R listLog(@RequestParam int page, @RequestParam int size, @RequestParam String deviceSecurityId) {

        return R.okWithData(deviceOperateLogService.list(deviceSecurityId, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"))))
    }

}
