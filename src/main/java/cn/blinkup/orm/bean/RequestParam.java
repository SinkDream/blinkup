package cn.blinkup.orm.bean;

import cn.blinkup.orm.utils.CastUtil;

import java.util.Map;

/**
 * @author zhangguosheng
 * 请求参数对象
 */
public class RequestParam {

    private Map<String, Object> paramMap;

    public RequestParam(Map<String, Object> paramMap){
        this.paramMap = paramMap;
    }

    public long getLongValue(String name){
        return CastUtil.castLong(paramMap.get(name));
    }

    public Map<String, Object> getParamMap(){
        return paramMap;
    }
}
