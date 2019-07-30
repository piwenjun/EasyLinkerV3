package com.easylinker.coapservice.coapdata.controller

import com.easylinker.coapservice.coapdata.form.PushDataForm
import com.easylinker.framework.common.web.R
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * CoAP 数据夫口和出口服务
 * @author wwhai* @date 2019/7/30 21:07
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@RestController
@RequestMapping("/CoAPData")
class CoAPDataController {
    /**
     * CoAP 终端设备上传数据接口
     * @param pushDataForm
     * @return
     */
    @PostMapping("/push")
    R push(@RequestBody PushDataForm pushDataForm) {

        return R.ok("Push Success")
    }
}
