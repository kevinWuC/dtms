package com.medical.dtms.common.thread;

import java.util.concurrent.CountDownLatch;

/**
 * @author : Hejinsheng
 * @date
 * @Description:
 */
public class SubJob implements Runnable {
    String statsName;
    CountDownLatch latch;

    public SubJob(String statsName, CountDownLatch latch) {
        this.statsName = statsName;
        this.latch = latch;
    }

    public void run() {
        try {
            System.out.println(statsName+ " do stats begin at "+ System.currentTimeMillis());
            //模拟任务执行时间
            Thread.sleep(10000);
            System.out.println(statsName + " do stats complete at "+ System.currentTimeMillis());
            latch.countDown();//单次任务结束，计数器减一
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
