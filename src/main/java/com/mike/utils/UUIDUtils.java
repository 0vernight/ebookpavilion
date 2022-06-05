package com.mike.utils;

import java.util.UUID;

/**
 * @author: 23236
 * @date: 2021/3/15 17:04
 * @description:
 */


public class UUIDUtils {
    //得到32位的uuid
    public static String getUUID32(){

        return UUID.randomUUID().toString().replace("-", "").toLowerCase();

    }

}
