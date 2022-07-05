package com.lgq.tortoise.practices.runtimep.dynamicproxy.example.e1.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author lgq
 */
public class Computer implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("Start...");
        //运用反射，动态执行代理方法，并返回方法执行结果
        Object result = methodProxy.invokeSuper(o, args);
        System.out.println("End");
        return result;
    }
}
