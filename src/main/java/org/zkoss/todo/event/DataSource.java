package org.zkoss.todo.event;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class DataSource {
    private static final String url = "jdbc:hsqldb:file:C:/hsqldb/event";
    private static final String user = "sa";
    private static final String pwd = "";

    private Connection conn = null;

    static {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Statement getStatement() throws SQLException {
        Statement stmt = null;
        // get connection
        conn = DriverManager.getConnection(url, user, pwd);
        stmt = conn.createStatement();
        return stmt;
    }

    public void close() {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}