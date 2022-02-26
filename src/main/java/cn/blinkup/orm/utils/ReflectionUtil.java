package cn.blinkup.orm.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author zhangguosheng
 */
public class ReflectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 反射获取实例
     * @param clazz
     * @return
     */
    public static Object newInstance(Class<?> clazz){
        Object instance = null;
        try{
            instance = clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            LOGGER.error("反射创建实例失败" + e.getMessage());
        }
        return instance;
    }

    /**
     * 反射调用方法
     * @param object
     * @param method
     * @param args
     * @return
     */
    public static Object invokeMethod(Object object, Method method, Object...args){
        Object result = null;
        try{
            method.setAccessible(true);
            result = method.invoke(object, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("反射调用方法失败" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 设置成员变量的值
     * @param object
     * @param field
     * @param value
     */
    public static void setField(Object object, Field field, Object value){
        try {
            field.setAccessible(true);
            field.set(object, value);
        } catch (IllegalAccessException e) {
            LOGGER.error("设置属性失败" + e.getMessage());
            e.printStackTrace();
        }
    }
}
