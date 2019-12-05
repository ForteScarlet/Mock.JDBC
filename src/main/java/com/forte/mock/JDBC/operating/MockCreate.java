package com.forte.mock.JDBC.operating;

import com.forte.mock.JDBC.table.MockTable;
import com.forte.util.mockbean.MockField;

import java.sql.Statement;
import java.util.Map;

/**
 * 如果有需要，通过Mock来创建一个表结构，通过参数生成建表语句
 *
 *  @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface MockCreate extends MockSQL {

    /**
     * 创建一个表
     * @param statement  sql连接信息
     * @param tableName  表名
     * @param fields     字段列表
     * @param parameters 数据库建表的额外参数列表
     * @return MockTable对象
     */
    MockTable createTable(Statement statement, String tableName, MockField[] fields, Map<String, String> parameters);


}
