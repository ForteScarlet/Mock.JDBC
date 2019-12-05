package com.forte.mock.jdbc.connect;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectAble {

    Connection getConnection()throws ClassNotFoundException,SQLException;

}
