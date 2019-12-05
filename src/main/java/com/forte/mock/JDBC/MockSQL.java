package com.forte.mock.JDBC;

/**
 *
 * 每一个Mock sql都应该能够转化为一个对应的sql字符串
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface MockSQL {

    /**
     * 转化为一句SQL字符串
     * @return to sql string
     */
    String toSQL();

}
