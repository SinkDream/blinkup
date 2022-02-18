package cn.blinkup.orm.db;

import java.sql.*;

/**
 * @author joe
 * 数据库基本操作类
 */
public class DatabaseHelper {

    private static final String USER_NAME = "root";
    private static final String PASSWORD = "123456";
    private static final String URL = "jdbc:mysql://localhost:3306/aktest?useUnicode=true&characterEncoding=utf-8";

    public static Connection getConnection(){
        Connection connection;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
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

    public void Query(String sql, String ... args){
        Connection conn = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setString(i + 1, args[i]);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String a = resultSet.getString("name");
                System.out.println(resultSet.getString("contract"));
                System.out.println(resultSet.getString("email"));
                System.out.println(a);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
