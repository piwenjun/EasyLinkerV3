package com.easylinker.v3.modules.schedule.model

import com.easylinker.v3.common.model.AbstractModel
import com.vladmihalcea.hibernate.type.json.JsonStringType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef

import javax.persistence.Column
import javax.persistence.Entity

/**
 * 存在数据库里面关联设备和Job的表
 * Quartz首先会在自己的数据表里面插入新增加的记录
 * 为了和我们的具体业务整合，让具体的设备的任务和Quartz关联起来，做了个中间表
 * @author wwhai* @date 2019/8/18 16:20
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@Entity
@TypeDef(name = "json", typeClass = JsonStringType.class)

class JobEntity extends AbstractModel {
    private String userSecurityId

    /**
     * 业务关联字段，表示和此表关联的其他表的SID
     */
    private String bizSecurityId
    /**
     * 任务名称
     */
    private String jobName
    /**
     * 任务分组
     */
    private String jobGroup

    /**
     * Cron 表达式
     */
    private String cron
    /**
     * 状态
     */

    private String status

    /**
     * 任务描述
     */
    private String jobDescription

    /**
     * 传到Job里面的数据
     */

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Map<String, Object> bizJobData

    Map<String, Object> getBizJobData() {
        return bizJobData
    }

    String getUserSecurityId() {
        return userSecurityId
    }

    void setUserSecurityId(String userSecurityId) {
        this.userSecurityId = userSecurityId
    }

    void setBizJobData(Map<String, Object> bizJobData) {
        this.bizJobData = bizJobData
    }

    String getCron() {
        return cron
    }

    String getJobDescription() {
        return jobDescription
    }

    String getStatus() {
        return status
    }

    void setStatus(String status) {
        this.status = status
    }

    void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription
    }

    void setCron(String cron) {
        this.cron = cron
    }

    String getBizSecurityId() {
        return bizSecurityId
    }

    void setBizSecurityId(String bizSecurityId) {
        this.bizSecurityId = bizSecurityId
    }

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


}
