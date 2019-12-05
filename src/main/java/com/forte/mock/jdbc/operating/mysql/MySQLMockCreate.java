package com.forte.mock.jdbc.operating.mysql;

import com.forte.mock.jdbc.connect.AbstractJDBCTemplate;
import com.forte.mock.jdbc.connect.ConnectAble;
import com.forte.mock.jdbc.connect.DefaultMySQLFactory;
import com.forte.mock.jdbc.operating.MockCreate;
import com.forte.mock.jdbc.table.BaseMockTable;
import com.forte.util.mockbean.MockField;

import java.sql.Connection;
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
        return SQL;
    }

    @Override
    public BaseMockTable createTable(Statement statement, String tableName, MockField[] fields, Map<String, String> parameters) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + tableName + "(");
        for (MockField f :
                fields) {
            if (null != f) {
                Map<Class, String> fieldMap = TypeToDatabase.getFieldMap();
                sb.append(f.getFieldName() + " ");
                sb.append(fieldMap.get(f.getFieldType()));
            }
        }
        sb.append(')');

        for (Map.Entry<String,String> e :
                parameters.entrySet()) {
            sb.append(e.getKey() + "=" + e.getValue());
            sb.append(' ');
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
