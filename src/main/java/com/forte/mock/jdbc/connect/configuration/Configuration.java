package com.forte.mock.jdbc.connect.configuration;

import com.forte.mock.jdbc.connect.ConnectInfo;

import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * 配置类
 */
public class Configuration {
    /** 连接信息 */
    private ConnectInfo connectInfo;
    /** 加载本地资源的加载器,类似于Properties */
    private final static ResourceBundle connectMappings;

    static {
        connectMappings = ResourceBundle.getBundle("configuration");
    }

    /**
     * 根据配置文件初始化连接信息
     */
    private void initConnectInfo(){
        connectInfo = new ConnectInfo();
        connectInfo.setDriver(connectMappings.getString("driver"));
        connectInfo.setUrl(connectMappings.getString("url"));
        connectInfo.setUsername(connectMappings.getString("username"));
        connectInfo.setPassword(connectMappings.getString("password"));
    }

    /**
     * 获取连接信息
     * @return
     */
    public ConnectInfo getConnectInfo(){
        return connectInfo;
    }

    public Configuration(){
        initConnectInfo();
    }

}
