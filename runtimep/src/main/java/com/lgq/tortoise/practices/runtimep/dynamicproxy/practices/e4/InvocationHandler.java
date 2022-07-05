package com.lgq.tortoise.practices.runtimep.dynamicproxy.practices.e4;

import java.lang.reflect.Method;

/**
 * @author lgq
 */
public interface InvocationHandler {
    /**
     * 用户自定义逻辑
     *
     * @param m     被执行方法
     * @param args  被执行方法参数
     * @return
     * @throws Throwable
     */
    Object invoke(Method m, Object[] args) throws Throwable;
}
