package com.forte.mock.jdbc.table.mysql;

import com.forte.mock.jdbc.connect.ConnectAble;
import com.forte.mock.jdbc.table.MockTable;
import com.forte.mock.jdbc.table.MockTableField;
import com.forte.util.mockbean.MockBean;

import java.sql.Connection;
import java.util.function.Predicate;


@Deprecated
public class MySQLMockTable<T> extends MockTable<T> {



    /**
     * 构造
     *
     * @param connectCreator jdbc连接器
     * @param tableName      表名
     * @param fields         字段列表
     * @param parameters     参数列表
     * @param mockBean
     */
    public MySQLMockTable(ConnectAble connectCreator,
                          String tableName,
                          MockTableField[] fields,
                          String[] parameters,
                          MockBean<T> mockBean) {
        super(connectCreator, tableName, fields, parameters, mockBean);
    }

    @Override
    public void createTable(boolean ignoreExist) {

    }

    /**
     * 插入一条数据
      */
    @Override
    public void insert() {
        // 获取连接
        Connection connection = getConnection();
//        connection.prepareStatement();

    }

    @Override
    public void insert(int limit) {

    }

    @Override
    public void insert(int limit, Predicate<T> filter) {

    }

    /**
     * to 啥的sql呢？
     * @return
     */
    @Override
    public String toSQL() {
        return null;
    }
}
