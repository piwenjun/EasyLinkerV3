package com.easylinker.v3.modules.device.service

import com.easylinker.framework.common.service.AbstractService
import com.easylinker.v3.modules.device.dao.TopicAclRepository
import com.easylinker.v3.modules.device.model.TopicAcl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TopicAclService extends AbstractService<TopicAcl> {
    @Autowired
    TopicAclRepository topicAclRepository

    @Override
    void save(TopicAcl topicAcl) {

        topicAclRepository.save(topicAcl)
    }

    @Override
    Page<TopicAcl> page(Pageable pageable) {
        return null
    }

    @Override
    TopicAcl getById(long id) {
        return null
    }

    @Override
    void delete(TopicAcl topicAcl) {

    }

    @Override
    void deleteById(long id) {

    }
}
