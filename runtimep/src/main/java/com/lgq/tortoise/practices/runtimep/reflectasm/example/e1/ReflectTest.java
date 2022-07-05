package com.lgq.tortoise.practices.runtimep.reflectasm.example.e1;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import com.lgq.tortoise.practices.runtimep.example.ReflectCls;
import org.junit.Before;
import org.junit.Test;

/**
 * @author lgq
 */
public class ReflectTest {

    ReflectCls reflect;

    @Before
    public void setUp() {
        reflect = new ReflectCls();
    }

    @Test
    public void testInvokeMethod() {
        MethodAccess access = MethodAccess.get(ReflectCls.class);
        access.invoke(reflect, "print");
        Object obj = access.invoke(reflect, "print", "lgq");
        if (obj != null && obj instanceof String) {
            System.out.println(obj);
        }
        access.invoke(reflect, "print", "Hi", "lgq");
    }

    @Test
    public void testInvokeMethodV2() {
        MethodAccess access = MethodAccess.get(ReflectCls.class);
        int index = access.getIndex("print", int.class, int.class);
        access.invoke(reflect, index, 3, 5);
    }

    @Test
    public void testInvokeField() {
        FieldAccess fieldAccess = FieldAccess.get(reflect.getClass());
        fieldAccess.set(reflect, "value", 10);
        int value = (Integer) fieldAccess.get(reflect, "value");
        System.out.println(value);
    }

    /**
     * ReflectAsm反射来调用构造方法
     */
    @Test
    public void testInvokeConstructor() {
        ConstructorAccess<ReflectCls> constructorAccess = ConstructorAccess.get(ReflectCls.class);
        ReflectCls userService = constructorAccess.newInstance();
        System.out.println(userService);
    }

    /**
     * 查找方法的索引
     */
    @Test
    public void testIndex() {
        MethodAccess methodAccess = MethodAccess.get(reflect.getClass());
        int index = methodAccess.getIndex("print", int.class, int.class);
        System.out.println(index);
    }
    // https://blog.csdn.net/weixin_40017062/article/details/119987954
}
