package com.forte.mock.jdbc.operating.mysql;

import com.forte.mock.jdbc.table.MockTable;
import com.forte.mock.jdbc.operating.MockInsert;
import com.forte.mock.jdbc.table.MockTableField;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * 对MySQL数据库进行插入
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class MySQLMockInsert<T> implements MockInsert<T> {

    /**
     * 数据库表对象
     */
    private MockTable<T> table;

    /** limit数量 */
    private int limit = 1;
    /** 重复数量 */
    private int round = 1;

    private void initParameters(){
        limit = 1;
        round = 1;
    }

    /**
     * 构造
     */
    public MySQLMockInsert(MockTable<T> table){
        this.table = table;
    }

    public MockTable<?> getTable(){
        return table;
    }

    @Override
    public MySQLMockInsert<T> limit(int limit) {
        this.limit = limit;
        return this;
    }

    @Override
    public MySQLMockInsert<T> round(int round) {
        this.round = round;
        return this;
    }

    @Override
    public int insert() throws SQLException {
        int insert = doInsert(limit, round);
        initParameters();
        return insert;
    }

    /**
     * 插入数据
     * @param limit 数据数量
     */
    private int doInsert(int limit, int round) throws SQLException {
        int update = 0;
        for (int i = 0; i < round; i++) {
            // 获取预处理SQL
            String preSQL = getPrepareInsertSQL(limit);
            // 获取statement
            PreparedStatement preparedStatement = table.getConnection().prepareStatement(preSQL);

            // 为预处理参数赋值
            assignment(preparedStatement, limit);
            // 执行
            update += preparedStatement.executeUpdate();
            table.commit();
            preparedStatement.close();
        }
        return update;
    }

    /**
     * 为预处理SQL赋值。
     * @param preparedStatement 预处理SQL得到的statement
     * @param limit             插入数量
     */
    private void assignment(PreparedStatement preparedStatement, int limit) throws SQLException {
        int fieldLength = table.getFieldLength();
        // 索引从1开始
        for (int i = 0; i < limit; i++) {
            for (int fi = 1; fi <= fieldLength; fi++) {
                MockTableField field = table.getField(fi-1);
                field.setPreparedStatementValue(preparedStatement, fi + (i * fieldLength));
            }
        }
    }

    /**
     * 获取当前表的预插入语句。可以多个。
     * limit 需要大于1，要不然语句是错误的。
     */
    private String getPrepareInsertSQL(int limit){
        // INSERT INTO `table` (f1, f2, f3...)
        // values
        // (?, ?, ?...),
        // (?, ?, ?...),
        // (?, ?, ?...);
        String head = "INSERT INTO ";

        int initLength = getInitLength(limit);
        StringBuilder sb = new StringBuilder(initLength);
        // INSERT INTO `table` (
        sb.append(head).append('`').append(table.getTableName()).append("` (");
        // 遍历字段名称并置入
        table.fieldForEach((e, m) -> {
            sb.append('`').append(m.fieldName()).append('`');
            if(!e){
                sb.append(", ");
            }
        });
        sb.append(')').append(" VALUES ");
        for (int i = 0; i < limit; i++) {
            sb.append('(');
            table.fieldForEach((e, m) -> {
                sb.append("?");
                if(!e){
                    sb.append(", ");
                }
            });

            sb.append(')');
            if((i + 1) == limit){
                sb.append(';');
            }else{
                sb.append(',');
            }
        }
        return sb.toString();
    }

    /**
     * 获取SrtingBuilder的初始化长度
     * @param limit limit
     * @return
     */
    private int getInitLength(int limit){
        // INSERT INTO <- 12
        int initLength = 12;
        String tableName = table.getTableName();
        initLength += (tableName.length() + 2);
        int flNum = (table.getFields().length << 1);
        initLength += (4 + table.getFieldNameLength()) + 7;
        initLength += table.getFieldNameLength() * (limit << 1) ;
        return initLength;
    }

    @Override
    public String toSQL() {
        return null;
    }
}
