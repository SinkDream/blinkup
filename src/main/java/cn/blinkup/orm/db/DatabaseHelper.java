package cn.blinkup.orm.db;

import cn.blinkup.orm.utils.PropsUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
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
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
    private static final Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);
    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL = new ThreadLocal<>();

    private static final String USERNAME;
    private static final String PASSWORD;
    private static final String URL;
    private static final String DRIVER;

    static {
        Properties conf = PropsUtil.loadProps("config.properties");
        DRIVER = conf.getProperty("jdbc.driver");
        URL = conf.getProperty("jdbc.url");
        USERNAME = conf.getProperty("jdbc.username");
        PASSWORD = conf.getProperty("jdbc.password");
    }

    public static Connection getConnection(){
        Connection connection = CONNECTION_THREAD_LOCAL.get();
        if(null == connection){
            try {
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection(){
        Connection connection = CONNECTION_THREAD_LOCAL.get();
        if(null != connection){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                CONNECTION_THREAD_LOCAL.remove();
            }
        }
    }

    public static<T> List<T> queryEntityList(Class<T> entityClass, String sql, Object...params){
        List<T> entityList = null;
        Connection connection = getConnection();
        try{
            entityList = QUERY_RUNNER.query(connection, sql, new BeanListHandler<>(entityClass), params);
        } catch (SQLException sqlException) {
            logger.error("sql错误：" + sqlException);
        } finally {
          closeConnection();
        }
        return entityList;
    }

    public static <T> T queryEntity(Class<T> entityClass, String sql, Object...params){
        T entity;
        try{
            Connection connection = getConnection();
            entity = QUERY_RUNNER.query(connection, sql, new BeanHandler<>(entityClass), params);
        } catch (SQLException sqlException) {
            logger.error("sql错误：" + sqlException);
            throw new RuntimeException(sqlException);
        } finally {
            closeConnection();
        }
        return entity;
    }
}
