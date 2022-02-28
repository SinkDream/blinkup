package cn.blinkup.orm.core;

import cn.blinkup.orm.annotation.Zgs;
import cn.blinkup.orm.bean.Handler;
import cn.blinkup.orm.bean.Request;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author zhangguosheng
 */
public final class ControllerCore {

    private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();

    static{
        Set<Class<?>> controllerClassSet = ClassUtils.getControllerClassSet();
        if(CollectionUtils.isNotEmpty(controllerClassSet)){
            for (Class<?> clazz : controllerClassSet) {
                Method[] declaredMethods = clazz.getDeclaredMethods();
                if(ArrayUtils.isNotEmpty(declaredMethods)){
                    for (Method declaredMethod : declaredMethods) {
                        if(declaredMethod.isAnnotationPresent(Zgs.class)){
                            Zgs zgs = declaredMethod.getAnnotation(Zgs.class);
                            String pathMapping = zgs.value();
                            String method = zgs.method();
                            Request request = new Request(method, pathMapping);
                            Handler handler = new Handler(clazz, declaredMethod);
                            ACTION_MAP.put(request, handler);
                        }
                    }
                }
            }
        }
    }

    public static Handler getHandler(String requestMethod, String requestPath){
        Request request= new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }

}
