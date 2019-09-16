/**
 * BEYONDSOFT.COM INC
 */
package com.medical.dtms.common.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 线程池管理
 * @author yulijun
 * @version $Id: RobTaskThreadPool.java, v 0.1 2017年7月11日 下午7:42:47 yulijun Exp $
 */
public class TaskThreadPoolExecutor extends ThreadPoolExecutor {

    /** 线程池大小 */
    private static final int  COREPOOLSIZE          = 10;
    /** 释放等待时长 */
    private static final long KEEPALIVETIME         = 60;
    /** 阻塞队列数量 */
    private static final int  BLOCKINGQUEUECAPACITY = 100;

    public static TaskThreadPoolExecutor getInstance() {
        if (instance == null) {
            synchronized (TaskThreadPoolExecutor.class) {
                if (instance == null) {
                    instance = new TaskThreadPoolExecutor();
                }
            }
        }
        return instance;
    }

    private TaskThreadPoolExecutor() {
        super(COREPOOLSIZE, COREPOOLSIZE, KEEPALIVETIME, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(BLOCKINGQUEUECAPACITY));
    }

    private static TaskThreadPoolExecutor instance;

    public void excuteTask(Runnable run) {
        instance.execute(run);
    }

    /**
     * 当前活动线程数
     * @return
     */
    public int getActiveThreadNum() {
        return instance.getActiveCount();
    }

    /**
     * 检查线程池缓冲队列是否有空闲空间
     * @return
     */
    public boolean checkQueueHasFreeSpace() {
        int queueNum = instance.getQueue().size();
        if (queueNum < BLOCKINGQUEUECAPACITY && instance.getActiveCount() < COREPOOLSIZE) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 线程池是否占满
     * @return
     */
    public boolean isPoolFull() {
        int activeNum = instance.getActiveCount();
        if (activeNum >= COREPOOLSIZE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查线程池缓冲队列是否为空
     * @return
     */
    public boolean checkQueueIsEmpty() {
        return instance.getQueue().size() == 0;
    }
}
