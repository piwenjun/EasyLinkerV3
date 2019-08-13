package com.easylinker.v3.modules.user.service

import com.easylinker.framework.common.service.AbstractService
import com.easylinker.v3.modules.user.dao.UserRepository
import com.easylinker.v3.modules.user.model.AppUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class UserService extends AbstractService<AppUser> {
    @Autowired
    UserRepository userRepository


    AppUser findByPrinciple(String principle) {

        return userRepository.findTopByPrinciple(principle)

    }

    AppUser findByEmail(String email) {

        return userRepository.findTopByEmail(email)

    }

    AppUser findBySecurityId(String securityId) {

        return userRepository.findTopBySecurityId(securityId)

    }
    
    Page<AppUser> search(String keyWords, Pageable pageable) {
        return userRepository.search(keyWords, pageable)
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
        return userRepository.findById(id).get()
    }

    @Override
    void delete(AppUser appUser) {
        userRepository.delete(appUser)

    }

    @Override
    void deleteById(long id) {
        userRepository.deleteById(id)

    }
}
