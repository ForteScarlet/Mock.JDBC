package com.forte.mock.jdbc.connect;


import java.sql.*;

/**
 * @author river <[email]zsa12450@yeah.com>
 * @since JDK1.8
 */
public class AbstractJDBCTemplate implements ConnectAble {

    private ConnectInfo connectInfo;

    public AbstractJDBCTemplate(ConnectInfo connectInfo) {
        this.connectInfo = connectInfo;
    }

    @Override
    public Connection getConnection() throws ClassNotFoundException,SQLException {
        Class.forName(connectInfo.getDriver());
        Connection connection = DriverManager.getConnection(connectInfo.getUrl(), connectInfo.getUsername(), connectInfo.getPassword());
        return connection;
    }

    @Override
    public ConnectInfo getConnectInfo() {
        return connectInfo;
    }

    public void execute(String sql){
        Connection connection = null;
        try {
            connection = getConnection();
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                int cid = rs.getInt("cid");
                String cname = rs.getString("cname");
                int tid = rs.getInt("tid");
                System.out.println(cid + " " + cname + " " + tid);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (null != connection){
                try {
                    connection.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}


