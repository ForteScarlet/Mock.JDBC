package com.forte.mock.JDBC.utils;

import java.lang.reflect.Field;

/**
 *
 * 表相关工具类，例如开头大写转下划线小写等。
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class TableUtils {


    /**
     * 将一个字符串转化为下划线格式。
     * @param name     一个字符串
     * @param allUpper 是否全部大写
     * @return 下划线格式，例如：userInfo -> user_info
     */
    public static String toUnderline(String name, boolean allUpper){
        StringBuilder sb = new StringBuilder(name.length() + 3);
        int le = name.length();
        for (int i = 0; i < le; i++) {
            char c = name.charAt(i);
            if(i > 0 && Character.isUpperCase(c)){
                sb.append('_');
            }
            if(allUpper){
                sb.append(Character.toUpperCase(c));
            }else{
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }

    /**
     * 转下划线格式，默认不全部大写
     * @param name 字符串
     */
    public static String toUnderline(String name){
        return toUnderline(name, false);
    }

    /**
     * 转下划线形式的
     * @param from      类
     * @param allUpper  是否全大写
     * @return 下划线格式，例如：userInfo -> user_info
     */
    public static String toUnderline(Class<?> from, boolean allUpper){
        return toUnderline(from.getSimpleName(), allUpper);
    }

    /**
     * 转下划线形式的
     * @param from      类
     * @return 下划线格式，例如：userInfo -> user_info
     */
    public static String toUnderline(Class<?> from){
        return toUnderline(from.getSimpleName());
    }

    /**
     * 转下划线形式的
     * @param from      字段
     * @param allUpper  是否全部大写
     * @return 下划线格式，例如：userInfo -> user_info
     */
    public static String toUnderline(Field from, boolean allUpper){
        return toUnderline(from.getName(), allUpper);
    }

    /**
     * 转下划线形式的
     * @param from      字段
     * @return 下划线格式，例如：userInfo -> user_info
     */
    public static String toUnderline(Field from){
        return toUnderline(from.getName());
    }





}
