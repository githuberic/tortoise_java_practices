package com.lgq.tortoise.practices.jredis.springJedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lgq
 */
public class SpElBeanTest {
    @Test
    public void testSpElBean() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring-redis.xml");

        SpElBean spElBean = (SpElBean) ac.getBean("spElBean");

        /**
         * 演示算术运算符
         */
        System.out.println(" spElBean.getAlgDemoValue():=" + spElBean.getAlgDemoValue());

        /**
         * 演示 字符串运算符
         */
        System.out.println(" spElBean.getStringConcatValue():=" + spElBean.getStringConcatValue());


        /**
         * 演示 类型运算符
         */
        System.out.println(" spElBean.getRandomInt():=" + spElBean.getRandomInt());

        /**
         * 展示SpEl 上下文变量
         */
        spElBean.showContextVar();
    }
}
