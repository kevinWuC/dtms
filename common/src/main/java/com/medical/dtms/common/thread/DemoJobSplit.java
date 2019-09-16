package com.medical.dtms.common.thread;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : Hejinsheng
 * @date
 * @Description: 大任务拆成多个子任务，并行执行
 */
public class DemoJobSplit {
    // 根据实际情况调整
    private static int corePoolSize = 10;

    private static ThreadPoolExecutor executor  = new ThreadPoolExecutor(corePoolSize, corePoolSize+1, 10l, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000));

    public static void execute(List<Runnable> jobs){
        jobs.forEach(job -> executor.execute(job));
    }
}
