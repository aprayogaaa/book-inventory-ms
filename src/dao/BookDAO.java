package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Vector;

import mysqlconnection.DatabaseConnection;

public class BookDAO {
	
	public Connection conn;
	public Statement stmt;
	public ResultSet rs;
	public PreparedStatement pst;
	public ResultSetMetaData rsmd;
	
	Random rand = new Random();
	
	public BookDAO() throws SQLException {
		initConn();
	}
	
	public void initConn() throws SQLException {
		conn = DatabaseConnection.DatabaseConnection();
		if (conn == null) {
			throw new SQLException();
		}
	}
	
	public void insertBook (String bookName, String bookGenre, String bookAuthor, String publishDate, String publisher, Integer numOfPage) {
		try {
			stmt = conn.createStatement();
			String mysql = "INSERT INTO `book_data`(`BookId`, `Name`, `Genre`, `Author`, `PublishDate`, `Publisher`, `NumOfPage`) VALUES ('" + randId() + "','" + bookName + "','" + bookGenre + "','" + bookAuthor + "','" + publishDate + "','" + publisher + "','" + numOfPage + "')";
			stmt.executeUpdate(mysql);
		} catch (SQLException e) {
			e.printStackTrace();
		}  
	}
	
	public void updateBook (String bookId, String bookName, String bookGenre, String bookAuthor, String publishDate, String publisher, Integer numOfPage) {
		try {
			stmt = conn.createStatement();
			String mysql = "UPDATE `book_data` SET `Name`='" + bookName + "',`Genre`='" + bookGenre + "',`Author`='" + bookAuthor + "',`PublishDate`='" + publishDate + "',`Publisher`='" + publisher + "',`NumOfPage`='" + numOfPage + "' WHERE BookId ='" + bookId + "'";
			stmt.executeUpdate(mysql);
		} catch (SQLException e) {
			e.printStackTrace();
		}  
	}
	
	public void deleteBook (String bookId) {
		try {
			stmt = conn.createStatement();
			String mysql = "DELETE FROM `book_data` WHERE BookId ='" + bookId + "'";
			stmt.executeUpdate(mysql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Vector<Vector<Object>> getData(){
		Vector<Vector<Object>> data = new Vector<>();
		try {
			stmt = conn.createStatement();
			String mysql = "SELECT * FROM `book_data` WHERE 1";
			rs = stmt.executeQuery(mysql);
			while (rs.next()) {
				Vector<Object> row = new Vector<>();
				row.add(rs.getString(1));
				row.add(rs.getString(2));
				row.add(rs.getString(3));
				row.add(rs.getString(4));
				row.add(rs.getString(5));
				row.add(rs.getString(6));
				row.add(rs.getInt(7));
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public String randId() {
		String newId = "";
		try {
			Statement stmt = conn.createStatement();
			int random = rand.nextInt(1000);
			newId = String.format("BI-%03d", random);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newId;
	}
}
