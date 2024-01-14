package com.cny.elasticJob_java;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;

/**
 * @author : chennengyuan
 * 基于Java原生API实现SimpleJob
 */
@Slf4j
public class MySimpleJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("{}-分片总数为：{}, 当前执行的分片为：{}", LocalTime.now(), shardingContext.getShardingTotalCount(), shardingContext.getShardingItem());
        switch (shardingContext.getShardingItem()){
            case 0:
                log.info("负责处理北京的数据");
                break;
            case 1:
                log.info("负责处理深圳的数据");
                break;
        }
    }
}
