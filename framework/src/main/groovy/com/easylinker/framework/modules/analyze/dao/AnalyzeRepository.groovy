package com.easylinker.framework.modules.analyze.dao

import com.easylinker.framework.modules.analyze.model.AnalyzeResult
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author wwhai* @date 2019/6/12 22:33
 * @email:751957846 @qq.com瞅啥瞅？代码拿过来我看看有没有BUG。
 */
interface AnalyzeRepository extends JpaRepository<AnalyzeResult, Long> {
}
