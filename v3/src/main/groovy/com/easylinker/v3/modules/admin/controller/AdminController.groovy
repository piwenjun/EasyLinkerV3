package com.easylinker.v3.modules.admin.controller

import com.easylinker.framework.common.exception.XException
import cn.hutool.json.JSONArray
import com.easylinker.framework.common.web.R
import com.easylinker.v3.common.controller.AbstractController
import com.easylinker.v3.config.security.RequireAuthRoles
import com.easylinker.v3.modules.user.model.AppUser
import com.easylinker.v3.modules.user.model.Role
import com.easylinker.v3.modules.user.model.UserStatus
import com.easylinker.v3.modules.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*


import javax.servlet.http.HttpServletRequest
/**
 * 后台管理员
 * @author wwhai* @date 2019/8/11 23:13
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@RestController
@RequestMapping("/admin")
@RequireAuthRoles(roles = ['ADMIN', 'BASE_ROLE'])
class AdminController extends AbstractController {

    AdminController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }

    @Autowired
    UserService userService
    
    /**
     * 管理员获取所有用户列表
     * @param int page
     * @param int size
     * @return
     */
    @GetMapping("/getAllUsers")
    @Transactional
    R getAllUsers(@RequestParam int page,
           @RequestParam int size) {
        JSONArray dataArray = new JSONArray()
        Page<AppUser> userPage= userService.page(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime")))
        Map<String, Object> pageMap = new HashMap<>()
        pageMap.put("page", userPage.getNumber())
        pageMap.put("totalElements", userPage.getTotalElements())
        pageMap.put("totalPages", userPage.getTotalPages())
        pageMap.put("size", userPage.getSize())
        pageMap.put("isLast", userPage.isLast())
        pageMap.put("isFirst", userPage.isFirst())

        for (AppUser appUser : userPage.getContent()) {
            Map<String, Object> userInfo = new HashMap<>()
            JSONArray roleArray = new JSONArray()
            List<Role> roles = appUser.roles
            for (Role role : roles) {
                roleArray.add(role.name)
            }
            userInfo.put("roles", roleArray)
            userInfo.put("principle", appUser.principle)
            userInfo.put("email", appUser.email)
            userInfo.put("name", appUser.name)
            /*传出 securityId ,方便从列表中选择查看详情*/
            userInfo.put("securityId", appUser.securityId)
            dataArray.add(userInfo);
        }
        pageMap.put("data", dataArray)
        return R.okWithData(pageMap)
    }

   /**
    * 查看用户详情
    * @param String securityId
    * @return
    */
    @GetMapping("/getUserDetail")
    @Transactional
    R getUserDetail(@RequestParam String securityId) {
   
        AppUser appUser = userService.findBySecurityId(securityId)
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

    /**
    * 冻结账户
    * @param String securityId
    * @return
    */
    @GetMapping("/freezeUser")
    @Transactional
    R freezeUser(@RequestParam String securityId) {
   
        AppUser appUser = userService.findBySecurityId(securityId)
        if(appUser) {
            appUser.userStatus = UserStatus.FREEZE
            userService.save(appUser)
            return R.ok("操作成功")
        } else {
            return R.error("用户不存在")
        }
    }

    /**
    * 禁用账户
    * @param String securityId
    * @return
    */
    @GetMapping("/forbidUser")
    @Transactional
    R forbidUser(@RequestParam String securityId) {
   
        AppUser appUser = userService.findBySecurityId(securityId)
        if(appUser) {
            appUser.userStatus = UserStatus.FORBID
            userService.save(appUser)
            return R.ok("操作成功")
        } else {
            return R.error("用户不存在")
        }
    }

    /**
    * 解封账户
    * @param String securityId
    * @return
    */
    @GetMapping("/unblockUser")
    @Transactional
    R unblockUser(@RequestParam String securityId) {
   
        AppUser appUser = userService.findBySecurityId(securityId)
        if(appUser) {
            appUser.userStatus = UserStatus.NORMAL
            userService.save(appUser)
            return R.ok("操作成功")
        } else {
            return R.error("用户不存在")
        }
    }  

    /**
    * 删除用户
    * @param String securityId
    * @return
    */
    @GetMapping("/deleUser")
    @Transactional
    R deleUser(@RequestParam String securityId) {
   
        AppUser appUser = userService.findBySecurityId(securityId)
        if(appUser) {
            userService.delete(appUser)
            return R.ok("操作成功")

        } else {
            return R.error("用户不存在")
        }
    }  

}

