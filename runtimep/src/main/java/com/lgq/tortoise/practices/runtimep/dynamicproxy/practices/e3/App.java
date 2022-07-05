package com.lgq.tortoise.practices.runtimep.dynamicproxy.practices.e3;


import com.lgq.tortoise.practices.runtimep.dynamicproxy.practices.Payable;

/**
 * @author lgq
 */
public class App {
    public static void main(String[] args) {
        Payable payable = (Payable) Proxy.newProxyInstance(new MyClassLoader(), Payable.class);
        payable.pay();
    }
}
