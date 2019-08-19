package com.easylinker.v3.modules.schedule.controller

import com.easylinker.framework.common.web.R
import com.easylinker.v3.common.controller.AbstractController
import com.easylinker.v3.modules.schedule.form.AddJobForm
import com.easylinker.v3.modules.schedule.model.JobEntity
import com.easylinker.v3.modules.schedule.service.SchedulePostJobService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

/**
 * 简单定时计划任务
 * @author wwhai* @date 2019/7/13 10:44
 * @email:751957846 @qq.com瞅啥瞅？代码拿过来我看看有没有BUG。
 */
@RestController
@RequestMapping("/job")
class SchedulePostJobController extends AbstractController {

    @Autowired
    SchedulePostJobService schedulePostJobService

    SchedulePostJobController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }

    /**
     * 添加任务
     * @return
     */
    @PostMapping("/addJob")
    R addJob(@RequestBody @Valid AddJobForm addJobForm) {
        JobEntity jobEntity = new JobEntity(
                userSecurityId: getCurrentUser().securityId,
                bizSecurityId: getCurrentUser().securityId,
                jobName: addJobForm.jobName,
                jobGroup: addJobForm.jobGroup,
                bizJobData: addJobForm.bizJobData,
                cron: addJobForm.cron,
                status: "NORMAL",
                jobDescription: addJobForm.jobDescription
        )
        schedulePostJobService.save(jobEntity)
        R.ok("添加成功")
    }
}
