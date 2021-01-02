package com.forte.mock.jdbc.operating.mysql;

import com.forte.mock.jdbc.table.MockTableField;
import com.forte.util.mockbean.MockField;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.function.BiFunction;

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

    @Override
    public void setPreparedStatementValue(PreparedStatement preparedStatement, int index, List<BiFunction<String, Object, Object>> mappers) throws SQLException {
        // 获取一个假数据
        Object mockValue = getMockValue();
        for (BiFunction<String, Object, Object> mapper : mappers) {
            mockValue = mapper.apply(getMockField().getFieldName(), mockValue);
        }
        preparedStatement.setObject(index, mockValue);
    }

}
