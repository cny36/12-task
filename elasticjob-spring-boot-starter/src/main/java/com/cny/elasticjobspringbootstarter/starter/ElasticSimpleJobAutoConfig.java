package com.cny.elasticjobspringbootstarter.starter;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Map;

/**
 * @author : chennengyuan
 */
@Configuration
@ConditionalOnBean(CoordinatorRegistryCenter.class)
@AutoConfigureAfter(ZookeeperAutoConfig.class)
public class ElasticSimpleJobAutoConfig {

    @Autowired
    private CoordinatorRegistryCenter zkCenter;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void initSimpleJob() {
        //1.获取到带注解的bean信息
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(ElasticSimpleJob.class);
        //2.遍历 逐一注册
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            //3.获取到注解的相关配置信息
            Object instance = entry.getValue();
            ElasticSimpleJob annotation = instance.getClass().getAnnotation(ElasticSimpleJob.class);
            String jobName = annotation.jobName();
            String cron = annotation.cron();
            int shardingCount = annotation.shardingCount();
            boolean overWrite = annotation.overWrite();
            boolean eventTracking = annotation.eventTracking();
            //3.实现相关配置
            JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder(jobName, cron, shardingCount).build();
            SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(jobCoreConfiguration, instance.getClass().getCanonicalName());
            LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(simpleJobConfiguration).overwrite(overWrite).build();
            //4.让注解生效
            if(eventTracking){
                new JobScheduler(zkCenter, liteJobConfiguration, new JobEventRdbConfiguration(dataSource)).init();
            } else {
                new JobScheduler(zkCenter, liteJobConfiguration).init();
            }
        }
    }
}
