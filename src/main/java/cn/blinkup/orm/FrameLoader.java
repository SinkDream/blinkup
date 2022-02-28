package cn.blinkup.orm;

import cn.blinkup.orm.utils.*;

/**
 * @author zhangguosheng
 * 框架加载器
 */
public class FrameLoader {
    /**
     * 初始化
     */
    public static void init(){
        Class<?>[] classArray = {ClassUtils.class, BeanContainer.class, IocHelper.class, ControllerHelper.class};
        for (Class<?> clazz : classArray) {
            CustomizeClassLoader.loadClass(clazz.getName(), false);
        }
    }

}
