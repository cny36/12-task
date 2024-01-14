package com.cny;

import com.cny.elasticJob_java.MyDataflowJob;
import com.cny.elasticJob_java.MySimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        //启动ElasticJob任务
        //new JobScheduler(zookeeperCenter(), simpleJobConfiguration()).init();
        //new JobScheduler(zookeeperCenter(), dataflowJobConfiguration()).init();

    }


    //初始化注册中心，由其来帮助管理分片的信息
    public static CoordinatorRegistryCenter zookeeperCenter() {
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration("192.168.247.5:2181", "java-simpleJob");
        ZookeeperRegistryCenter zookeeperCenter = new ZookeeperRegistryCenter(zookeeperConfiguration);
        zookeeperCenter.init();
        return zookeeperCenter;
    }

    //配置Job相关信息
    public static LiteJobConfiguration simpleJobConfiguration() {
        //1.配置任务名称、corn、分片总数
        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder("mySimpleJob", "0/5 * * * * ?", 2).build();
        //2.绑定任务和配置
        SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(jobCoreConfiguration, MySimpleJob.class.getCanonicalName());
        //3.设置Job的跟配置
        LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration
                .newBuilder(simpleJobConfiguration)
                .overwrite(true)
                .build();
        return liteJobConfiguration;

    }

    public static LiteJobConfiguration dataflowJobConfiguration() {
        //1.配置任务名称、corn、分片总数
        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder("myDataFlowJob", "0/5 * * * * ?", 2).build();
        //2.绑定任务和配置
        DataflowJobConfiguration dataflowJobConfiguration = new DataflowJobConfiguration(jobCoreConfiguration, MyDataflowJob.class.getCanonicalName(), true);
        //3.设置Job的跟配置
        LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration
                .newBuilder(dataflowJobConfiguration)
                .overwrite(true)
                .build();
        return liteJobConfiguration;

    }

}
