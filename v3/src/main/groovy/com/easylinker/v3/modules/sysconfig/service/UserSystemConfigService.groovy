package com.easylinker.v3.modules.sysconfig.service

import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.easylinker.framework.common.service.AbstractService
import com.easylinker.v3.modules.sysconfig.dao.UserSystemConfigRepository
import com.easylinker.v3.modules.sysconfig.model.UserSystemConfig
import com.easylinker.v3.modules.user.model.AppUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

/**
 * @author wwhai* @date 2019/8/10 22:41
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@Service
class UserSystemConfigService extends AbstractService<UserSystemConfig> {
    @Autowired
    UserSystemConfigRepository userSystemConfigRepository

    @Override
    void save(UserSystemConfig userSystemConfig) {
        userSystemConfigRepository.save(userSystemConfig)
    }

    @Override
    Page<UserSystemConfig> page(Pageable pageable) {
        return userSystemConfigRepository.findAll(pageable)
    }

    @Override
    UserSystemConfig getById(long id) {
        return userSystemConfigRepository.getOne(id)
    }

    @Override
    void delete(UserSystemConfig userSystemConfig) {
        userSystemConfigRepository.delete(userSystemConfig)
    }

    @Override
    void deleteById(long id) {
        userSystemConfigRepository.deleteById(id)
    }

//    /**
//     * 更新配置
//     * @return
//     */
//    UserSystemConfig updateConfig(AppUser appUser, List<Map<String, Object>> tabs) {
//        UserSystemConfig userSystemConfig =  userSystemConfigRepository.findTopByUserSecurityId(appUser.getSecurityId())
//        if (userSystemConfig) {
//            userSystemConfig = userSystemConfigRepository.findTopByUserSecurityId(appUser.getSecurityId())
//            userSystemConfig.setDisplayTabs(JSONObject.toJSONString(tabs))
//            userSystemConfigRepository.save(userSystemConfig)
//            return userSystemConfig
//
//        } else {
//            userSystemConfig = new UserSystemConfig()
//            userSystemConfig.setUserSecurityId(appUser.securityId)
//            userSystemConfig.setDisplayTabs(JSONObject.toJSONString(tabs))
//            userSystemConfigRepository.save(userSystemConfig)
//            return userSystemConfig
//
//        }
//
//    }
    /**
     * 更新配置
     * @param AppUser appUser
     * @param JSONObject tabsJson
     * @return UserSystemConfig
     */
    UserSystemConfig updateConfig(AppUser appUser, JSONArray tabsJson) {
        UserSystemConfig userSystemConfig =  userSystemConfigRepository.findTopByUserSecurityId(appUser.getSecurityId())
        if (!userSystemConfig) {
            userSystemConfig = new UserSystemConfig()
            userSystemConfig.setUserSecurityId(appUser.securityId)
        }
        userSystemConfig.setDisplayTabs(tabsJson)
        userSystemConfigRepository.save(userSystemConfig)
        return userSystemConfig
    }    
    /**
     *
     * @param appUser
     * @return
     */
    UserSystemConfig getByAppUser(AppUser appUser) {
        UserSystemConfig userSystemConfig = userSystemConfigRepository.findTopByUserSecurityId(appUser.getSecurityId())
        return userSystemConfig
    }
}
