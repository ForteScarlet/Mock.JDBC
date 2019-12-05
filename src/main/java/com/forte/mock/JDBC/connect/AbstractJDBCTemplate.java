package com.forte.mock.JDBC.connect;


import java.sql.*;

public class AbstractJDBCTemplate implements ConnectAble {

    private String driver;
    private String url;
    private String username;
    private String password;

    public AbstractJDBCTemplate(String driver, String url, String username, String password) {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
    }


    public Connection getConnection() throws ClassNotFoundException,SQLException {
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }

    public void execute(String sql){
        Connection connection = null;
        try {
            connection = getConnection();
            Statement statement = connection.createStatement();
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


