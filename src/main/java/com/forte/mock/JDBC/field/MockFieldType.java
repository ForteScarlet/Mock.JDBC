package com.forte.mock.JDBC.field;

/**
 *
 * 假字段类型封装类，一般可以获取到转化为字符串的类型等
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface MockFieldType {

    /**
     * t提供一些可能存在的参数，返回一个类型值。
     * 例如:
     * <ul>
     *     <li><code>int</code></li>
     *     <li><code>double(2)</code></li>
     *     <li><code>double(2,5)</code></li>
     * </ul>
     * @param args 可能存在的参数
     * @return 类型字符串
     */
    String getValue(Object... args);

}
