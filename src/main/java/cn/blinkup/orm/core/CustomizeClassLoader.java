package cn.blinkup.orm.core;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author zhangguosheng
 * 自定义类加载器
 */
public final class CustomizeClassLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomizeClassLoader.class);

    /**
     * 获取当前线程的类加载器
     * @return 当前线程的类加载器
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
     * @param packageName 包名
     * @return 包名下所有的类的集合
     */
    public static Set<Class<?>> getClassSet(String packageName){
        Set<Class<?>> classSet = new HashSet<>();
        try{
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()){
                URL url = urls.nextElement();
                if(null != url){
                    String protocol = url.getProtocol();
                    if("file".equals(protocol)){
                        //过滤空格
                        String packagePath = url.getPath().replaceAll("%20", "");
                        addClass(classSet, packagePath, packageName);
                    } else if("jar".equals(protocol)){
                        JarURLConnection jarUrlConnection = (JarURLConnection) url.openConnection();
                        if(null != jarUrlConnection){
                            JarFile jarFile = jarUrlConnection.getJarFile();
                            if(null != jarFile){
                                Enumeration<JarEntry> jarEntries = jarFile.entries();
                                while (jarEntries.hasMoreElements()){
                                    JarEntry jarEntry = jarEntries.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if(jarEntryName.endsWith(".class")){
                                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/",  ".");
                                        doAddClass(classSet, className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classSet;
    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName){
        File[] files = new File(packagePath).listFiles(pathname -> (pathname.isFile() && pathname.getName().endsWith(".class")) || pathname.isDirectory());
        if(null == files){
            throw new RuntimeException();
        }
        for (File file : files) {
            String fileName = file.getName();
            if(file.isFile()){
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if(StringUtils.isNotEmpty(packageName)){
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);
            } else {
                String subPackagePath = fileName;
                if(StringUtils.isNotEmpty(packagePath)){
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if(StringUtils.isNotEmpty(subPackageName)){
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet, subPackagePath, subPackageName);
            }
        }

    }

    private static void doAddClass(Set<Class<?>> classSet, String className){
        Class<?> clazz = loadClass(className, false);
        classSet.add(clazz);
    }
}
