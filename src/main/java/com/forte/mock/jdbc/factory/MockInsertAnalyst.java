package com.forte.mock.jdbc.factory;

import com.forte.mock.jdbc.operating.MockInsert;
import com.forte.mock.jdbc.table.MockTable;

import java.util.function.Function;

/**
 *
 * 插入器，传入一个MockTable得到一个MockInsert
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface MockInsertAnalyst<T> extends Function<MockTable<T>, MockInsert<T>> {


}
