package com.easylinker.v3.modules.developer.controller

import com.easylinker.framework.common.controller.AbstractController
import com.easylinker.framework.common.exception.XException
import com.easylinker.framework.common.web.R
import com.easylinker.framework.common.web.ReturnEnum
import com.easylinker.v3.modules.developer.form.AddAppForm
import com.easylinker.v3.modules.developer.model.DevelopApp
import com.easylinker.v3.modules.developer.model.Developer
import com.easylinker.v3.modules.developer.model.DeveloperState
import com.easylinker.v3.modules.developer.service.DeveloperService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

/**
 * 开发者相关
 * @author wwhai* @date 2019/7/5 22:00
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@RestController
@RequestMapping("/developer")
class DeveloperController extends AbstractController {

    @Autowired
    DeveloperService developerService

    DeveloperController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }

    /**
     * 申请开发者
     * @return
     */

    @GetMapping("/apply")
    R apply() {
        Developer developer = new Developer(level: 0, developerState: DeveloperState.APPLYING, appUser: getCurrentUser())
        developerService.save(developer)
        return R.ok(ReturnEnum.SUCCESS)
    }
    /**
     * 获取用户的开发者身份
     * @return
     */
    @GetMapping("/getDeveloper")
    R getDeveloper() {
        return R.okWithData(developerService.findByAppUser(getCurrentUser()))
    }


    /**
     * 开发者添加一个APP
     * @return
     */

    @PostMapping("/addApp")
    R addApp(@RequestBody @Valid AddAppForm addAppForm) {
        Developer developer = developerService.findByAppUser(getCurrentUser())
        if (isDeveloper(developer)) {
            DevelopApp developApp = new DevelopApp()
            developApp.setAppName(addAppForm.appName)
            developApp.setAppInfo(addAppForm.appInfo)
            developerService.addApp(developApp)
            return R.ok(ReturnEnum.SUCCESS)
        } else {
            return R.error(401, "该用户不是开发者")

        }

    }

    /**
     * 列出APP
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/listApps/{page}/{size}")
    R listApps(@PathVariable int page, @PathVariable int size) {
        Developer developer = developerService.findByAppUser(getCurrentUser())
        if (isDeveloper(developer)) {
            return R.okWithData(developerService.listApp(developer, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"))))
        } else {
            return R.error(401, "该用户不是开发者")

        }

    }

    /**
     * 私有方法 判断某个用户是不是开发者
     * @param developer
     * @return
     */
    private static isDeveloper(Developer developer) {
        if (developer) {
            if (developer.developerState == DeveloperState.NORMAL) {
                return true
            } else {
                switch (developer.developerState) {
                    case DeveloperState.APPLYING:
                        throw new XException("开发者正在申请中")
                    case DeveloperState.FORBID:
                        throw new XException("开发者账号被封禁")
                    case DeveloperState.FREEZE:
                        throw new XException("开发者账号被冻结")
                    default:
                        throw new XException("开发者状态异常")
                }
            }
        } else {
            return false
        }
    }
}
