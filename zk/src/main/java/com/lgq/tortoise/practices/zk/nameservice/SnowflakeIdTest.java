package com.lgq.tortoise.practices.zk.nameservice;

import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author lgq
 */
public class SnowflakeIdTest {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws InterruptedException the interrupted exception
     */
    public static void main(String[] args) throws InterruptedException {
        long workerId = SnowflakeIdWorker.instance.getId();
        SnowflakeIdGenerator.instance.init(workerId);

        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        final HashSet idSet = new HashSet();
        Collections.synchronizedCollection(idSet);

        long start = System.currentTimeMillis();
        System.out.println(" start generate id *");

        int threadCount = 10;
        int turn = 50000;
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            threadPool.execute(() ->
            {
                for (long j = 0; j < turn; j++) {
                    long id = SnowflakeIdGenerator.instance.nextId();
                    synchronized (idSet) {
                        if (j % 10000 == 0) {
                            System.out.println("第 " + j + "个 id 为:" + id);
                        }
                        idSet.add(id);
                    }
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await(50000, TimeUnit.MICROSECONDS);
        threadPool.shutdown();
        //threadPool.awaitTermination(10, TimeUnit.SECONDS);
        long end = System.currentTimeMillis();
        System.out.println(" end generate id ");
        System.out.println("* cost " + (end - start) + " ms!");
    }
}
