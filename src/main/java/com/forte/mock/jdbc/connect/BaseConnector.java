package com.forte.mock.jdbc.connect;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * 临时使用的连接器
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class BaseConnector implements ConnectAble {

    /**
     * 连接信息
     */
    private final ConnectInfo connectInfo;

    /**
     * 获取一个链接
     */
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        return null;
    }

    /**
     * 获取连接信息
     */
    @Override
    public ConnectInfo getConnectInfo() {
        return connectInfo;
    }

    //**************** 构造 ****************//
    private BaseConnector(ConnectInfo connectInfo){
        this.connectInfo = connectInfo;
    }

}
