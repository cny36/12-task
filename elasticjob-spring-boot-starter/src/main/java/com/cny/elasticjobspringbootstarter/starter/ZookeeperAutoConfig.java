package com.cny.elasticjobspringbootstarter.starter;

import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : chennengyuan
 * 自动装配配置信息
 */
@Configuration
@ConditionalOnProperty("elasticjob.zookeeper.server-lists")
@EnableConfigurationProperties(ZookeeperProperties.class)
public class ZookeeperAutoConfig {

    @Autowired
    private ZookeeperProperties zookeeperProperties;

    @Bean(initMethod = "init")
    public CoordinatorRegistryCenter coordinatorRegistryCenter() {
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(zookeeperProperties.getServerLists(), zookeeperProperties.getNamespace());
        ZookeeperRegistryCenter zookeeperCenter = new ZookeeperRegistryCenter(zookeeperConfiguration);
        return zookeeperCenter;
    }

}
