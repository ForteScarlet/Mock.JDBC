package com.forte.mock.JDBC.operating.mysql;

import com.forte.mock.JDBC.operating.MockCreate;
import com.forte.mock.JDBC.table.MockTable;
import com.forte.util.mockbean.MockField;

import java.sql.Statement;
import java.util.Map;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class MySQLMockCreate implements MockCreate {

    @Override
    public String toSQL() {
        return null;
    }

    @Override
    public MockTable createTable(Statement statement, String tableName, MockField[] fields, Map<String, String> parameters) {
        return null;
    }
}
