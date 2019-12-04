package com.forte;

import com.forte.util.utils.MockUtil;

import java.sql.*;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class Test {
    public static void main(String[] args) throws Exception {
        // Connection
        // Jdbc程序中的Connection，它用于代表数据库的链接，
        // Collection是数据库编程中最重要的一个对象，
        // 客户端与数据库所有交互都是通过connection对象完成的
        Connection conn = DriverManager.getConnection("", "", "");

        Statement statement = conn.createStatement();
        PreparedStatement preparedStatement = conn.prepareStatement("");

        String s = "" +
                "" +
                "";

    }
}
