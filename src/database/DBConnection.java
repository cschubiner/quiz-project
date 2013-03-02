package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


//simple DBConnection class that opens up a database connection and returns a statement object
public class DBConnection {
	Connection con;
	
	//constructor, opens db connection
	public DBConnection(){
		con = MyDB.getConnection();
	}
	
	//returns a statement object for users to query
	public Statement getStatement(){
		try {
			return con.createStatement();
		} catch (SQLException e) {
			return null;
		}
	}
	
}
