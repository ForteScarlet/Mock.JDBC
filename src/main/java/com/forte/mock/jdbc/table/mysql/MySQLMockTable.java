package com.forte.mock.jdbc.table.mysql;

import com.forte.mock.jdbc.connect.ConnectAble;
import com.forte.mock.jdbc.table.BaseMockTable;
import com.forte.mock.jdbc.table.MockTableField;

import java.util.function.Predicate;

/**
 *
 * MySQL对应的数据库
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class MySQLMockTable<T> extends BaseMockTable<T> {
    /**
     * 构造
     * @param connectCreator jdbc连接器
     * @param tableName      表名
     * @param fields         字段列表
     * @param parameters     参数列表
     */
    public MySQLMockTable(ConnectAble connectCreator, String tableName, MockTableField[] fields, String[] parameters) {
        super(connectCreator, tableName, fields, parameters);
    }

    @Override
    public void createTable(boolean ignoreExist) {

    }

    @Override
    public void insert() {

    }

    @Override
    public void insert(int limit) {

    }

    @Override
    public void insert(int limit, Predicate<T> filter) {

    }

    @Override
    public String toSQL() {
        return null;
    }
}
