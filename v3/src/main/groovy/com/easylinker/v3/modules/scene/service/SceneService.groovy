package com.easylinker.v3.modules.scene.service

import com.easylinker.framework.common.service.AbstractService
import com.easylinker.v3.modules.scene.dao.SceneRepository
import com.easylinker.v3.modules.scene.model.Scene
import com.easylinker.v3.modules.user.model.AppUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class SceneService extends AbstractService<Scene> {
    @Autowired
    SceneRepository sceneRepository

    @Override
    void save(Scene scene) {
        sceneRepository.save(scene)

    }

    @Override
    Page<Scene> page(Pageable pageable) {
        return sceneRepository.findAll(pageable)
    }

    @Override
    Scene getById(long id) {
        return sceneRepository.findById(id).get()
    }

    @Override
    void delete(Scene scene) {

        sceneRepository.delete(scene)
    }

    @Override
    void deleteById(long id) {
        sceneRepository.deleteById(id)
    }
    /**
     * 计算用户有多少个场景
     * @param appUser
     * @return
     */

    long countByAppuser(AppUser appUser) {
        return sceneRepository.countByAppUser(appUser)
    }
    /**
     * 根据安全ID查找
     * @param securityId
     * @return
     */
    Scene findBySecurityId(String securityId) {
        return sceneRepository.findBySecurityId(securityId)
    }

    Page<Scene> findByAppUser(AppUser appUser, Pageable pageable) {
        return sceneRepository.findByAppUser(appUser, pageable)
    }


}
