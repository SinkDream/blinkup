package cn.blinkup.orm.core;

import cn.blinkup.orm.annotation.Inject;
import cn.blinkup.orm.utils.ReflectionUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;

/**
 * @author zhangguosheng
 * 依赖注入助手类
 */
public class IocCore {

    static{
        //获取所有的Bean类与Bean实例之间的映射关系
        Map<Class<?>, Object> beanMap = BeanContainer.getBeanMap();
        if(CollectionUtils.isNotEmpty(Collections.singleton(beanMap))){
            beanMap.forEach((k, v) ->{
                //获取类的实例和成员变量
                Field[] beanFields = k.getDeclaredFields();
                if(ArrayUtils.isNotEmpty(beanFields)){
                    for (Field beanField : beanFields) {
                        if(beanField.isAnnotationPresent(Inject.class)){
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if(null != beanFieldInstance){
                                //通过反射初始化BeanField的Value
                                ReflectionUtil.setField(v, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            });
        }
    }
}
