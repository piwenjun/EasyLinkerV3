package com.easylinker.coapservice.coapdata.controller

import com.easylinker.framework.common.web.R
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * CoAP 数据夫口和出口服务
 * @author wwhai* @date 2019/7/30 21:07
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@RestController
class CoAPDataController {
    /**
     * CoAP 终端设备上传数据接口
     * @param pushDataForm
     * @return
     */
    @GetMapping("/")
    R i() {
        return R.ok("COAP Service ok")
    }
}
