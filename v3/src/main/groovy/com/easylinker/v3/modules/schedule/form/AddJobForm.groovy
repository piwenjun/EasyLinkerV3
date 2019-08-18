package com.easylinker.v3.modules.schedule.form

import javax.validation.constraints.NotNull

/**
 * @author wwhai* @date 2019/8/18 16:24
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
class AddJobForm {
    @NotNull(message = "名称不可为空")
    private String jobName
    @NotNull(message = "分组不可为空")
    private String jobGroup
    @NotNull(message = "Cron表达式不可为空")
    private String cron
    private String jobDescription
    private Map<String, Object> bizJobData

    String getJobName() {
        return jobName
    }

    void setJobName(String jobName) {
        this.jobName = jobName
    }

    String getJobGroup() {
        return jobGroup
    }

    void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup
    }

    String getCron() {
        return cron
    }

    void setCron(String cron) {
        this.cron = cron
    }

    String getJobDescription() {
        return jobDescription
    }

    void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription
    }

    Map<String, Object> getBizJobData() {
        return bizJobData
    }

    void setBizJobData(Map<String, Object> bizJobData) {
        this.bizJobData = bizJobData
    }
}
