package cn.blinkup.orm.operate;

import cn.blinkup.orm.db.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author joe
 * 基础数据操作
 */
public class BaseDatabaseOperate {

    /**
     * 执行sql语句
     * @param sql sql语句
     * @return wheather success
     */
    public boolean executeLanguage(String sql){
        boolean success = false;
        Connection conn = DatabaseHelper.getConnection();
        try {
            assert conn != null;
            PreparedStatement pstm = conn.prepareStatement(sql);
            success = pstm.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DatabaseHelper.closeConnection(conn);
        }
        return success;
    }
}
