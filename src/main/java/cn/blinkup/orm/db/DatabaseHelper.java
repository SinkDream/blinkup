package cn.blinkup.orm.db;

import cn.blinkup.orm.utils.PropsUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

/**
 * @author joe
 * 数据库帮助类
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

    /**
     * 获取数据库连接
     * @return Connection对象
     */
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

    /**
     * 关闭数据库连接
     */
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

    /**
     * 查询实体列表
     * @param entityClass 实体类class
     * @param sql sql语句
     * @param params sql参数
     * @param <T> 返回的对应实体类对象类型
     * @return 实体类对应对象类型
     */
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

    /**
     * 查询单个实体
     * @param entityClass 实体类class
     * @param sql sql语句
     * @param params sql参数
     * @param <T> 实体类的对象类型
     * @return 单个实体类
     */
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

    /**
     * 查询连接查询后的结果集
     * @param sql sql语句
     * @param params sql参数
     * @return 连接查询后的数据内容
     */
    public static List<Map<String, Object>> queryUnionSet(String sql, Object...params){
        List<Map<String, Object>> result;
        try{
            Connection connection = getConnection();
            result = QUERY_RUNNER.query(connection, sql, new MapListHandler(), params);
        } catch (SQLException sqlException) {
            logger.error("sql错误：" + sqlException);
            throw new RuntimeException(sqlException);
        } finally {
            closeConnection();
        }
        return result;
    }

    /**
     * 执行数据更新操作
     * @param sql sql语句
     * @param params sql参数
     * @return 执行更新的数据行数
     */
    public static int execute(String sql, Object...params){
        int recordRows;
        try{
            Connection connection = getConnection();
            recordRows = QUERY_RUNNER.update(sql, params);
        } catch (SQLException sqlException) {
            logger.error("sql错误：" + sqlException);
            throw new RuntimeException(sqlException);
        } finally {
            closeConnection();
        }
        return recordRows;
    }

    /**
     * 根据类名获取表名
     * @param clazz 类名
     * @return 类名
     */
    private static String getTableName(Class<?> clazz){
        return clazz.getSimpleName();
    }

    /**
     * 插入单个实体
     * @param entityClass 实体类class
     * @param fieldMap 插入数据
     * @param <T> 类
     * @return 是否成功
     */
    public static<T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap){
        if(CollectionUtils.isEmpty(Collections.singleton((fieldMap)))){
            logger.error("字段集合为空");
            return false;
        }
        String sql = "INSERT INTO " + getTableName(entityClass);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        fieldMap.forEach((k,v) ->{
            columns.append(k).append(",");
            values.append(v).append(",");
        });
        sql += columns.substring(0, columns.length() - 1) + ")" + "VALUES" + values.substring(0, values.length() - 1) + ")";
        Object[] param = fieldMap.values().toArray();
        return execute(sql, param) == 1;
    }


}
