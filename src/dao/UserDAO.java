package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import mysqlconnection.DatabaseConnection;

public class UserDAO {

	public Connection conn;
	public Statement stmt;
	public ResultSet rs;
	public PreparedStatement pst;
	public ResultSetMetaData rsmd;
	
	public UserDAO() throws SQLException {
		initConn();
	}

	public void initConn() throws SQLException {
		conn = DatabaseConnection.DatabaseConnection();
		if (conn == null) {
			throw new SQLException();
		}
	}
	
	public void insertNewUser(String name, String address, String gender, Integer age, String password, String repassword) {
		try {
			stmt = conn.createStatement();
			String mysql = "INSERT INTO `user_data`(`Name`, `Address`, `Gender`, `Age`, `Password`) VALUES ('" + name + "','" + address + "','" + gender + "','" + age + "','" + password + "')";
			stmt.executeUpdate(mysql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean authenticationUser (String username, String password) {
		try {
			stmt = conn.createStatement();
			String mysql = "SELECT * FROM `user_data` WHERE Name = '" + username + "' && Password = '" + password + "'";
			rs = stmt.executeQuery(mysql);
			if (rs.next()) {
				username = rs.getString("Name");
				password = rs.getString("Password");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Vector<Vector<Object>> getUserData(){
		Vector<Vector<Object>> userData = new Vector<>();
		try {
			stmt = conn.createStatement();
			String mysql = "SELECT * FROM `user_data` WHERE 1";
			rs = stmt.executeQuery(mysql);
			while (rs.next()) {	
				Vector<Object> row = new Vector<>();
				row.add(rs.getString(1));
				row.add(rs.getString(2));
				row.add(rs.getString(3));
				row.add(rs.getInt(4));
				userData.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userData;
	}
	
}
