package cn.blinkup.orm.bean;

import java.lang.reflect.Method;

/**
 * @author zhangguosheng
 */
public class Handler {

    private final Class<?> controllerClass;

    private final Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod){
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
