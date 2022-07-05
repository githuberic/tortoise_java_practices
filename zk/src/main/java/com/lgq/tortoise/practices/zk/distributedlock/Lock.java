package com.lgq.tortoise.practices.zk.distributedlock;

/**
 * @author lgq
 */
public interface Lock {
    boolean lock() throws Exception;

    boolean unlock();
}
