package cn.blinkup.orm.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author zhangguosheng
 * 流操作工具类
 */
public class StreamUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamUtils.class);

    public static String getString(InputStream inputStream){
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        try {
            line = bufferedReader.readLine();
            while(null != line){
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("读取请求体错误" + e.getMessage());
        }
        return stringBuilder.toString();
    }
}
