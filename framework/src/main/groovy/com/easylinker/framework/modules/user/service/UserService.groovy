package com.easylinker.framework.modules.user.service

import com.easylinker.framework.common.service.AbstractService
import com.easylinker.framework.modules.user.dao.UserRepository
import com.easylinker.framework.modules.user.model.AppUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class UserService extends AbstractService<AppUser> {
    @Autowired
    UserRepository userRepository


    AppUser findByPrinciple(String principle) {

        return userRepository.findByPrinciple(principle)

    }

    @Override
    void save(AppUser appUser) {
        userRepository.save(appUser)
    }

    @Override
    Page<AppUser> page(Pageable pageable) {
        return userRepository.findAll(pageable)
    }

    @Override
    AppUser getById(long id) {
        return userRepository.findTop1ById(id)
    }

    @Override
    void deleteById(long id) {
        userRepository.deleteById(id)

    }
}
