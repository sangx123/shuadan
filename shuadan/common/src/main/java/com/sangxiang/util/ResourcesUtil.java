package com.sangxiang.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 资源文件读取工具类
 */
public class ResourcesUtil {

    private static ResourceBundle getBundle(String baseName, Locale locale) {
        if (locale == null) {
            return ResourceBundle.getBundle(baseName, Locale.getDefault());
        }
        return ResourceBundle.getBundle(baseName, locale);
    }

    private static String getProperties(String baseName, String section, Locale locale) {
        ResourceBundle rb = getBundle(baseName, locale);
        return (String) rb.getObject(section);
    }

    public static String getValue(String fileName, String key, Locale locale) {
        return getProperties(fileName, key, locale);
    }

    public static String getValue(String fileName, String key) {
        return getProperties(fileName, key, null);
    }

    public static String getValue(String fileName, String key, Locale locale, Object... objs) {
        String pattern = getValue(fileName, key, locale);
        String value = MessageFormat.format(pattern, objs);
        return value;
    }

    public static String getValue(String fileName, String key, Object... objs) {
        String pattern = getValue(fileName, key);
        String value = MessageFormat.format(pattern, objs);
        return value;
    }

    // 从资源文件中获取所有key，以list返回
    public static List<String> getKeyList(String baseName, Locale locale) {
        ResourceBundle rb = getBundle(baseName, locale);
        return new ArrayList<>(rb.keySet());
    }

    public static List<String> getKeyList(String baseName) {
        return getKeyList(baseName, null);
    }

}
