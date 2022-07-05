package com.lgq.tortoise.practices.jredis.jedispool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lgq
 */
public class JredisPoolBuilder {

    public static final int MAX_IDLE = 50;
    public static final int MAX_TOTAL = 50;
    private static JedisPool pool = null;

    static {
        //创建连接池
        buildPool();
        //预热连接池
        hotPool();
    }

    // 创建连接池
    private static JedisPool buildPool() {
        if (pool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_TOTAL);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(1000 * 10);
            // 在borrow一个jedis实例时，是否提前进行validate操作；
            // 如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);

            // new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
            pool = new JedisPool(config, "127.0.0.1", 6379, 10000);
        }
        return pool;
    }

    // 获取连接
    public synchronized static Jedis getJedis() {
        return pool.getResource();
    }

    // 连接池的预热
    public static void hotPool() {
        List<Jedis> minIdleJedisList = new ArrayList<>(MAX_IDLE);
        Jedis jedis = null;

        for (int i = 0; i < MAX_IDLE; i++) {
            try {
                jedis = pool.getResource();
                minIdleJedisList.add(jedis);
                jedis.ping();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
            }
        }

        for (int i = 0; i < MAX_IDLE; i++) {
            try {
                jedis = minIdleJedisList.get(i);
                jedis.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
            }
        }
    }
}
