package com.forte.mock.jdbc.operating.mysql;

import com.forte.mock.jdbc.operating.MockCreate;
import com.forte.mock.jdbc.table.MockTable;
import com.forte.util.mockbean.MockField;
import com.sun.istack.internal.Nullable;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class MySQLMockCreate implements MockCreate {

    private String SQL;


    @Override
    public String toSQL() {
        return null;
    }

    @Override
    public MockTable createTable(Statement statement, String tableName, MockField[] fields, @Nullable Map<String, String> parameters) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + tableName + "(");
        //建表语句中的字段 CRATE TABLE tableName( xx int ...)
        for (MockField f :
                fields) {
            if (null != f) {
                Map<Class, String> fieldMap = TypeToDatabase.MYSQL.getFieldMap();
                sb.append(f.getFieldName() + " ");
                sb.append(fieldMap.get(f.getFieldType()));
            }
        }
        sb.append(')');
        //根据参数parameters制定建表引擎与默认编码
        //如果用户没有传递这个参数，默认采用InnoDB建表引擎与utf8mb4编码
        if (null != parameters) {
            for (Map.Entry<String, String> e :
                    parameters.entrySet()) {
                sb.append(e.getKey() + "=" + e.getValue());
                sb.append(' ');
            }
        }
        sb.append(';');

        SQL = sb.toString();
        System.out.println(SQL);
        try {
            statement.execute(SQL);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
