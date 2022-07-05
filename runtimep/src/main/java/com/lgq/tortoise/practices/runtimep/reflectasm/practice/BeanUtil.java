package com.lgq.tortoise.practices.runtimep.reflectasm.practice;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 通过reflectASM实现对象拷贝
 *
 * @author lgq
 */
public class BeanUtil {
    /**
     * 大小写可以忽略
     * 下划线 _ 被忽略
     * NULL值和空字符串不会覆盖新值
     *
     * @param source
     * @param target
     * @param <T>
     * @return
     */
    public static <T> T copyPropertiesIgnoreCase(Object source, Object target) {
        Map<String, Field> sourceMap = CacheFieldMap.getFieldMap(source.getClass());
        CacheFieldMap.getFieldMap(target.getClass()).values().forEach((it) -> {
            Field field = sourceMap.get(it.getName().toLowerCase().replace("_", ""));
            if (field != null) {
                it.setAccessible(true);
                field.setAccessible(true);
                try {
                    //忽略null和空字符串
                    if (field.get(source) != null)
                        it.set(target, field.get(source));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        return (T) target;
    }

    private static class CacheFieldMap {
        private static Map<String, Map<String, Field>> cacheMap = new HashMap<>();

        private static Map<String, Field> getFieldMap(Class clazz) {
            Map<String, Field> result = cacheMap.get(clazz.getName());
            if (result == null) {
                synchronized (CacheFieldMap.class) {
                    if (result == null) {
                        Map<String, Field> fieldMap = new HashMap<>();
                        for (Field field : clazz.getDeclaredFields()) {
                            fieldMap.put(field.getName().toLowerCase().replace("_", ""), field);
                        }
                        cacheMap.put(clazz.getName(), fieldMap);
                        result = cacheMap.get(clazz.getName());
                    }
                }
            }
            return result;
        }
    }
}
