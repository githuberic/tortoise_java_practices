package com.lgq.tortoise.practices.runtimep.dynamicproxy.practices;

import java.util.concurrent.TimeUnit;

/**
 * @author lgq
 */
public class Alipay implements Payable {
    @Override
    public void pay() {
        try {
            // ...
            System.out.println("支付宝支付接口调用中......");

            //模拟方法调用延时
            TimeUnit.MILLISECONDS.sleep((long) (Math.random() * 3000));
            // ...
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
