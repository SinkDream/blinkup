package cn.blinkup.orm.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 切面代理类
 * @author joe
 */
public class AspectProxy implements Proxy{

    private static final Logger logger = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public Object doProxy(ProxyChain proxyChain) {
        Object result = null;
        Class<?> clazz = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] methodParams = proxyChain.getMethodParams();
        begin();
        try {
            if(intercept(clazz, method, methodParams)){
                before(clazz, method, methodParams);
                result = proxyChain.doProxyChain();
                after(clazz, method, methodParams, result);
            }else{
                result = proxyChain.doProxyChain();
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            error(clazz, method, methodParams);
            logger.error("proxy error:" + throwable);
        } finally {
            end();
        }
        return result;
    }

    public void begin(){

    }

    public boolean intercept(Class<?> clazz, Method method, Object[] params) throws Throwable{
        return true;
    }

    public void before(Class<?> clazz, Method method, Object[] params) throws Throwable{

    }

    public void after(Class<?> clazz, Method method, Object[] params, Object result) throws Throwable{

    }

    public void end(){

    }

    public void error(Class<?> clazz, Method method, Object[] params) {

    }
}
