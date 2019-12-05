package com.forte.mock.jdbc.table;

import com.forte.mock.jdbc.MockSQL;

/**
 *
 * 数据库字段对应表
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface MockTableField extends MockSQL {

    /**
     * 获取字段的名称
     * @return 字段名
     */
    String fieldName();

    /**
     * 获取字段在数据库中的类型
     * @return 字段的数据库类型
     */
    String fieldTableType();

    /**
     * 如果存在的话，获取字段在数据库中的类型的参数。
     * 例如：double(1,2),其中，1、2即为参数
     * @return 约束条件
     */
    String[] fieldTableTypeParameters();

    /**
     * 如果存在的话，获取此字段的建表约束条件。
     * 例如NOT NULL
     * @return 约束条件
     */
    String[] fieldConstraint();

    /**
     * 获取字段真正的类型
     * @return 字段类型
     */
    Class fieldType();

    /**
     * 转化为一行建表语句中的字符串。
     * 默认等同与{@link #toSQL()}
     * @return 转化为字符串
     */
    default String toLine(){
        return toSQL();
    };

}
