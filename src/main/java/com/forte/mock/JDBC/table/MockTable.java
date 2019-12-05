package com.forte.mock.JDBC.table;

import com.forte.mock.JDBC.MockSQL;

import java.util.function.Predicate;

/**
 * MockTable 表映射
 * 通过此类可以进行建表，插入。
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface MockTable<T> extends MockSQL {

    /**
     * 获取表名
     * @return 表名
     */
    String getTableName();

    /**
     * 获取表字段列表
     * @return 表字段列表
     */
    MockTableField[] getFields();

    /**
     * 有时候会在表结尾存在部分参数，例如编码格式。
     * @return 尾部参数
     */
    String[] getParameters();

    //**************** 操作相关，通过泛型实现链式操作 ****************//

    /**
     * 创建一个表
     * @param ignoreExist 是否忽略表的存在性
     */
    void createTable(boolean ignoreExist);

    /**
     * 创建一个表，默认忽略表的存在性
     */
    default void createTable(){
        createTable(true);
    }

    /**
     * 插入一条数据
     */
    void insert();

    /**
     * 插入指定数量的数据
     * @param limit 数量
     */
    void insert(int limit);

    /**
     * 获取指定数量的通过了过滤器的数据
     * @param limit  数量
     * @param filter 过滤器
     */
    void insert(int limit, Predicate<T> filter);


}
