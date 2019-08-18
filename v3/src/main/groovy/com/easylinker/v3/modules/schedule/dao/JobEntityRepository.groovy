package com.easylinker.v3.modules.schedule.dao

import com.easylinker.v3.modules.schedule.model.JobEntity
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 中间表查询器
 * @author wwhai* @date 2019/8/18 16:29
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
interface JobEntityRepository extends JpaRepository<JobEntity, Long> {
}
