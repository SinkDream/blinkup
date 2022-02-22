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

    public boolean createUser(Map<String, Object> userParamMap){
        return DatabaseHelper.insertEntity(User.class, userParamMap);
    }

    public boolean deleteUser(long userId){
        return DatabaseHelper.deleteEntity(User.class, userId);
    }

    public boolean updateUser(long userId, Map<String, Object> userParamMap){
        return DatabaseHelper.updateEntity(User.class, userId, userParamMap);
    }

    public User getUserDetail(long userId){
        String sql = "select * from user";
        return DatabaseHelper.queryEntity(User.class, sql, null);
    }

    public List<User> getUserList(Map<String, Object> listParamMap){
        String sql = "select * from user";
        List<User> users = DatabaseHelper.queryEntityList(User.class, sql);
        return users;
    }
}
