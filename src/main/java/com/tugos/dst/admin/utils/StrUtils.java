package com.tugos.dst.admin.utils;

/**
 * @author qinming
 * @date 2022-11-25 22:29:23
 * <p> String工具 </p>
 */
public class StrUtils {

    public static String ofNULL(Object obj) {
        return ofNULL(obj, "");
    }

    public static String ofNULL(Object obj, String defaultStr) {
        return obj == null ? defaultStr : obj.toString();
    }


}
