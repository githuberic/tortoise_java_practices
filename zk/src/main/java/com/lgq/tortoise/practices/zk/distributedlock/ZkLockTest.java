package com.lgq.tortoise.practices.zk.distributedlock;

import com.lgq.tortoise.practices.zk.util.FutureTaskScheduler;
import org.junit.Test;

/**
 * @author lgq
 */
public class ZkLockTest {
    int count = 0;

    @Test
    public void testLock() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            FutureTaskScheduler.add(() -> {
                ZkLock lock = new ZkLock();
                lock.lock();

                for (int j = 0; j < 10; j++) {
                    count++;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("count = " + count);
                lock.unlock();
            });
        }

        Thread.sleep(Integer.MAX_VALUE);
    }
}
