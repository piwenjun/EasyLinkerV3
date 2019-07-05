package com.easylinker.v3.modules.developer.service

import com.easylinker.framework.common.service.AbstractService
import com.easylinker.framework.modules.user.model.AppUser
import com.easylinker.v3.modules.developer.dao.DeveloperAppRepository
import com.easylinker.v3.modules.developer.dao.DeveloperRepository
import com.easylinker.v3.modules.developer.model.DevelopApp
import com.easylinker.v3.modules.developer.model.Developer
import com.easylinker.v3.modules.developer.model.DeveloperState
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

/**
 * @author wwhai* @date 2019/7/5 22:41
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@Service
class DeveloperService extends AbstractService<Developer> {
    @Autowired
    DeveloperRepository developerRepository

    @Override
    void save(Developer developer) {
        developerRepository.save(developer)

    }

    @Override
    Page<Developer> page(Pageable pageable) {
        return null
    }

    @Override
    Developer getById(long id) {
        return null
    }

    @Override
    void delete(Developer developer) {

    }

    @Override
    void deleteById(long id) {

    }
    /**
     * 改变开发者状态
     * @param developer
     * @param developerState
     */
    void changeState(Developer developer, DeveloperState developerState) {
        developer.setDeveloperState(developerState)
        save(developer)
    }
    /**
     * 通过申请
     * @param developer
     */
    void accessApply(Developer developer) {
        changeState(developer, DeveloperState.NORMAL)
    }

    void forbidDeveloper(Developer developer) {
        changeState(developer, DeveloperState.FORBID)
    }

    void freezeDeveloper(Developer developer) {
        changeState(developer, DeveloperState.FREEZE)
    }

    void Applying(Developer developer) {
        changeState(developer, DeveloperState.APPLYING)
    }

    Developer findByAppUser(AppUser appUser) {
        return developerRepository.findTopByAppUser(appUser)
    }


    @Autowired
    DeveloperAppRepository developerAppRepository

    /**
     * 添加一个APP
     * @param developApp
     */

    void addApp(DevelopApp developApp) {
        developerAppRepository.save(developApp)
    }

    /**
     * 查询某开发者的APP
     * @param developer
     * @param pageable
     * @return
     */
    Page<DevelopApp> listApp(Developer developer, Pageable pageable) {
        return developerAppRepository.findAllByDeveloper(developer, pageable)
    }


}
