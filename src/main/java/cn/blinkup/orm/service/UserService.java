package cn.blinkup.orm.service;

import cn.blinkup.orm.utils.PropsUtil;

import java.util.Map;
import java.util.Properties;

/**
 * @author joe
 * 用户服务
 */
public class UserService {

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        Properties conf = PropsUtil.loadProps("config.properties");
        DRIVER = conf.getProperty("jdbc.driver");
        URL = conf.getProperty("jdbc.url");
        USERNAME = conf.getProperty("jdbc.username");
        PASSWORD = conf.getProperty("jdbc.password");
    }

    public void createUser(Map<String, Object> userParamMap){
        //todo
    }

    public void deleteUser(long userId){
        //todo
    }

    public void updateUser(Map<String, Object> userParamMap){
        //todo
    }

    public void getUserDetail(long userId){
        //todo
    }

    public void getUserList(Map<String, Object> listParamMap){
        //todo
    }
}
