package com.lgq.tortoise.practices.runtimep.dynamicproxy.practices.e5;

import java.lang.reflect.Method;

/**
 * @author lgq
 */
public interface InvocationHandler {
    /**
     * 用户自定义逻辑
     *
     * @param proxy  生成的代理对象
     * @param method 被执行方法
     * @param args   被执行方法参数
     * @return
     * @throws Throwable
     */
    Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
