package com.jsfztech.common.utils;

/**
 * Redis所有Keys
 *
 * @author xujianhua
 * @email xujianhua@outlook.com
 * @date 2017-07-18 19:51
 */
public class RedisKeys {

    public static String getSysConfigKey(String key){
        return "sys:config:" + key;
    }
}
