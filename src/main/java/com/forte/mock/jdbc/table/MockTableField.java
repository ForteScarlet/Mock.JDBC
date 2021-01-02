package com.forte.mock.jdbc.table;

import com.forte.mock.jdbc.utils.TableUtils;
import com.forte.util.Mock;
import com.forte.util.mockbean.MockField;
import org.apache.commons.beanutils.ConvertUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

/**
 *
 * 数据库字段对应表
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class MockTableField {

    /** 对应生成假数据的字段对象 */
    private final MockField mockField;
    /** 字段名，一般需要用下划线分隔而不是驼峰 */
    private final String fieldName;
    /** 字段在数据库中的类型 */
    private final String fieldTableType;
    /** 字段类型的参数，例如double(1,2),则这个1和2就是参数 */
    private final String[] fieldTableTypeParameters;
    /** 字段的约束条件 */
    private final String[] fieldConstraint;
    /** 字段的类型 */
    private final Class<?> fieldType;

    /**
     * 全参构造
     */
    public MockTableField(MockField mockField, String fieldName, String fieldTableType, String[] fieldTableTypeParameters, String[] fieldConstraint, Class<?> fieldType) {
        this.mockField = mockField;
        this.fieldName = fieldName;
        this.fieldTableType = fieldTableType;
        this.fieldTableTypeParameters = fieldTableTypeParameters;
        this.fieldConstraint = fieldConstraint;
        this.fieldType = fieldType;
    }

    public static MockTableField[] parse(MockField... fields){
        return Arrays.stream(fields).map(mf -> {
            // TODO 之后考虑额外参数，目前先按照默认的访日
            return new MockTableField(mf,
                    mf.getFieldName(),
                    TableUtils.toUnderline(mf.getFieldName()),
                    new String[0],
                    new String[0],
                    mf.getFieldType()
            );
        }).toArray(MockTableField[]::new);
    }

    public static final AtomicInteger iii = new AtomicInteger(41);


    /**
     * 根据类型对参数进行赋值
     * @param preparedStatement 预处理SQL对象
     * @param index             索引
     */
    public void setPreparedStatementValue(PreparedStatement preparedStatement, int index, List<BiFunction<String, Object, Object>> mappers) throws SQLException{
        Object value = mockField.getValue();
        for (BiFunction<String, Object, Object> mapper : mappers) {
            value = mapper.apply(mockField.getFieldName(), value);
        }
        value = ConvertUtils.convert(value, mockField.getFieldType());
        preparedStatement.setObject(index, value);
    }


    /**
     * 获取字段假数据生成器
     * @return 字段假数据生成器
     */
    public MockField getMockField(){
        return mockField;
    }

    /**
     * 获取一个假数据
     */
    public Object getMockValue(){
        if(getMockField() != null){
            return getMockField().getValue();
        }else{
            return null;
        }
    }

    /**
     * 获取字段的名称
     * @return 字段名
     */
    public String fieldName(){
        return fieldName;
    }

    /**
     * 获取字段在数据库中的类型
     * @return 字段的数据库类型
     */
    public String fieldTableType(){
        return fieldTableType;
    }

    /**
     * 如果存在的话，获取字段在数据库中的类型的参数。
     * 例如：double(1,2),其中，1、2即为参数
     * @return 约束条件
     */
    public String[] fieldTableTypeParameters(){
        return Arrays.copyOf(fieldTableTypeParameters, fieldTableTypeParameters.length);
    }

    /**
     * 如果存在的话，获取字段在数据库中的类型的参数。
     * 例如：double(1,2),其中，1、2即为参数
     * @return 约束条件
     */
    public String fieldTableTypeParameter(int index){
        return fieldTableTypeParameters[index];
    }

    /**
     * 对类型的参数执行join
     * @param delimiter join的delimiter
     * @return join结果
     */
    public String fieldTableTypeParametersJoin(CharSequence delimiter){
        return String.join(delimiter, fieldTableTypeParameters);
    }

    /**
     * 如果存在的话，获取此字段的建表约束条件。
     * 例如NOT NULL
     * @return 约束条件
     */
    public String[] fieldConstraint(){
        return Arrays.copyOf(fieldConstraint, fieldConstraint.length);
    }

    /**
     * 如果存在的话，获取此字段的建表约束条件。
     * 例如NOT NULL
     * @return 约束条件
     */
    public String fieldConstraint(int index){
        return fieldConstraint[index];
    }

    /**
     * 对字段约束进行join
     * @return 约束条件join
     */
    public String fieldConstraintJoin(CharSequence delimiter){
        return String.join(delimiter, fieldConstraint);
    }

    /**
     * 获取字段真正的类型
     * @return 字段类型
     */
    public Class<?> fieldType(){
        return fieldType;
    }

}
