package com.cny.elasticjobspringbootstarter.starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author : chennengyuan
 */
@ConfigurationProperties(prefix = "elasticjob.zookeeper")
@Data
public class ZookeeperProperties {

    private String serverLists;

    private String namespace;
}
