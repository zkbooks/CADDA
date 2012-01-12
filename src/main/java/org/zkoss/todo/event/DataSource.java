package org.zkoss.todo.event;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.hsqldb.jdbcDriver;

public enum DataSource {
	
	INSTANCE;
	
	private static final String url = "jdbc:hsqldb:mem:data";
	private static final String user = "sa";
	private static final String pwd = "";

	private Connection conn = null;

	static {
		try {
			new jdbcDriver();
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private DataSource() {
		// drop the table if it exists
		try {
			Statement stmt = this.getStatement();
			stmt.executeUpdate("drop table event if exists");
			stmt.executeUpdate("create table event (evtid VARCHAR(37) NOT NULL PRIMARY KEY, name VARCHAR(100), priority INTEGER, evtdate TIMESTAMP)");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close();
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
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}