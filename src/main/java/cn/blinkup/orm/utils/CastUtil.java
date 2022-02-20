package cn.blinkup.orm.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author zhangguosheng
 * 类型转换工具类
 */
public class CastUtil {

    public static String castString(Object object){
        return CastUtil.castString(object, "");
    }

    public static String castString(Object object, String defaultValue){
        return null != object ? String.valueOf(object) : defaultValue;
    }

    public static double castDouble(Object object){
        return CastUtil.castDouble(object, 0);
    }

    public static double castDouble(Object object, double defaultValue){
        double value = defaultValue;
        if(null != object){
            String valueStr= castString(object);
            if(StringUtils.isEmpty(valueStr)){
                try{
                    value = Double.parseDouble(valueStr);
                } catch (NumberFormatException e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    public static long castLong(Object object){
        return CastUtil.castLong(object, 0L);
    }

    public static long castLong(Object object, long defaultValue){
        long value = defaultValue;
        if(null != object){
            String valueStr= castString(object);
            if(StringUtils.isEmpty(valueStr)){
                try{
                    value = Long.parseLong(valueStr);
                } catch (NumberFormatException e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    public static int castInt(Object object){
        return CastUtil.castInt(object, 0);
    }

    public static int castInt(Object object, int defaultValue){
        int value = defaultValue;
        if(null != object){
            String valueStr= castString(object);
            if(StringUtils.isEmpty(valueStr)){
                try{
                    value = Integer.parseInt(valueStr);
                } catch (NumberFormatException e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    public static boolean castBoolean(Object object){
        return CastUtil.castBoolean(object, false);
    }

    public static boolean castBoolean(Object object, boolean defaultValue){
        boolean value = defaultValue;
        if(null != object){
            String valueStr= castString(object);
            value = Boolean.parseBoolean(castString(object));
        }
        return value;
    }
}
