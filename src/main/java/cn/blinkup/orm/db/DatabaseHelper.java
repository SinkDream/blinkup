package cn.blinkup.orm.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author joe
 * 帮助类
 */
public class DatabaseHelper {

    private static final String USER_NAME = "";
    private static final String PASSWORD = "";
    private static final String URL = "";

    public static Connection getConnection(){
        Connection connection;
        try {
            Class.forName("com.mysql.");
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeConnection(Connection connection){
        if(null != connection){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
