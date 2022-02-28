package cn.blinkup.orm.core;

import cn.blinkup.orm.FrameLoader;
import cn.blinkup.orm.bean.Data;
import cn.blinkup.orm.bean.Handler;
import cn.blinkup.orm.bean.RequestParam;
import cn.blinkup.orm.bean.View;
import cn.blinkup.orm.config.ConfigHelper;
import cn.blinkup.orm.utils.CodecUtils;
import cn.blinkup.orm.utils.ReflectionUtil;
import cn.blinkup.orm.utils.StreamUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求转发器
 * @author zhangguosheng
 */
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        //初始化相关类
        FrameLoader.init();
        ServletContext servletContext = servletConfig.getServletContext();
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getJSPPath() + "*");
        //注册处理静态资源的默认Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getJSPPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod().toUpperCase();
        String requestMapping = req.getPathInfo();
        Handler handler = ControllerCore.getHandler(method, requestMapping);
        if(null != handler){
            Class<?> controllerClass = handler.getControllerClass();
            Object bean = BeanContainer.getBean(controllerClass);
            //创建请求参数对象
            Map<String, Object> paramMap = new HashMap<>();
            Enumeration<String> paramNames = req.getParameterNames();
            while(paramNames.hasMoreElements()){
                String paramName = paramNames.nextElement();
                String paramValue = req.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }
            String body = CodecUtils.decodeURL(StreamUtils.getString(req.getInputStream()));
            if(StringUtils.isNotEmpty(body)){
                String[] params = StringUtils.split(body, "&");
                if(ArrayUtils.isNotEmpty(params)){
                    for (String param : params) {
                        String[] split = StringUtils.split(param, "=");
                        String paramName = split[0];
                        String paramValue = split[1];
                        paramMap.put(paramName, paramValue);
                    }
                }
            }
            RequestParam requestParam = new RequestParam(paramMap);
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(bean, actionMethod, requestParam);
            if(result instanceof View){
                //todo 返回jsp页面
            }
            else if(result instanceof Data){
                //todo 返回JSON
            }

        }
    }
}
