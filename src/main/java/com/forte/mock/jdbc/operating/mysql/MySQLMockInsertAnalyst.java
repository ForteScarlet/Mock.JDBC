package com.forte.mock.jdbc.operating.mysql;

import com.forte.mock.jdbc.factory.MockInsertAnalyst;
import com.forte.mock.jdbc.operating.MockInsert;
import com.forte.mock.jdbc.table.MockTable;

/**
 * MySQL的
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class MySQLMockInsertAnalyst<T> implements MockInsertAnalyst<T> {

    @Override
    public MockInsert<T> apply(MockTable<T> table) {
        return new MySQLMockInsert<>(table);
    }
}
