package com.forte.mock.JDBC.field;

import java.sql.PreparedStatement;

/**
 *
 * 假字段，用于对表的创建
 *
 *  @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface MockField {


    /**
     * 获取这个字段的名称
     * @return 字段名称
     */
    String getName();

    /**
     * 获取这个字段的类型
     * @return 字段类型
     */
    MockFieldType getType();


    /**
     * 提供一个预处理sql，索引以及参数值，对其进行预处理赋值。
     * @param preparedStatement 预处理sql
     * @param index 索引
     * @param value 参数值
     */
    void preparedSet(PreparedStatement preparedStatement, int index, Object value);





}
