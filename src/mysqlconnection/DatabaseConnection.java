package mysqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

	public static Connection DatabaseConnection() {
		
		Connection conn = null;
		
		try {	
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bootcamplnt", "root", "");
//			System.out.println("Test");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

}
