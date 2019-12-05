package com.forte.mock.jdbc.connect;

import java.io.Serializable;

/**
 *
 * 简单的连接信息封装类
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class ConnectInfo implements Serializable {
    private static final long serialVersionUID = 12346789756343567L;

    private String driver;
    private String url;
    private String username;
    private String password;

    public ConnectInfo() {
    }

    public ConnectInfo(String driver, String url, String username, String password) {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
