package com.forte.mock.jdbc.operating;

import com.forte.mock.jdbc.MockSQL;
import com.forte.mock.jdbc.table.MockTable;
import com.forte.util.mockbean.MockField;

import java.util.Map;

/**
 * 如果有需要，通过Mock来创建一个表结构，通过参数生成建表语句
 *
 *  @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface MockCreate extends MockSQL {

    /**
     * 创建一个表
     * @param ignore 是否忽略已经存在的情况
     * @return MockTable对象
     */
    void createTable(boolean ignore);

    /**
     * 创建表，默认忽略存在情况
     */
    default void createTable(){
        createTable(true);
    }


}
