package com.cny;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : chennengyuan
 */
public class DateTest {


    public static void main(String[] args) {
        LocalDateTime dataTimestamp0 = LocalDateTime.of(2024, 7, 23, 15, 1, 12);
        LocalDateTime dataTimestamp1 = LocalDateTime.of(2024, 7, 23, 22, 1, 12);

        LocalDateTime queryTimestamp0 = LocalDateTime.of(2024, 7, 23, 21, 0, 0);
        LocalDateTime queryTimestamp1 = LocalDateTime.of(2024, 7, 23, 22, 0, 1);
        LocalDateTime queryTimestamp2 = LocalDateTime.of(2024, 7, 23, 22, 5, 5);
        LocalDateTime queryTimestamp3 = LocalDateTime.of(2024, 7, 24, 6, 0, 0);
        LocalDateTime queryTimestamp4 = LocalDateTime.of(2024, 7, 24, 6, 5, 5);

        System.out.println(isOvernight(dataTimestamp0, queryTimestamp1));

        String date = "2024-07-23 08:02:20";
        System.out.println(LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }


    /**
     * 判断数据是否过夜
     * 比如持仓开始时间为2024-07-23 08:02:20，只要在2024-07-23 22:00:00之后还能查询到，则属于过夜
     * 比如持仓开始时间为2024-07-23 22:01:01，则属于过夜
     *
     * @param dataTimestamp  数据生成时间
     * @param queryTimestamp 当前查询时间
     * @return
     */
    public static boolean isOvernight(LocalDateTime dataTimestamp, LocalDateTime queryTimestamp) {
        // 定义晚上10点
        LocalDateTime nightStart = dataTimestamp.toLocalDate().atTime(22, 0, 0);

        // 检查查询时间是否在晚上10点之后
        boolean isAfterNightStart = queryTimestamp.isAfter(nightStart);

        // 检查数据生成时间是否在查询时间之前
        boolean isDataBeforeQuery = dataTimestamp.isBefore(queryTimestamp);

        return isAfterNightStart && isDataBeforeQuery;
    }


}
