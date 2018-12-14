package com.jsfztech.common.utils;

public class StringUtils {
    /**
     * 判断字符串为否为空
     *
     * @param str
     * @return
     */
    public static Boolean isNullOrEmpty(String str) {
        return (str == null || str.length() <= 0);
    }
}
