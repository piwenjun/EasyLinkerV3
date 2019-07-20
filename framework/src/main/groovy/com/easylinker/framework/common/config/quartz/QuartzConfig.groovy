//package com.easylinker.framework.common.config.quartz
//
//import org.quartz.Scheduler
//import org.quartz.ee.servlet.QuartzInitializerListener
//
///**
// * @author wwhai* @date 2019/7/20 23:27
// * @email:751957846 @qq.com
// * 瞅啥瞅？代码拿过来我看看有没有BUG。
// *
// */
//
//import org.quartz.spi.TriggerFiredBundle
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.beans.factory.config.AutowireCapableBeanFactory
//import org.springframework.beans.factory.config.PropertiesFactoryBean
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.core.io.ClassPathResource
//import org.springframework.scheduling.quartz.AdaptableJobFactory
//import org.springframework.scheduling.quartz.SchedulerFactoryBean
//import org.springframework.stereotype.Component
//
///**
// * 对quartz.rpoperties进行读取
// * @author vividzc*
// */
//@Configuration
//class SchedulerConfig {
//
//    @Autowired
//    private MyJobFactory myJobFactory
//
//    @Bean
//    SchedulerFactoryBean schedulerFactoryBean() throws IOException {
//        SchedulerFactoryBean factory = new SchedulerFactoryBean()
//
//        factory.setOverwriteExistingJobs(true)
//
//        // 延时启动
//        factory.setStartupDelay(20)
//
//        // 加载quartz数据源配置
//        factory.setQuartzProperties(quartzProperties())
//
//        // 自定义Job Factory，用于Spring注入
//        factory.setJobFactory(myJobFactory)
//
//        return factory
//    }
//
//    @Bean
//    public Properties quartzProperties() throws IOException {
//        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean()
//        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"))
//        //在quartz.properties中的属性被读取并注入后再初始化对象
//        propertiesFactoryBean.afterPropertiesSet()
//        return propertiesFactoryBean.getObject()
//    }
//
//    /*
//     * quartz初始化监听器
//     */
//
//    @Bean
//    QuartzInitializerListener executorListener() {
//        return new QuartzInitializerListener()
//    }
//
//    /*
//     * 通过SchedulerFactoryBean获取Scheduler的实例
//     */
//
//    @Bean(name = "scheduler")
//    Scheduler scheduler() throws Exception {
//        return schedulerFactoryBean().getScheduler()
//    }
//
//
//}
//
//@Component
//class MyJobFactory extends AdaptableJobFactory {
//
//    @Autowired
//    private AutowireCapableBeanFactory capableBeanFactory
//
//    @Override
//    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
//        // 调用父类的方法
//        Object jobInstance = super.createJobInstance(bundle)
//        // 进行注入
//        capableBeanFactory.autowireBean(jobInstance)
//        return jobInstance
//    }
//}