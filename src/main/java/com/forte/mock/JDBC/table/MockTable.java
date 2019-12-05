package com.forte.mock.JDBC.table;

import com.forte.mock.JDBC.MockSQL;

/**
 * MockTable 表映射
 * 通过此类可以进行建表，插入。
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface MockTable extends MockSQL {

    /**
     * 获取表名
     * @return 表名
     */
    String getTableName();

    /**
     * 获取表字段列表
     * @return 表字段列表
     */
    MockTableField[] getFields();

    /**
     * 有时候会在表结尾存在部分参数，例如编码格式。
     * @return 尾部参数
     */
    String[] getParameters();



}
