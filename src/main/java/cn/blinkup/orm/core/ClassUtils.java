package cn.blinkup.orm.core;

import cn.blinkup.orm.annotation.Controller;
import cn.blinkup.orm.annotation.Service;
import cn.blinkup.orm.config.ConfigHelper;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zhangguosheng
 * 类操作工具
 */
public class ClassUtils {

    private static final Set<Class<?>> CLASS_SET;

    static{
        String basePackage = ConfigHelper.getBasePackage();
        CLASS_SET = CustomizeClassLoader.getClassSet(basePackage);
    }

    /**
     * 获取字节码集合
     * @return 字节码集合
     */
    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if(clazz.isAnnotationPresent(Service.class)){
                classSet.add(clazz);
            }
        }
        return classSet;
    }

    /**
     * 获取被Controller注解的类
     * @return 被Controller注解的类
     */
    public static Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if(clazz.isAnnotationPresent(Controller.class)){
                classSet.add(clazz);
            }
        }
        return classSet;
    }

    /**
     * 获取Bean
     * @return Bean实例
     */
    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> beanClassSet = new HashSet<>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }
}
