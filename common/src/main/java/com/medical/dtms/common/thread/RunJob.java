package com.medical.dtms.common.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author : Hejinsheng
 * @date 19-2-16
 * @Description:
 */
@Slf4j
public class RunJob {

    public static void main(String[] args) throws InterruptedException {

        Long s = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(5);

        SubJob job1 = new SubJob("任务1",latch);
        SubJob job2 = new SubJob("任务2",latch);
        SubJob job3 = new SubJob("任务3",latch);
        SubJob job4 = new SubJob("任务4",latch);
        SubJob job5 = new SubJob("任务5",latch);

        List list = Arrays.asList(job1,job2,job3,job4,job5);

        DemoJobSplit.execute(list);

        latch.await();

        log.info("任务执行总耗时：{}",(System.currentTimeMillis()-s));
    }
}
