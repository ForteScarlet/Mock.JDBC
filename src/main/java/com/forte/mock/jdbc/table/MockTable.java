package com.forte.mock.jdbc.table;

import com.forte.mock.jdbc.MockSQL;
import com.forte.mock.jdbc.connect.ConnectAble;
import com.forte.util.Mock;
import com.forte.util.mockbean.MockBean;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

/**
 * MockTable 表映射
 * 通过此类可以进行建表，插入。
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class MockTable<T> implements Closeable {

    /** 连接获取器 */
    private ConnectAble connectCreator;
    /** 连接对象 */
    private AtomicReference<Connection> connection = new AtomicReference<>(null);
    /** 表名 */
    private final String tableName;
    /** 表字段列表 */
    private final MockTableField[] fields;
    /** 尾部参数 */
    private final String[] parameters;
    /** 用于生成假数据的mockBean */
    private final MockBean<T> mockBean;
    /** 是否已经关闭 */
    private boolean close = false;
    /** 记录所有的字段名的长度总和 */
    private final int fieldNameLength;

    /**
     * 构造
     * @param connectCreator jdbc连接器
     * @param tableName      表名
     * @param fields         字段列表
     * @param parameters     参数列表
     * @param mockBean       生成假数据用的mockBean对象
     */
    public MockTable(ConnectAble connectCreator,
                     String tableName,
                     MockTableField[] fields,
                     String[] parameters,
                     MockBean<T> mockBean){
        this.connectCreator = connectCreator;
        this.tableName = tableName;
        this.fields = fields;
        this.parameters = parameters;
        this.mockBean = mockBean;
        int fLength = 0;
        for (MockTableField field : fields) {
            fLength += field.fieldName().length();
        }
        // 赋值字段总长
        fieldNameLength = fLength;

    }

    /**
     * 获取连接器
     */
    public ConnectAble getConnectCreator(){
        return connectCreator;
    }

    public int getFieldNameLength(){
        return fieldNameLength;
    }

    /**
     * 获取一个连接。注意，获取的连接默认会切换至非自动提交状态。
     */
    public Connection getConnection() throws RuntimeException {
        if(close) {
            throw new RuntimeException("The connection has been closed");
        }

        return this.connection.updateAndGet(old -> {
            try {
                Connection oldConn = old;
                if(oldConn.isClosed()){
                    oldConn = null;
                }
                if(oldConn == null){
                    Connection connection = this.connectCreator.getConnection();
                    connection.setAutoCommit(false);
                    return connection;
                }else{
                    return oldConn;
                }
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 获取表名
     * @return 表名
     */
    public String getTableName(){
        return tableName;
    }

    /**
     * 获取表字段列表
     * @return 表字段列表
     */
    public MockTableField[] getFields(){
        return Arrays.copyOf(fields, fields.length);
    }

    /**
     * 获取指定索引的字段
     * @param index 索引
     */
    public MockTableField getField(int index){
        return fields[index];
    }

    /**
     * 遍历字段列表，参数有两个："是否为最后一个"与"字段对象"
     * @param consumer 遍历函数
     */
    public void fieldForEach(BiConsumer<Boolean, MockTableField> consumer){
        for (int i = 0; i < fields.length; i++) {
            consumer.accept((i + 1) == fields.length, fields[i]);
        }
    }

    /**
     * 有时候会在表结尾存在部分参数，例如编码格式。
     * @return 尾部参数
     */
    public String[] getParameters(){
        return parameters;
    }


    public MockBean<T> getMockBean(){
        return mockBean;
    }

    // closeable

    @Override
    public void close() throws IOException {
        close = true;
        Connection connection = this.connection.get();
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                throw new IOException(e);
            }
        }
    }

    public boolean isClose(){
        return close;
    }

    public void commit() throws SQLException {
        getConnection().commit();
    }



//
//    /**
//     * 创建一个表
//     * @param ignoreExist 是否忽略表的存在性
//     */
//    public abstract void createTable(boolean ignoreExist);
//
//    /**
//     * 创建一个表，默认忽略表的存在性
//     */
//    public void createTable(){
//        createTable(true);
//    }
//
//    /**
//     * 插入一条数据
//     */
//    public abstract void insert();
//
//    /**
//     * 插入指定数量的数据
//     * @param limit 数量
//     */
//    public abstract void insert(int limit);
//
//    /**
//     * 获取指定数量的通过了过滤器的数据
//     * @param limit  数量
//     * @param filter 过滤器
//     */
//    public abstract void insert(int limit, Predicate<T> filter);


}