package com.easylinker.v3.modules.schedule.model

import org.quartz.DisallowConcurrentExecution
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException

/**
 * 这是一个最简单的Job，主要用于定时打印一些日志到数据库中
 *
 * @author wwhai* @date 2019/8/18 16:30
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@DisallowConcurrentExecution

class SchedulePostJob implements Job {
    @Override
    void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        println "任务描述：" + jobExecutionContext.getJobDetail().description
    }
}
