package com.lgq.tortoise.practices.runtimep.dynamicproxy.practices.e4;

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
    public Object invoke(Method method, Object[] args) throws Throwable {
        before();

        Object res = method.invoke(target, args);

        after();

        return res;
    }

    private void before() {
        System.out.println("Print log(invoke before)>>>");
    }

    private void after() {
        System.out.println("Print log(after before)>>>");
    }
}
