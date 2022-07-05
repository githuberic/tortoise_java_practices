package com.lgq.tortoise.practices.runtimep.reflectasm.practice;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentMap;

/**
 * @author lgq
 */
public class ReflectAsmManager {
    private static final ConcurrentMap<Class, MethodAccess> localCache = Maps.newConcurrentMap();

    public static <F,T> void copyProperties(F from, T to) {
        MethodAccess fromMethodAccess = get(from.getClass());
        MethodAccess toMethodAccess = get(to.getClass());
        Field[] fromDeclaredFields = from.getClass().getDeclaredFields();
        for(Field field : fromDeclaredFields) {
            String name = field.getName();
            try {
                Object value = fromMethodAccess.invoke(from,  "get" + StringUtils.capitalize(name), null);
                toMethodAccess.invoke(to, "set" + StringUtils.capitalize(name), value);
            } catch (Exception e) {
                // 设置异常，可能会没有对应字段，忽略
            }
        }
    }

    public static MethodAccess get(Class clazz) {
        if(localCache.containsKey(clazz)) {
            return localCache.get(clazz);
        }

        MethodAccess methodAccess = MethodAccess.get(clazz);
        localCache.putIfAbsent(clazz, methodAccess);
        return methodAccess;
    }
}
