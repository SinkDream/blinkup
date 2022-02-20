package cn.blinkup.orm.service;

import cn.blinkup.orm.db.DatabaseHelper;
import cn.blinkup.orm.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author joe
 * 用户服务
 */
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

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

    public List<User> getUserList(Map<String, Object> listParamMap){
        String sql = "select * from user";
        List<User> users = DatabaseHelper.queryEntityList(User.class, sql);
        return users;
    }
}
