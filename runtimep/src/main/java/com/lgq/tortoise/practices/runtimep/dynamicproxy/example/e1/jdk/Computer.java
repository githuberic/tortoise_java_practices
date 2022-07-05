package com.lgq.tortoise.practices.runtimep.dynamicproxy.example.e1.jdk;


import com.lgq.tortoise.practices.runtimep.dynamicproxy.example.e1.Device;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author lgq
 */
public class Computer implements InvocationHandler {
    private Device device;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Start...");
        //运用反射，动态执行代理方法，并返回方法执行结果
        Object result = method.invoke(device, args);
        System.out.println("End");
        return result;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
