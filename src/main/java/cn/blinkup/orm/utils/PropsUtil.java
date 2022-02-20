package cn.blinkup.orm.utils;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author zhangguosheng
 * 读取配置文件的属性
 */
public class PropsUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    public static Properties loadProps(String fileName){
        Properties props = null;
        InputStream inputStream = null;
        try{
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if(null == inputStream){
                throw new FileNotFoundException(fileName + "文件未找到");
            }
            props = new Properties();
            props.load(inputStream);
        } catch (IOException e) {
            LOGGER.error("加载配置文件失败：" + e);
        } finally {
            if(null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOGGER.error("关闭输入流失败：" + e);
                }
            }
        }
        return props;
        }

        public static String getString(Properties properties, String key){
            return getString(properties, key, "");
        }

        public static String getString(Properties properties, String key, String defaultValue){
            String value = defaultValue;
            if(properties.contains(key)){
                value = properties.getProperty(key);
            }
            return value;
        }

        public static Integer getInteger(Properties properties, String key){
            return getInteger(properties, key, 0);
        }

    public static Integer getInteger(Properties properties, String key, int defaultValue){
        int value = defaultValue;
        if(properties.contains(key)){
            value = CastUtil.castInt(properties.getProperty(key));
        }
        return value;
    }

    public static boolean getBoolean(Properties properties, String key){
        return getBoolean(properties, key, false);
    }

    public static boolean getBoolean(Properties properties, String key, boolean defaultValue){
        boolean value = defaultValue;
        if(properties.contains(key)){
            value = CastUtil.castBoolean(properties.getProperty(key));
        }
        return value;
    }

}
