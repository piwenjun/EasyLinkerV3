package com.easylinker.v3.modules.sysconfig.controller

import com.easylinker.framework.common.web.R
import com.easylinker.v3.common.controller.AbstractController
import com.easylinker.v3.modules.sysconfig.service.UserSystemConfigService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletRequest

/**
 * @author wwhai* @date 2019/8/10 22:36
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@RestController
@RequestMapping("/systemConfig")
class SystemConfigController extends AbstractController {
    @Autowired
    UserSystemConfigService userSystemConfigService

    SystemConfigController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }

    /**
     * 更新配置
     * [
     *{*   "name": "HTTP协议设备",
     *   "key": "HTTP"
     *}* ]
     * @param tabs
     * @return
     */
    @PutMapping("/update")
    R update(@RequestBody List<Map<String, Object>> tabs) {
        userSystemConfigService.updateConfig(getCurrentUser(), tabs)
        return R.ok("配置更新成功")
    }

    /**
     * 获取配置
     * @return
     */
    @GetMapping("/getConfig")
    R getConfig() {
        return R.okWithData(userSystemConfigService.getByAppUser(getCurrentUser()))
    }
}
