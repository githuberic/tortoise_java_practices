package com.lgq.tortoise.practices.runtimep.dynamicproxy.practices.e1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author lgq
 */
public class LogInvocationHandler implements InvocationHandler {
    // 被代理对象
    private Object target;

    public LogInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();

        Object res = method.invoke(target, args);

        after();

        return res;
    }

    private void before() {
        System.out.println("Invoke before>>>");
    }

    private void after() {
        System.out.println("Invoke after>>>");
    }
}
