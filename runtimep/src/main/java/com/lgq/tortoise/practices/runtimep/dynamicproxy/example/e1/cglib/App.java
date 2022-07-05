package com.lgq.tortoise.practices.runtimep.dynamicproxy.example.e1.cglib;

import com.lgq.tortoise.practices.runtimep.dynamicproxy.example.e1.CPU;
import net.sf.cglib.proxy.Enhancer;

/**
 * @author lgq
 */
public class App {
    public static void main(String[] args) throws Exception {
        Enhancer enhancer = new Enhancer();
        // 设置要代理超类
        enhancer.setSuperclass(CPU.class);
        // 设置回调处理类
        enhancer.setCallback(new Computer());
        // 构建代理类
        CPU cpu = (CPU) enhancer.create();
        cpu.run();
    }
}
