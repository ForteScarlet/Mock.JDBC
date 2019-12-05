package com.forte.mock.jdbc.operating;

import com.forte.mock.jdbc.MockSQL;

import java.sql.SQLException;

/**
 * 提供一个连接对象与参数，执行向数据库插入数据的操作。
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface MockInsert<T> extends MockSQL {

    //**************** 将与Stream操作手感类似，提供一系列预处理参数 ****************//

    /**
     * 一次性插入多少条，一般为一次事物的提交的条数。
     * @param limit limit条数
     * @return 链式
     */
    MockInsert<T> limit(int limit);

    /**
     * 循环多少次插入
     * @param round 循环次数。
     * @return 链式
     */
    MockInsert<T> round(int round);


    //**************** 终结参数 ****************//

    /**
     * 最终执行insert方法。一般来讲插入条数为 limit * round
     * 且在插入完成后初始化上述参数。
     * @return 插入条数
     */
    int insert() throws SQLException ;

    ////////////////////////

    /**
     * 执行插入
     * @param limit 插入数量
     * @return 插入成功条数
     * @throws SQLException 数据库操作必然伴随着SQL异常
     */
    default int insert(int limit) throws SQLException {
        return limit(limit).insert();
    }



}
