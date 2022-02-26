package cn.blinkup.orm.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author zhangguosheng
 * Bean工具类，获取bean对象的属性和内容
 */
public class BeanContainer {

    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        Set<Class<?>> beanClassSet = ClassUtils.getBeanClassSet();
        for (Class<?> clazz : beanClassSet) {
            Object object = ReflectionUtil.newInstance(clazz);
            BEAN_MAP.put(clazz, object);
        }
    }

    /**
     * 获取Bean映射列表
     * @return
     */
    public static Map<Class<?>, Object> getBeanMap(){
        return BEAN_MAP;
    }

    @SuppressWarnings("unckecked")
    public static <T> T getBean(Class<T> clazz){
        if(!BEAN_MAP.containsKey(clazz)){
            throw new RuntimeException("未获取到相应类的Bean" + clazz);
        }
        return (T) BEAN_MAP.get(clazz);
    }
}
