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

    /**
     * 执行插入
     * @param limit 插入数量
     * @throws SQLException 数据库操作必然伴随着SQL异常
     */
    void insert(int limit) throws SQLException;

    /**
     * 仅插入一条
     * @throws SQLException 数据库操作必然伴随着SQL异常
     */
    default void insert() throws SQLException {
        insert(1);
    }



}
