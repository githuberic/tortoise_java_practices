package com.lgq.tortoise.practices.jredis.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @author lgq
 */
public class StringE {
    @Test
    public void operateString() {
        Jedis jedis = new Jedis("localhost", 6379);
        //如果返回 pang 代表链接成功
        System.out.println("jedis.ping():" + jedis.ping());
        //设置key0的值 123456
        jedis.set("key0", "123456");
        //返回数据类型  string
        System.out.println("jedis.type(key0): " + jedis.type("key0"));
        //get key
        System.out.println("jedis.get(key0): " + jedis.get("key0"));
        // key是否存在
        System.out.println("jedis.exists(key0):" + jedis.exists("key0"));
        //返回key的长度
        System.out.println("jedis.strlen(key0): " + jedis.strlen("key0"));
        //返回截取字符串, 范围 0,-1 表示截取全部
        System.out.println("jedis.getrange(key0): " + jedis.getrange("key0", 0, -1));
        //返回截取字符串, 范围 1,4 表示从表示区间[1,4]
        System.out.println("jedis.getrange(key0): " + jedis.getrange("key0", 1, 4));

        //追加
        System.out.println("jedis.append(key0): " + jedis.append("key0", "appendStr"));
        System.out.println("jedis.get(key0): " + jedis.get("key0"));

        //重命名
        jedis.rename("key0", "key0_new");
        //判断key 是否存在
        System.out.println("jedis.exists(key0): " + jedis.exists("key0"));

        //批量插入
        jedis.mset("key1", "val1", "key2", "val2", "key3", "100");
        //批量取出
        System.out.println("jedis.mget(key1,key2,key3): " + jedis.mget("key1", "key2", "key3"));
        //删除
        System.out.println("jedis.del(key1): " + jedis.del("key1"));
        System.out.println("jedis.exists(key1): " + jedis.exists("key1"));
        //取出旧值 并set新值
        System.out.println("jedis.getSet(key2): " + jedis.getSet("key2", "value3"));
        //自增1 要求数值类型
        System.out.println("jedis.incr(key3): " + jedis.incr("key3"));
        //自增15 要求数值类型
        System.out.println("jedis.incrBy(key3): " + jedis.incrBy("key3", 15));
        //自减1 要求数值类型
        System.out.println("jedis.decr(key3): " + jedis.decr("key3"));
        //自减5 要求数值类型
        System.out.println("jedis.decrBy(key3): " + jedis.decrBy("key3", 15));
        //增加浮点类型
        System.out.println("jedis.incrByFloat(key3): " + jedis.incrByFloat("key3", 1.1));

        //返回0 只有在key不存在的时候才设置
        System.out.println("jedis.setnx(key3): " + jedis.setnx("key3", "existVal"));
        System.out.println("jedis.get(key3): " + jedis.get("key3"));// 3.1

        //只有key都不存在的时候才设置,这里返回 null
        System.out.println("jedis.msetnx(key2,key3): " + jedis.msetnx("key2", "exists1", "key3", "exists2"));
        System.out.println("jedis.mget(key2,key3): " + jedis.mget("key2", "key3"));

        //设置key 2 秒后失效
        jedis.setex("key4", 2, "2 seconds is no Val");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 2 seconds is no Val
        System.out.println("jedis.get(key4): " + jedis.get("key4"));


        jedis.set("key6", "123456789");
        //下标从0开始，从第三位开始,将新值覆盖旧值
        jedis.setrange("key6", 3, "abcdefg");
        //返回：123abcdefg
        System.out.println("jedis.get(key6): " + jedis.get("key6"));

        //返回所有匹配的key
        System.out.println("jedis.get(key*): " + jedis.keys("key*"));

        jedis.close();
    }
}
