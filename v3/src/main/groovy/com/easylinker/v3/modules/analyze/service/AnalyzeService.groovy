package com.easylinker.v3.modules.analyze.service

import com.easylinker.framework.common.service.AbstractService
import com.easylinker.v3.modules.analyze.dao.AnalyzeRepository
import com.easylinker.v3.modules.analyze.model.AnalyzeResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

/**
 * @author wwhai* @date 2019/6/12 22:37
 * @email:751957846 @qq.com瞅啥瞅？代码拿过来我看看有没有BUG。
 */
@Service
class AnalyzeService extends AbstractService<AnalyzeResult> {
    @Autowired
    AnalyzeRepository analyzeRepository

    @Override
    void save(AnalyzeResult analyzeResult) {
        analyzeRepository.save(analyzeResult)
    }

    @Override
    Page<AnalyzeResult> page(Pageable pageable) {
        analyzeRepository.findAll(pageable)
    }

    @Override
    AnalyzeResult getById(long id) {
        analyzeRepository.findById(id).get()
    }

    @Override
    void deleteById(long id) {
        analyzeRepository.deleteById(id)
    }
}
