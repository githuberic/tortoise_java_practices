package com.lgq.tortoise.practices.runtimep.dynamicproxy.example.e1.jdk;


import com.lgq.tortoise.practices.runtimep.dynamicproxy.example.e1.CPU;
import com.lgq.tortoise.practices.runtimep.dynamicproxy.example.e1.Device;

import java.lang.reflect.Proxy;

/**
 * @author lgq
 */
public class App {
    public static void main(String[] args) throws Exception {
        CPU cpu = new CPU();
        Computer computer = new Computer();
        computer.setDevice(cpu);

        //获取代理接口实例
        Device device = (Device) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                cpu.getClass().getInterfaces(),
                computer);
        device.run();
    }
}
