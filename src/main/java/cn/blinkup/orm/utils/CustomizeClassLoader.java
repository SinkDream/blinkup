package cn.blinkup.orm.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * @author zhangguosheng
 * 自定义类加载器
 */
public final class CustomizeClassLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomizeClassLoader.class);

    /**
     * 获取当前线程的类加载器
     * @return
     */
    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 判断类是否已经初始化
     * @param className 全限定类名
     * @param initialized 是否初始化
     * @return 符合条件的类
     */
    public static Class<?> loadClass(String className, boolean initialized){
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className, initialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("加载类失败", e);
            e.printStackTrace();
        }
        return clazz;
    }

    /**
     * 获取指定包名下所有的类
     * @param packageName
     * @return
     */
    public static Set<Class<?>> getClassSet(String packageName){
        //todo
        return null;
    }
}
