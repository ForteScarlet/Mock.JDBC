package com.forte.mock.jdbc.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 *
 * 连接数据信息。
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class Connector implements ConnectAble {

    /**
     * 连接信息
     */
    private final ConnectInfo connectInfo;

    /**
     * 获取一个链接
     */
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        // 加载驱动
        Class.forName(connectInfo.getDriver());
        return DriverManager.getConnection(connectInfo.getUrl(), connectInfo.getUsername(), connectInfo.getPassword());
    }

    /**
     * 获取连接信息
     */
    @Override
    public ConnectInfo getConnectInfo() {
        return connectInfo;
    }

    /**
     * 构造
     */
    private Connector(ConnectInfo connectInfo){
        this.connectInfo = connectInfo;
    }

    /**
     * 获取一个连接获取器
     */
    public static Connector of(ConnectInfo connectInfo){
        return new Connector(connectInfo);
    }

    /**
     * 通过参数获取连接获取器
     * @param driver    驱动
     * @param url       路径
     * @param username  用户名
     * @param password  密码
     */
    public static Connector of(String driver, String url, String username, String password){
        ConnectInfo info = new ConnectInfo(driver, url, username, password);
        return new Connector(info);
    }

    /**
     * 可以通过properties数据来获取，但是其中必须包括以下四个key：
     * <ul>
     *     <li>driver</li>
     *     <li>url</li>
     *     <li>username</li>
     *     <li>password</li>
     * </ul>
     * @param data proterties数据
     */
    public static Connector of(Properties data){
        ConnectInfo info = new ConnectInfo();
        info.setDriver(  Objects.requireNonNull(data.getProperty("driver")  , "value of 'driver' not found.")  );
        info.setUrl(     Objects.requireNonNull(data.getProperty("url")     , "value of 'url' not found.")     );
        info.setUsername(Objects.requireNonNull(data.getProperty("username"), "value of 'username' not found."));
        info.setPassword(Objects.requireNonNull(data.getProperty("password"), "value of 'password' not found."));
        return new Connector(info);
    }

    /**
     * 允许使用Map类型作为数据，但是其中必须包括以下四个key：
     * <ul>
     *     <li>driver</li>
     *     <li>url</li>
     *     <li>username</li>
     *     <li>password</li>
     * </ul>
     * value值会通过String.valueOf()转化为字符串
     * @param data map数据
     */
    public static Connector of(Map<String, ?> data){
        ConnectInfo info = new ConnectInfo();
        info.setDriver(  String.valueOf(Objects.requireNonNull(data.get("driver")  , "value of 'driver' not found.")  ));
        info.setUrl(     String.valueOf(Objects.requireNonNull(data.get("url")     , "value of 'url' not found.")     ));
        info.setUsername(String.valueOf(Objects.requireNonNull(data.get("username"), "value of 'username' not found.")));
        info.setPassword(String.valueOf(Objects.requireNonNull(data.get("password"), "value of 'password' not found.")));
        return new Connector(info);
    }

}
