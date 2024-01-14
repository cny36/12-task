package com.cny.elasticJob_springboot;

import com.cny.elasticjobspringbootstarter.starter.ElasticSimpleJob;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

/**
 * @author : chennengyuan
 */
@Slf4j
@ElasticSimpleJob(jobName = "myAnnotationSimpleJobs", cron = "0/5 * * * * ?", shardingCount = 2, overWrite = true, eventTracking = true)
@Component
public class MyAnnotationSimpleJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("{}-分片总数为：{}, 当前执行的分片为：{}", LocalTime.now(), shardingContext.getShardingTotalCount(), shardingContext.getShardingItem());
        switch (shardingContext.getShardingItem()) {
            case 0:
                log.info("自定义注解实现-负责处理北京的数据");
                break;
            case 1:
                log.info("自定义注解实现-负责处理深圳的数据");
                break;
        }
    }
}
