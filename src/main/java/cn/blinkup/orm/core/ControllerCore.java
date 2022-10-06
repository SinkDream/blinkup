package cn.blinkup.orm.core;

import cn.blinkup.orm.annotation.MyGetMapping;
import cn.blinkup.orm.annotation.MyPostMapping;
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

    private static final String POST_REQUEST = "POST";
    private static final String GET_REQUEST = "GET";

    static{
        Set<Class<?>> controllerClassSet = ClassUtils.getControllerClassSet();
        if(CollectionUtils.isNotEmpty(controllerClassSet)){
            for (Class<?> clazz : controllerClassSet) {
                Method[] declaredMethods = clazz.getDeclaredMethods();
                if(ArrayUtils.isNotEmpty(declaredMethods)){
                    for (Method declaredMethod : declaredMethods) {
                        //处理POST请求
                        if(declaredMethod.isAnnotationPresent(MyPostMapping.class)){
                            MyPostMapping zgs = declaredMethod.getAnnotation(MyPostMapping.class);
                            Request request = new Request(POST_REQUEST, zgs.value());
                            Handler handler = new Handler(clazz, declaredMethod);
                            ACTION_MAP.put(request, handler);
                        }
                        //处理GET请求
                        if(declaredMethod.isAnnotationPresent(MyGetMapping.class)){
                            MyGetMapping zgs = declaredMethod.getAnnotation(MyGetMapping.class);
                            Request request = new Request(GET_REQUEST, zgs.value());
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
