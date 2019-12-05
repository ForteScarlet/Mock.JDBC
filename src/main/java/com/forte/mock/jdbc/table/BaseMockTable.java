package com.forte.mock.jdbc.table;

import com.forte.mock.jdbc.MockSQL;
import com.forte.mock.jdbc.connect.ConnectAble;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

/**
 * MockTable 表映射
 * 通过此类可以进行建表，插入。
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public abstract class BaseMockTable<T> implements MockSQL {

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


    /**
     * 父类构造
     * @param connectCreator jdbc连接器
     * @param tableName      表名
     * @param fields         字段列表
     * @param parameters     参数列表
     */
    public BaseMockTable(ConnectAble connectCreator, String tableName, MockTableField[] fields, String[] parameters){
        this.connectCreator = connectCreator;
        this.tableName = tableName;
        this.fields = fields;
        this.parameters = parameters;
    }

    /**
     * 获取连接器
     */
    public ConnectAble getConnectCreator(){
        return connectCreator;
    }

    /**
     * 获取一个连接
     */
    public Connection getConnection() throws RuntimeException {
        return this.connection.updateAndGet(old -> {
            try {
                Connection oldConn = old;
                if(oldConn.isClosed()){
                    oldConn = null;
                }
                return oldConn == null ? this.connectCreator.getConnection() : oldConn;
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }


    /**
     * 获取表名
     * @return 表名
     */
    String getTableName(){
        return tableName;
    }

    /**
     * 获取表字段列表
     * @return 表字段列表
     */
    MockTableField[] getFields(){
        return Arrays.copyOf(fields, fields.length);
    }

    /**
     * 有时候会在表结尾存在部分参数，例如编码格式。
     * @return 尾部参数
     */
    String[] getParameters(){
        return parameters;
    }

    //**************** 操作相关，通过泛型实现链式操作 ****************//

    /**
     * 创建一个表
     * @param ignoreExist 是否忽略表的存在性
     */
    public abstract void createTable(boolean ignoreExist);

    /**
     * 创建一个表，默认忽略表的存在性
     */
    public void createTable(){
        createTable(true);
    }

    /**
     * 插入一条数据
     */
    public abstract void insert();

    /**
     * 插入指定数量的数据
     * @param limit 数量
     */
    public abstract void insert(int limit);

    /**
     * 获取指定数量的通过了过滤器的数据
     * @param limit  数量
     * @param filter 过滤器
     */
    public abstract void insert(int limit, Predicate<T> filter);

}
