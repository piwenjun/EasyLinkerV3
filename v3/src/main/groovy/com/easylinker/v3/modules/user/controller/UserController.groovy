package com.easylinker.v3.modules.user.controller

import cn.hutool.json.JSONArray
import com.easylinker.framework.common.web.R
import com.easylinker.v3.common.controller.AbstractController
import com.easylinker.v3.modules.device.form.UpdateForm
import com.easylinker.v3.modules.user.model.AppUser
import com.easylinker.v3.modules.user.model.Role
import com.easylinker.v3.modules.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

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
            Map<String,Object>map=new HashMap<>()
            map.put("name",role.getName())
            map.put("info",role.getInfo())
            roleArray.add(map)
        }

        userInfo.put("roles", roleArray)
        Map<String, Object> dataMap = new HashMap<>()
        dataMap.put("roles", roleArray)
        dataMap.put("principle", appUser.principle)
        dataMap.put("email", appUser.email)
        dataMap.put("name", appUser.name)
        return R.okWithData(dataMap)

    }

    /**
     * 更新用户资料
     * @param updateForm
     * @return
     */
    @PutMapping("/update")
    R update(@RequestBody   UpdateForm updateForm) {
        AppUser appUser = userService.findBySecurityId(getCurrentUser().securityId)
        if (appUser) {
            appUser.setName(updateForm.getName())
            userService.save(appUser)
            return R.ok("用户信息更新成功")
        } else {
            return R.error("用户不存在")

        }

    }
}
