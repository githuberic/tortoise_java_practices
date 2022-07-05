package com.lgq.tortoise.practices.runtimep.reflectasm.example.e2;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author lgq
 */
public class MapToObjectIgnoreCaseTest {
    public static void main(String[] args) throws Exception {
        MerchInfo merchInfo = new MerchInfo();
        System.out.println(merchInfo);

        Map<Object, Object> map = new HashMap<>();
        map.put("fc_id", "TK");
        map.put("fc_merch_id", "123456");
        map.put("fc_merch_name", "中金");
        merchInfo = (MerchInfo) mapToObject(map, merchInfo.getClass());
        System.out.println(merchInfo);
    }

    public static Object mapToObject(Map<Object, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Map<Object, Object> map2 = new HashMap<>();
        Set<Object> keySet = map.keySet();
        for (Object key : keySet) {
            map2.put(key.toString().toLowerCase().replace("_", ""), map.get(key));
        }

        Object obj = beanClass.newInstance();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            if (map2.containsKey(field.getName().toLowerCase().replace("_", ""))) {
                field.set(obj, map2.get(field.getName().toLowerCase().replace("_", "")));
            }
        }
        return obj;
    }
}
