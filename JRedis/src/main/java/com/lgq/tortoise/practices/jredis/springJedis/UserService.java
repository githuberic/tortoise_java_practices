package com.lgq.tortoise.practices.jredis.springJedis;

/**
 * @author lgq
 */
public interface UserService {
    /**
     * CRUD 之   查询
     *
     * @param id id
     * @return 用户
     */
    User getUser(long id);

    /**
     * CRUD 之  新增/更新
     *
     * @param user 用户
     */
    User saveUser(final User user);

    /**
     * CRUD 之 删除
     *
     * @param id id
     */

    void deleteUser(long id);


    /**
     * 删除全部
     */
    public void deleteAll();
}
