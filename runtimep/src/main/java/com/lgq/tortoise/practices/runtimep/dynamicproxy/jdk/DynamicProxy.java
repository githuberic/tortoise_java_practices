package com.lgq.tortoise.practices.runtimep.dynamicproxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author lgq
 */
public class DynamicProxy {
    public static void main(String[] args) {
        //设置这个值，在程序运行完成后，可以生成代理类
        //System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        IHello hello = (IHello) new LogInvocationHandler().bind(new Hello());
        hello.sayHello("lgq");
    }
}

interface IHello {
    String sayHello(String str);
}

class Hello implements IHello {
    @Override
    public String sayHello(String str) {
        return "HelloImp" + str;
    }
}

/**
 * 实现一个InvocationHandler，方法调用会被转发到该类的invoke()方法。
 */
class LogInvocationHandler implements InvocationHandler {
    Object originalObj;

    Object bind(Object originalObj) {
        this.originalObj = originalObj;
        return Proxy.newProxyInstance(originalObj.getClass().getClassLoader(), originalObj.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("sayHello".equalsIgnoreCase(method.getName())) {
            System.out.println("You said:" + Arrays.toString(args));
        }
        return method.invoke(originalObj, args);
    }
}
