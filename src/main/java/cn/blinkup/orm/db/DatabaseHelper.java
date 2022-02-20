package cn.blinkup.orm.db;

import cn.blinkup.orm.utils.PropsUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 * @author joe
 * 数据库基本操作类
 */
public class DatabaseHelper {

    private static String USERNAME;
    private static String PASSWORD;
    private static String URL;
    private static String DRIVER;

    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private static Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);

    public static Connection getConnection(){
        Connection connection;
         {
            Properties conf = PropsUtil.loadProps("config.properties");
            DRIVER = conf.getProperty("jdbc.driver");
            URL = conf.getProperty("jdbc.url");
            USERNAME = conf.getProperty("jdbc.username");
            PASSWORD = conf.getProperty("jdbc.password");
        }
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
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

    public static<T> List<T> queryEntityList(Class<T> entityClass, String sql, Object...params){
        List<T> entityList = null;
        Connection connection = getConnection();
        try{
            entityList = QUERY_RUNNER.query(connection, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException sqlException) {
            logger.error("sql错误：" + sqlException);
        } finally {
          closeConnection(connection);
        }
        return entityList;
    }
}
