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

    public static Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if(clazz.isAnnotationPresent(Controller.class)){
                classSet.add(clazz);
            }
        }
        return classSet;
    }

    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }
}
