package com.lgq.tortoise.practices.jredis.springJedis;

import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

/**
 * @author lgq
 */
@Service
@CacheConfig(cacheNames = "userCache")
public class UserServiceImplWithAnno implements UserService {
    public static final String USER_UID_PREFIX = "'userCache:'+";

    /**
     * CRUD 之  新增/更新
     *
     * @param user 用户
     */
    @CachePut(key = USER_UID_PREFIX + "T(String).valueOf(#user.uid)")
    @Override
    public User saveUser(final User user) {
        //保存到数据库
        //返回值，将保存到缓存
        System.out.println("user : save to redis");
        return user;
    }

    /**
     * 带条件缓存
     *
     * @param user 用户
     * @return 用户
     */
    @CachePut(key = "T(String).valueOf(#user.uid)", condition = "#user.uid>1000")
    public User cacheUserWithCondition(final User user) {
        //保存到数据库
        //返回值，将保存到缓存
        System.out.println("user : save to redis");
        return user;
    }

    /**
     * CRUD 之   查询
     *
     * @param id id
     * @return 用户
     */
    @Cacheable(key = USER_UID_PREFIX + "T(String).valueOf(#id)")
    @Override
    public User getUser(final long id) {
        //如果缓存没有,则从数据库中加载
        System.out.println("user : is null");
        return null;
    }

    /**
     * CRUD 之 删除
     *
     * @param id id
     */

    @CacheEvict(key = USER_UID_PREFIX + "T(String).valueOf(#id)")
    @Override
    public void deleteUser(long id) {

        //从数据库中删除
        System.out.println("delete  User:"+id);
    }

    /**
     * 删除userCache中的全部缓存
     */
    @CacheEvict(value = "userCache", allEntries = true)
    public void deleteAll() {

    }

    /**
     * 一个方法上，加上三类cache处理
     */
    @Caching(cacheable = @Cacheable(key = "'userCache:'+ #uid"),
            put = @CachePut(key = "'userCache:'+ #uid"),
            evict = {
                    @CacheEvict(key = "'userCache:'+ #uid"),
                    @CacheEvict(key = "'addressCache:'+ #uid"),
                    @CacheEvict(key = "'messageCache:'+ #uid")
            }
    )
    public User updateRef(String uid) {
        //....业务逻辑
        return null;
    }
}
