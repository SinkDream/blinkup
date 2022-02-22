package cn.blinkup.orm.db;

import cn.blinkup.orm.utils.PropsUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.dbcp2.BasicDataSource;
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
    private static final QueryRunner QUERY_RUNNER;
    private static final Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);
    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL;
    private static final BasicDataSource DATA_SOURCE;

    static {
        CONNECTION_THREAD_LOCAL = new ThreadLocal<Connection>();
        QUERY_RUNNER = new QueryRunner();

        Properties conf = PropsUtil.loadProps("config.properties");
        String driver = conf.getProperty("jdbc.driver");
        String url = conf.getProperty("jdbc.url");
        String userName = conf.getProperty("jdbc.username");
        String password = conf.getProperty("jdbc.password");

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setUrl(url);
        DATA_SOURCE.setDriverClassName(driver);
        DATA_SOURCE.setUsername(userName);
        DATA_SOURCE.setPassword(password);
    }

    /**
     * 获取数据库连接
     * @return Connection对象
     */
    public static Connection getConnection(){
        Connection connection = CONNECTION_THREAD_LOCAL.get();
        if(null == connection){
            try {
                connection = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                CONNECTION_THREAD_LOCAL.set(connection);
            }
        }
        return connection;
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
            recordRows = QUERY_RUNNER.update(connection, sql, params);
        } catch (SQLException sqlException) {
            logger.error("sql错误：" + sqlException);
            throw new RuntimeException(sqlException);
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
        StringBuilder columns = new StringBuilder(" (");
        StringBuilder values = new StringBuilder(" (");
        fieldMap.forEach((k,v) ->{
            columns.append(k).append(",");
            values.append("?").append(",");
        });
        sql += columns.substring(0, columns.length() - 1) + ") " + "VALUES" + values.substring(0, values.length() - 1) + ") ";
        Object[] param = fieldMap.values().toArray();
        return execute(sql, param) == 1;
    }

    public static <T> boolean updateEntity(Class<T> clazz, long id, Map<String, Object> filedMap){
        if(CollectionUtils.isEmpty(Collections.singleton(filedMap))){
            logger.error("参数map为空");
            return false;
        }
        String sql = "UPDATE " + getTableName(clazz) + " SET ";
        StringBuilder columns = new StringBuilder();
        filedMap.forEach((k,v) ->{
            columns.append(k).append("=?, ");
        });
        sql += columns.substring(0, columns.length() - 2) + " WHERE id=?";
        List<Object> paramList = new ArrayList<>();
        paramList.addAll(filedMap.values());
        paramList.add(id);
        Object[] params = paramList.toArray();
        System.out.println(sql);
        return execute(sql, params) == 1;
    }

    public static <T> boolean deleteEntity(Class<T> clazz, long id){
        String sql = "DELETE FROM " + getTableName(clazz) + " WHERE id=?";
        return execute(sql, id) == 1;
    }

}
