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
        List<User> users = DatabaseHelper.queryEntityList(User.class, sql, null);

//        Connection connection = null;
//        List<User> userList = new ArrayList<>();
//        try{
//            String sql = "select * from user";
//            connection = DatabaseHelper.getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()){
//                User user = new User();
//                user.setId(resultSet.getLong("id"));
//                user.setName(resultSet.getString("name"));
//                user.setPhone(resultSet.getString("phone"));
//                user.setEmail(resultSet.getString("email"));
//                userList.add(user);
//            }
//        } catch (SQLException sqlException) {
//            logger.error("sql错误：" + sqlException);
//        } finally {
//          if(null != connection){
//              try {
//                  connection.close();
//              } catch (SQLException sqlException) {
//                  logger.error("关闭数据库连接错误：" + sqlException);
//              }
//          }
//        }
        return users;
    }
}
