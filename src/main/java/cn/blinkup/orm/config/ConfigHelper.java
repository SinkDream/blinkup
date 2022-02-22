package cn.blinkup.orm.config;

import cn.blinkup.orm.utils.PropsUtil;

import java.util.Properties;

/**
 * @author zhangguosheng
 */
public final class ConfigHelper {

    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    public static String getJDBCDriver(){
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
    }

    public static String getJDBCUrl(){
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
    }
    public static String getJDBCUsername(){
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
    }
    public static String getJDBCPassword(){
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
    }

    public static String getBasePackage(){
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.BASE_PACKAGE);
    }
    public static String getJSPPath(){
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JSP_PATH, "/WEB-INF/view/");
    }
    public static String getAssetPath(){
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.ASSET_PATH, "/asset/");
    }



}
