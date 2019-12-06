package com.forte.mock.jdbc.operating;

import com.forte.mock.jdbc.MockSQL;

import java.sql.SQLException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

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

    /**
     * 可以提供一些中间操作，例如用来打印日志等。
     * peek函数的两个参数: round值与sql字符串
     * @param sqlPeek peek
     * @return 链式
     */
    MockInsert<T> sqlPeek(BiConsumer<Integer, String> sqlPeek);

    /**
     * 是否启用异步
     * @param enable s是否开启
     * @return 链式
     */
    MockInsert<T> parallel(boolean enable);

    /**
     * 启用异步
     * @return 链式
     */
    default MockInsert<T> parallel(){
        return parallel(true);
    }

    /**
     * 用户自定义的异常捕获。
     * @param errCatch 异常处理器
     * @return 链式
     */
    MockInsert<T> catchSQLError(Consumer<SQLException> errCatch);

    //**************** 终结参数 ****************//

    /**
     * 最终执行insert方法。一般来讲插入条数为 limit * round
     * 且在插入完成后初始化上述参数。
     * @return 插入条数
     */
    int insert();

    ////////////////////////

    /**
     * 执行插入
     * @param limit 插入数量
     * @return 插入成功条数
     * @throws SQLException 数据库操作必然伴随着SQL异常
     */
    default int insert(int limit) {
        return limit(limit).insert();
    }



}
