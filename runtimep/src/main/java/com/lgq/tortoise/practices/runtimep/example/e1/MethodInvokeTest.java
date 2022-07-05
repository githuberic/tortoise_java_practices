package com.lgq.tortoise.practices.runtimep.example.e1;

import com.lgq.tortoise.practices.runtimep.example.ReflectCls;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Created by eric on 2019/11/10.
 *
 * @author lgq
 */
public class MethodInvokeTest {

    ReflectCls methodInvoke;
    Class clazz;

    @Before
    public void setup() {
        methodInvoke = new ReflectCls();
        clazz = methodInvoke.getClass();
    }

    @Test
    public void testMethodInvoke() {
        try {
            Method method = clazz.getMethod("print", int.class, int.class);
            method.invoke(methodInvoke, 10, 20);

            method = clazz.getMethod("print", String.class);
            Object object = method.invoke(methodInvoke, "Eric Liu");
            System.out.println(object);

            method = clazz.getMethod("print", String.class, String.class);
            method.invoke(methodInvoke, "Hi", "Eric");

            method = clazz.getMethod("print");
            method.invoke(methodInvoke);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testMethodInvokeV2() {
        try {
            Class clazz = Class.forName("com.lgq.runtimep.reflect.example.ReflectCls");
            ReflectCls methodInvoke = (ReflectCls) clazz.newInstance();

            Method method = clazz.getMethod("print");
            method.setAccessible(true);
            method.invoke(methodInvoke);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
