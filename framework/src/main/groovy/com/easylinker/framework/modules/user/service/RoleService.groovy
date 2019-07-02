package com.easylinker.framework.modules.user.service

import com.easylinker.framework.common.service.AbstractService
import com.easylinker.framework.modules.user.dao.RoleRepository
import com.easylinker.framework.modules.user.model.AppUser
import com.easylinker.framework.modules.user.model.Role
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

/**
 * @author wwhai* @date 2019/6/6 21:44
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@Service
class RoleService extends AbstractService<Role> {
    @Autowired
    RoleRepository roleRepository

    @Override
    void save(Role role) {

        roleRepository.save(role)
    }

    @Override
    Page<Role> page(Pageable pageable) {
        return roleRepository.findAll(pageable)
    }

    @Override
    Role getById(long id) {
        return null
    }

    @Override
    void delete(Role role) {
        roleRepository.delete(role)

    }

    @Override
    void deleteById(long id) {

    }
/**
 * 获取用户的角色
 * @param appUser
 * @return
 */
    List<Role> getByUser(AppUser appUser) {
        roleRepository.findAllByAppUser(appUser)
    }
}
