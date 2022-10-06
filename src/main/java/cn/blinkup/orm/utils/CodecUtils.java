package cn.blinkup.orm.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author zhangguosheng
 * http编码工具类
 */
public class CodecUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodecUtils.class);

    public static String encodeURL(String source){
        String target = null;
        try {
            target = URLEncoder.encode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LOGGER.error("编码失败" + e.getMessage());
        }
        return target;
    }

    public static String decodeURL(String source){
        String target = null;
        try {
            target = URLDecoder.decode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LOGGER.error("解码失败" + e.getMessage());
        }
        return target;
    }
}
