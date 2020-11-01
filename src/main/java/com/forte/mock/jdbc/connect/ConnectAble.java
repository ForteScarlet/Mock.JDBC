package com.forte.mock.jdbc.connect;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectAble {
    /**
     * 获取一个数据的连接对象
     * 这个连接对象可以是mysql、oracle、sqlServer的连接对象
     * @return 数据库连接对象
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    Connection getConnection() throws ClassNotFoundException,SQLException;

    /**
     * 获取连接信息
     */
    ConnectInfo getConnectInfo();

}
