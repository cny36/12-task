package com.cny.springTask;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author : chennengyuan
 * 启用集群任务，任务会重复执行
 * 可考虑用分布式锁控制
 */
@Slf4j
//@Service
public class SpringTask {

    @Scheduled(cron = "0/3 * * * * ?")
    public void sendMessage() {
        log.info("开始发送提醒消息");
    }
}
