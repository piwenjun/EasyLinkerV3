package com.easylinker.framework.modules.user.controller

import cn.hutool.json.JSONArray
import com.easylinker.framework.common.controller.AbstractController
import com.easylinker.framework.common.web.R
import com.easylinker.framework.modules.user.model.AppUser
import com.easylinker.framework.modules.user.model.Role
import com.easylinker.framework.modules.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/user")
class UserController extends AbstractController {
    UserController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }

    /**
     * 获取用户信息
     * @return
     */
    @Autowired
    UserService userService

    @GetMapping("/detail")
    @Transactional
    R detail() {
        AppUser appUser = userService.findBySecurityId(getCurrentUser().securityId)
        Map<String, Object> userInfo = new HashMap<>()
        JSONArray roleArray = new JSONArray()
        List<Role> roles = appUser.roles
        for (Role role : roles) {
            roleArray.add(role.name)
        }

        userInfo.put("roles", roleArray)
        Map<String, Object> dataMap = new HashMap<>()
        dataMap.put("roles", roleArray)
        dataMap.put("principle", appUser.principle)
        dataMap.put("email", appUser.email)
        dataMap.put("name", appUser.name)
        return R.okWithData(dataMap)

    }
}
