package com.cny.elasticJob_java;

import com.cny.entity.CarOrder;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : chennengyuan
 */
@Slf4j
public class MyDataflowJob implements DataflowJob<CarOrder> {

    private static List<CarOrder> carOrderList = selectCarOrderList();

    @Override
    public List<CarOrder> fetchData(ShardingContext shardingContext) {
        //1.模拟抓取数据
        //分片处理订单的规则
        //每个分片按照处理规则来处理不同的打车订单
        //订单该分给哪个分片？这个关系如下：
        //订单号%分片总数 == 当前分片项，那么就由该分片负责的服务器来处理
        List<CarOrder> orderList = carOrderList.stream().filter(order -> order.getStatus() == 0)
                .filter(order -> order.getId() % shardingContext.getShardingTotalCount() == shardingContext.getShardingItem())
                .collect(Collectors.toList());
        List<CarOrder> subOrderList = null;
        if (orderList != null && orderList.size() > 0) {
            subOrderList = orderList.subList(0, 10);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("{} - 当前分片:{} - 抓取的数据:{}", LocalTime.now(), shardingContext.getShardingItem(), subOrderList);
        }
        return subOrderList;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<CarOrder> list) {
        log.info("{} - 当前分片:{} - 正在模拟匹配派发打车订单", LocalTime.now(), shardingContext.getShardingItem());

        list.forEach(order -> {
            if (order.getStatus() == 0) {
                order.setStatus(1);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("完成对订单：{}的处理", order.getId());
            }
        });
    }

    /**
     * 模拟数据库中的数据
     *
     * @return
     */
    public static List<CarOrder> selectCarOrderList() {
        List<CarOrder> list = new ArrayList<>(200);
        for (int i = 0; i < 200; i++) {
            list.add(new CarOrder(i + 1, 0));
        }
        return list;
    }
}
