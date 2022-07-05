package com.lgq.tortoise.practices.jredis.jedispool;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @author lgq
 */
public class JredisPoolTest {
    public static final int NUM = 200;
    public static final String ZSET_KEY = "zset1";

    //测试删除
    @Test
    public void testDel() {
        Jedis redis = null;
        try {
            redis = JredisPoolBuilder.getJedis();
            redis.del(ZSET_KEY);
        } finally {
            //使用后一定关闭，还给连接池
            if (redis != null) {
                redis.close();
            }
        }
    }
}
