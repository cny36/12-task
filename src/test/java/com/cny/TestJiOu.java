package com.cny;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : chennengyuan
 */
public class TestJiOu {

    private static int num = 1;
    private static Lock lock = new ReentrantLock();
    private static Condition jiCondition = lock.newCondition();
    private static Condition ouCondition = lock.newCondition();


    /**
     * 奇数偶数交替打印
     *
     * @param args
     */
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (num < 100) {
                lock.lock();
                if (num % 2 == 0) {
                    try {
                        jiCondition.await();
                    } catch (InterruptedException e) {
                    }
                }
                System.out.println(Thread.currentThread().getName() + " - " + num);
                num++;
                ouCondition.signal();
                lock.unlock();
            }
        });
        t1.setName("奇数");

        Thread t2 = new Thread(() -> {
            while (num < 100) {
                lock.lock();
                if (num % 2 == 1) {
                    try {
                        ouCondition.await();
                    } catch (InterruptedException e) {
                    }
                }
                System.out.println(Thread.currentThread().getName() + " - " + num);
                num++;
                jiCondition.signal();
                lock.unlock();
            }
        });
        t2.setName("偶数");

        t1.start();
        t2.start();
    }


}
