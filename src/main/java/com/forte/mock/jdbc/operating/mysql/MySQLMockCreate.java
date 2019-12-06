package com.forte.mock.jdbc.operating.mysql;

import com.forte.mock.jdbc.TypeToDatabase;
import com.forte.mock.jdbc.operating.MockCreate;
import com.forte.mock.jdbc.table.MockTableField;
import com.forte.mock.jdbc.table.MockTable;

import java.sql.SQLException;

import java.util.Map;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class MySQLMockCreate implements MockCreate {

    private String SQL;
    /**
     * 数据库表对象
     */
    private MockTable<?> mockTable;

    public MySQLMockCreate(MockTable<?> mockTable){
        this.mockTable = mockTable;
    }

    @Override
    public String toSQL() {
        return null;
    }

    /**
     * 初始化StringBuilder的初始长度
     * @return 建表语句的大致字符长度
     */
    public int initLength(){
        return mockTable.getFieldNameLength();
    }

    @Override
    public void createTable(boolean ignore) {
        StringBuilder sb = new StringBuilder(initLength());
        if (ignore) {
            sb.append("CREATE TABLE IF NOT exists").append(mockTable.getTableName()).append("(");
        }else {
            sb.append("CREATE TABLE ").append(mockTable.getTableName()).append("(");
        }
        //建表语句中的字段 CRATE TABLE tableName( xx int ...)
        for (MockTableField f :
                mockTable.getFields()) {
            if (null != f) {
                Map<Class, String> fieldMap = TypeToDatabase.MYSQL.getFieldMap();
                sb.append(f.fieldName()).append(" ");
                sb.append(fieldMap.get(f.fieldType()));
            }
        }
        sb.append(')');
        //根据参数parameters制定建表引擎与默认编码
        //如果用户没有传递这个参数，默认采用InnoDB建表引擎与utf8mb4编码
        String[] parameters = mockTable.getParameters();
        if (null != parameters) {
            for (String param :
                    parameters) {
                sb.append(param);
                sb.append(' ');
            }
        }
        sb.append(';');

        SQL = sb.toString();
        System.out.println(SQL);
        try {
            mockTable.getConnection().createStatement().execute(SQL);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
