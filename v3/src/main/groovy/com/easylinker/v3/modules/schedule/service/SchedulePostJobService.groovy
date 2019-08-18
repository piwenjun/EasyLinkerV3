package com.easylinker.v3.modules.schedule.service

import com.easylinker.framework.common.exception.XException
import com.easylinker.framework.common.service.AbstractService
import com.easylinker.v3.modules.schedule.dao.JobEntityRepository
import com.easylinker.v3.modules.schedule.model.JobEntity
import com.easylinker.v3.modules.schedule.model.SchedulePostJob
import org.quartz.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author wwhai* @date 2019/8/18 16:17
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
class SchedulePostJobService extends AbstractService<JobEntity> {
    @Autowired
    Scheduler scheduler
    @Autowired
    JobEntityRepository jobEntityRepository

    /**
     * 转换器：Map转换成JobDataMap
     * @param map
     * @return
     */
    private static JobDataMap getDataMap(Map map) {
        JobDataMap jobDataMap = new JobDataMap()

        if (map.keySet().size() == 0) return jobDataMap

        for (String k : map.keySet()) {
            jobDataMap.put(k, map.get(k))
        }
        return jobDataMap
    }

    @Override
    void save(JobEntity jobEntity) {

        //构建job信息:JobBuilder.newJob 是关键，这个类用来实例化各种任务
        JobDetail jobDetail = JobBuilder.newJob(SchedulePostJob.class)
                .usingJobData(getDataMap(jobEntity.getBizJobData()))
                .withIdentity(new JobKey(jobEntity.getSecurityId(), jobEntity.getJobGroup()))
                .withDescription(jobEntity.getJobDescription()).build()
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(jobEntity.getCron())

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobEntity.getSecurityId(), jobEntity.getJobGroup())
                .withDescription(jobEntity.getJobDescription())
                .startNow().withSchedule(cronScheduleBuilder).build()
        //保存业务表
        jobEntity.setStatus("NORMAL")
        jobEntityRepository.save(jobEntity)
        //保存Quartz
        scheduler.scheduleJob(jobDetail, trigger)
        if (!scheduler.isShutdown()) {
            scheduler.start()
        }
    }

    @Override
    Page<JobEntity> page(Pageable pageable) {
        return jobEntityRepository.findAll(pageable)
    }

    @Override
    JobEntity getById(long id) {
        return jobEntityRepository.getOne(id)
    }

    /**
     * 删除Job的同时，中间表也要清空
     * @param jobEntity
     */
    @Override
    void delete(JobEntity jobEntity) {

        try {

            TriggerKey triggerKey = TriggerKey.triggerKey(jobEntity.getJobName(), jobEntity.jobGroup)
            scheduler.pauseTrigger(triggerKey)
            scheduler.unscheduleJob(triggerKey)
            JobKey jobKey = new JobKey(jobEntity.getJobName(), jobEntity.jobGroup)
            //删除Job
            scheduler.deleteJob(jobKey)
            //删除中间表
            jobEntityRepository.delete(jobEntity)

        } catch (Exception e) {
            throw new XException("删除失败")
        }


    }

    @Override
    void deleteById(long id) {

        jobEntityRepository.deleteById(id)

    }
    /**
     * 暂停Job
     * @param jobEntity
     * @return
     */

    boolean pause(JobEntity jobEntity) {
        JobKey jobKey = new JobKey(jobEntity.getJobName(), jobEntity.jobGroup)
        //删除Job
        jobEntity.setStatus("PAUSE")
        jobEntityRepository.save(jobEntity)

        scheduler.pauseJob(jobKey)
    }

    /**
     * 恢复Job
     * @param jobEntity
     * @return
     */
    boolean resume(JobEntity jobEntity) {
        JobKey jobKey = new JobKey(jobEntity.getJobName(), jobEntity.jobGroup)
        //删除Job
        jobEntity.setStatus("NORMAL")
        jobEntityRepository.save(jobEntity)

        scheduler.resumeJob(jobKey)
    }

    /**
     * 中断
     * @param jobEntity
     * @return
     */
    boolean interrupt(JobEntity jobEntity) {
        JobKey jobKey = new JobKey(jobEntity.getJobName(), jobEntity.jobGroup)
        //删除Job
        jobEntity.setStatus("INTERRUPT")
        jobEntityRepository.save(jobEntity)

        scheduler.interrupt(jobKey)
    }
    /**
     * 重新触发[立即触发一次]
     */
    boolean triggerJob(JobEntity jobEntity) {
        JobKey jobKey = new JobKey(jobEntity.getJobName(), jobEntity.jobGroup)
        //删除Job
        jobEntity.setStatus("NORMAL")
        jobEntityRepository.save(jobEntity)
        scheduler.triggerJob(jobKey)
    }

    boolean updateJob(JobEntity jobEntity) {

    }
}
