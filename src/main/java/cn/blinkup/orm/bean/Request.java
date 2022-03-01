package cn.blinkup.orm.bean;

import java.util.Objects;

/**
 * @author zhangguosheng
 */
public class Request {

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求路径
     */
    private String path;

    public Request(String method, String path) {
        this.method = method;
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o){
             return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Request request = (Request) o;
        return Objects.equals(method, request.method) && Objects.equals(path, request.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path);
    }

}
