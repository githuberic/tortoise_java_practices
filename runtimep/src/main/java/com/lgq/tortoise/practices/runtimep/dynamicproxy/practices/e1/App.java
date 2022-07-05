package com.lgq.tortoise.practices.runtimep.dynamicproxy.practices.e1;

import com.lgq.tortoise.practices.runtimep.dynamicproxy.practices.Alipay;
import com.lgq.tortoise.practices.runtimep.dynamicproxy.practices.MyClassLoader;
import com.lgq.tortoise.practices.runtimep.dynamicproxy.practices.Payable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author lgq
 */
public class App {
    public static void main(String[] args) {
        // 创建被代理对象
        Alipay target = new Alipay();

        // 实现自己的逻辑
        InvocationHandler logHandler = new LogInvocationHandler(target);

        // 代理对象
        Class[] classes = new Class[1];
        classes[0] = Payable.class;
        Payable proxy = (Payable) Proxy.newProxyInstance(new MyClassLoader(), classes, logHandler);

        // 调用代理对象目标方法
        proxy.pay();
    }
}
