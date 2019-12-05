package com.forte.mock.jdbc.operating.mysql;

import com.forte.mock.jdbc.table.MockTableField;
import com.forte.util.mockbean.MockField;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class MySQLMockTableField extends MockTableField {

    /**
     * 全参构造
     */
    public MySQLMockTableField(MockField mockField, String fieldName, String fieldTableType, String[] fieldTableTypeParameters, String[] fieldConstraint, Class<?> fieldType) {
        super(mockField, fieldName, fieldTableType, fieldTableTypeParameters, fieldConstraint, fieldType);
    }

    /**
     * 得到一个预处理对象，对其进行赋值
     * @param statement 预处理SQL对象
     * @param index     索引
     */
    @Override
    public void setPreparedStatementValue(PreparedStatement statement, int index) throws SQLException {
        // 获取一个假数据
        statement.setObject(index, getMockValue());
    }
}
