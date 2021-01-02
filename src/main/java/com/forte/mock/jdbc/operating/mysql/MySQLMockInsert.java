package com.forte.mock.jdbc.operating.mysql;

import com.forte.mock.jdbc.table.MockTable;
import com.forte.mock.jdbc.operating.MockInsert;
import com.forte.mock.jdbc.table.MockTableField;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
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

    /**
     * limit数量
     */
    private int limit = 1;
    /**
     * 重复数量
     */
    private int round = 1;
    /**
     * 保存所有的sqlPeek
     */
    private List<BiConsumer<Integer, String>> sqlPeeks = new ArrayList<>(2);

    /**
     * 保存所有的sqlPeek
     */
    private List<BiFunction<String, Object, Object>> consumers = new ArrayList<>(2);

    /**
     * 是否启用异步
     */
    private boolean parallel = false;
    /**
     * 默认抛出
     */
    private Consumer<SQLException> errCatch = e -> {
        throw new RuntimeException(e);
    };

    /**
     * 清空参数
     */
    private void initParameters() {
        limit = 1;
        round = 1;
        sqlPeeks.clear();
        parallel = false;
        errCatch = e -> {
            throw new RuntimeException(e);
        };
    }

    /**
     * 构造
     */
    public MySQLMockInsert(MockTable<T> table) {
        this.table = table;
    }

    public MockTable<?> getTable() {
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
    public MockInsert<T> sqlPeek(BiConsumer<Integer, String> sqlPeek) {
        sqlPeeks.add(sqlPeek);
        return this;
    }


    /**
     * 进行中间操作。
     * @param mapper mapper
     * @return
     */
    @Override
    public MockInsert<T> map(BiFunction<String, Object, Object> mapper) {
        consumers.add(mapper);
        return this;
    }

    @Override
    public MockInsert<T> parallel(boolean enable) {
        parallel = enable;
        return this;
    }

    @Override
    public MockInsert<T> catchSQLError(Consumer<SQLException> errCatch) {
        this.errCatch = errCatch;
        return this;
    }

    @Override
    public int insert() {
        int insert = doInsert(limit, round);
        initParameters();
        return insert;
    }

    /**
     * 插入数据
     *
     * @param limit 数据数量
     */
    private int doInsert(int limit, int round) {
        Stream<Integer> stream = Stream.iterate(0, i -> i + 1).limit(round);
        if (parallel) {
            stream = stream.parallel();
        }
        int finalUpdate = stream.mapToInt(r -> {
            int update = 0;
            try {
                // 获取预处理SQL
                String preSql = getPrepareInsertSQL(limit);
                // 获取statement
                PreparedStatement preparedStatement = table.getConnection().prepareStatement(preSql);

                // 为预处理参数赋值
                assignment(preparedStatement, limit);
                // 如果存在peek函数
                if (sqlPeeks.size() > 0) {
                    String sqlString = preparedStatement.toString();
                    int nowRound = r + 1;
                    sqlPeeks.forEach(peek -> peek.accept(nowRound, sqlString));
                }
                // 执行
                update = preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                errCatch.accept(e);
                try {
                    table.rollback();
                } catch (SQLException ex) {
                    errCatch.accept(e);
                }
            }
            return update;
        }).sum();
        try {
            table.commit();
        } catch (SQLException e) {
            errCatch.accept(e);
            try {
                table.rollback();
            } catch (SQLException ex) {
                errCatch.accept(e);
            }
        }
        // 初始化参数
        return finalUpdate;
    }

    /**
     * 为预处理SQL赋值。
     *
     * @param preparedStatement 预处理SQL得到的statement
     * @param limit             插入数量
     */
    private void assignment(PreparedStatement preparedStatement, int limit) throws SQLException {
        int fieldLength = table.getFieldLength();
        // 索引从1开始
        for (int i = 0; i < limit; i++) {
            for (int fi = 1; fi <= fieldLength; fi++) {
                MockTableField field = table.getField(fi - 1);
                field.setPreparedStatementValue(preparedStatement, fi + (i * fieldLength), consumers);
            }
        }
    }

    /**
     * 获取当前表的预插入语句。可以多个。
     * limit 需要大于1，要不然语句是错误的。
     */
    private String getPrepareInsertSQL(int limit) {
        String head = "INSERT INTO ";
        int initLength = getInitLength(limit);
        StringBuilder sb = new StringBuilder(initLength);
        // INSERT INTO `table` (
        sb.append(head).append('`').append(table.getTableName()).append("` (");
        // 遍历字段名称并置入
        table.fieldForEach((e, m) -> {
            sb.append('`').append(m.fieldName()).append('`');
            if (!e) {
                sb.append(',');
            }
        });
        sb.append(')').append(" VALUES ");
        for (int i = 0; i < limit; i++) {
            sb.append('(');
            table.fieldForEach((e, m) -> {
                sb.append('?');
                if (!e) {
                    sb.append(',');
                }
            });

            sb.append(')');
            if ((i + 1) == limit) {
                sb.append(';');
            } else {
                sb.append(',');
            }
        }
        return sb.toString();
    }

    /**
     * 获取SrtingBuilder的初始化长度
     *
     * @param limit limit
     * @return
     */
    private int getInitLength(int limit) {
        // INSERT INTO `table` (f1, f2, f3...)
        // values
        // (?,?,?...),
        // (?,?,?...),
        // (?,?,?...);
        int initLength = 17;
        String tableName = table.getTableName();
        // 加表名 + ' values '
        initLength += tableName.length() + 8;
        // 加字段名 + 字段数量 - 1 个逗号
        int fl = table.getFieldLength();
        initLength += table.getFieldNameLength() + (fl - 1);
        // 加重复数量 * (2 + 字段数量 - 1 个逗号 + 字段数量个问号)
        initLength += limit * (2 + (fl - 1) + fl);
        initLength += limit * ((fl << 1) + 1);
        initLength += fl;
        return initLength;
    }

    @Override
    public String toSQL() {
        return null;
    }
}
