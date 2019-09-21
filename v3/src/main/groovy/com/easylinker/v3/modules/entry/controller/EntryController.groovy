package com.easylinker.v3.modules.entry.controller

import cn.hutool.json.JSONArray
import com.easylinker.framework.common.exception.XException
import com.easylinker.framework.common.web.R
import com.easylinker.framework.common.web.ReturnEnum
import com.easylinker.framework.utils.RedisUtils
import com.easylinker.v3.common.controller.AbstractController
import com.easylinker.v3.common.model.DeviceProtocol
import com.easylinker.v3.modules.entry.form.LoginForm
import com.easylinker.v3.modules.entry.form.SignUpForm
import com.easylinker.v3.modules.sysconfig.service.UserSystemConfigService
import com.easylinker.v3.modules.syslog.model.SystemLog
import com.easylinker.v3.modules.user.model.AppUser
import com.easylinker.v3.modules.user.model.Role
import com.easylinker.v3.modules.user.service.RoleService
import com.easylinker.v3.modules.user.service.UserService
import com.easylinker.v3.utils.CaptchaUtils
import com.easylinker.v3.utils.JwtUtils
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

import static com.alibaba.fastjson.JSON.parseArray
import static com.alibaba.fastjson.JSON.toJSONString

/**
 * @author wwhai* @date 2019/6/4 21:43
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@RestController
@RequestMapping("/entry")
class EntryController extends AbstractController {
    @Autowired
    CaptchaUtils captchaUtils
    @Autowired
    UserService userService

    @Autowired
    RoleService roleService

    @Autowired
    RedisUtils redisUtils

    @Autowired
    MongoTemplate mongoTemplate

    EntryController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }

    @PostMapping("/login")
    @Transactional
    R login(  @RequestBody LoginForm loginForm) {
        if (!captchaUtils.validate(loginForm.uuid, loginForm.captchaCode)) {

            throw new XException("验证码识别失败!")
        }

        AppUser appUser = userService.findByPrinciple(loginForm.getPrinciple())

        if (appUser && appUser.getPassword() == DigestUtils.sha256Hex(loginForm.getPassword())) {
            Map<String, Object> jwtMap = new HashMap<>()
            jwtMap.put("principle", appUser.principle)
            JSONArray roleArray = new JSONArray()
            List<Role> roles = appUser.roles
            for (Role role : roles) {
                roleArray.add(role.name)
            }

            jwtMap.put("roles", roleArray)
            jwtMap.put("securityId", appUser.securityId)
            jwtMap.put("id", appUser.id)
            Map<String, Object> dataMap = new HashMap<>()
            dataMap.put("roles", roleArray)
            dataMap.put("principle", appUser.principle)
            dataMap.put("token", JwtUtils.token(jwtMap))
            //redis缓存
            try {
                redisUtils.set("USER:" + appUser.securityId, toJSONString(dataMap), 3_600_0000L)
                mongoTemplate.save(new SystemLog(reason: "登陆", info: "用户:" + appUser.name + " 登陆成功,登陆IP:" + getHttpServletRequest().getRemoteHost(), userSecurityId: appUser.securityId), "SYSTEMLOG")
                return R.okWithData(dataMap)

            } catch (e) {
                logger.error("Redis异常:" + e.message)
                throw new XException("内部错误")
            }
        } else {
            mongoTemplate.save(new SystemLog(reason: "登陆", info: "登陆失败,登陆IP:" + getHttpServletRequest().getRemoteHost()), "SYSTEMLOG")

            throw new XException("登陆失败!用户不存在!")
        }
    }

    /**
     * 注册
     * @param baseForm
     * @return
     */
    @Autowired
    UserSystemConfigService userSystemConfigService

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/signUp")
    R signUp(  @RequestBody SignUpForm signUpForm) {

        if (userService.findByPrinciple(signUpForm.principle) != null) {
            return R.error("用户名已经存在!")
        }

        if (userService.findByEmail(signUpForm.email) != null) {
            return R.error("该邮箱已经被注册!")

        }
        AppUser appUser = new AppUser(principle: signUpForm.principle,
                password: DigestUtils.sha256Hex(signUpForm.password),
                email: signUpForm.email,
                name: signUpForm.getName()
        )
        /**
         * 生成默认配置
         */
        List<Map<String, Object>> list = new ArrayList<>()
        for (DeviceProtocol deviceProtocol : DeviceProtocol.values()) {
            Map<String, Object> map = new HashMap<>()
            map.put("name", deviceProtocol.name)
            map.put("key", deviceProtocol)
            map.put("display", true)
            list.add(map)
        }

        userSystemConfigService.updateConfig(appUser, parseArray(toJSONString(list)))

        userService.save(appUser)
        roleService.save(new Role(name: "BASE_ROLE", info: "基本权限", appUser: appUser))
        mongoTemplate.save(new SystemLog(reason: "注册", info: "用户:" + appUser.name + " 注册成功", userSecurityId: ""), "SYSTEMLOG")
        return R.ok("注册成功")

    }

    /**
     * 注销账号
     * @return
     */
    @GetMapping
    @RequestMapping("/logOut")
    R logOut() {
        String redisKey = "USER:" + getCurrentUser().getSecurityId()
        if (redisUtils.hasKey(redisKey)) {
            redisUtils.del(redisKey)
            return R.ok(ReturnEnum.SUCCESS)

        } else {
            return R.error(ReturnEnum.FAIL)

        }


    }
}
