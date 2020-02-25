package com.kayakwise.rabbit.producer.broker;

import ch.qos.logback.core.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @ClassName AsyncBaseQueue
 * @Description TODO
 * @Author Jaycrees
 * @Date 2020/2/26 0:02
 * @Version 1.0
 **/
@Slf4j
public class AsyncBaseQueue {

    public static final int THREAD_SIZE = Runtime.getRuntime().availableProcessors();

    public static final int QUEUE_SIZE = 10000;

    private static ExecutorService senderAsync = new ThreadPoolExecutor(THREAD_SIZE,
            THREAD_SIZE,
            60L,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(QUEUE_SIZE),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r);
                    t.setName("rabbitmq_client_async_sender");
                    return t;
                }
            },
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    log.error("async sender is error rejected,runnable:{},executor:{}", r, executor);
                }
            });

    public static void submit(Runnable runnable) {
        senderAsync.submit(runnable);
    }
}
