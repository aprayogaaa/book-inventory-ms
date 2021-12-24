import java.sql.Connection;

import mysqlconnection.DatabaseConnection;

public class Main {

	public Main() {
		Connection conn = DatabaseConnection.DatabaseConnection();
		new Login();
	}

	public static void main(String[] args) {
		new Main();
	}

}
