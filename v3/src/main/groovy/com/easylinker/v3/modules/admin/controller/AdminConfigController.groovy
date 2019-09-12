package com.easylinker.v3.modules.admin.controller

import com.alibaba.fastjson.JSONObject
import com.easylinker.framework.common.web.R
import com.easylinker.v3.common.controller.AbstractController
import com.easylinker.v3.config.security.RequireAuthRoles
import com.easylinker.v3.modules.sysconfig.model.UserSystemConfig
import com.easylinker.v3.modules.sysconfig.service.UserSystemConfigService
import com.easylinker.v3.modules.user.model.AppUser
import com.easylinker.v3.modules.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletRequest

/**
 * 后台管理员
 * @author wwhai* @date 2019/8/13 18:13
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@RestController
@RequestMapping("/adminConfig")
@RequireAuthRoles(roles = ['ADMIN', 'BASE_ROLE'])

class AdminConfigController extends AbstractController{
    @Autowired
    UserSystemConfigService userSystemConfigService
    @Autowired
    UserService userService

    AdminConfigController(HttpServletRequest httpServletRequest) {
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
    @PostMapping("/update")
    R update(@RequestBody JSONObject configBody) {

        String securityId = configBody.getString("securityId");
        JSONObject tabsJson = configBody.getJSONObject("tabs")

        AppUser appUser = userService.findBySecurityId(securityId)
        if(appUser){
            userSystemConfigService.updateConfig(appUser, tabsJson)
            return R.ok("配置更新成功")
        }else {
            return R.error("用户不存在")
        }
    }

    /**
     * 获取配置
     * @return
     */
    @GetMapping("/getConfig")
    R getConfig(@RequestParam String securityId) {
        AppUser appUser = userService.findBySecurityId(securityId)

        UserSystemConfig userSystemConfig = userSystemConfigService.getByAppUser(appUser)
        if(userSystemConfig){
            Map<String, Object> dataMap = new HashMap<>()
            dataMap.put("tabs", userSystemConfig.displayTabs)
            dataMap.put("userSecurityId", userSystemConfig.userSecurityId)
            dataMap.put("updateTime", userSystemConfig.updateTime)
            return R.okWithData(dataMap)
        }else {
            return R.error("用户配置不存在")
        }

    }
}

