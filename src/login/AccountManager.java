package login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;

public class AccountManager {

	private DBConnection database;
	//constructor
	public AccountManager(DBConnection db){
		database = db;
	}

	//checks to see if the given user is in the database
	private String getPassword(String userName){
		String query = "Select Password From Users Where UserName = \"" + userName + "\";";
		Statement stmt = database.getStatement();
		try {
			ResultSet ret = stmt.executeQuery(query);
			ret.beforeFirst();
			if(ret.next()){
				return ret.getString(1);
			}
			return "";
		} catch (SQLException e) {
			return "";
		}
	}

	//if the user does not exist, return false
	//otherwise return if the correct password for that user was provided
	public boolean login(String userName, String password){
		String realPassword = getPassword(userName);
		if(realPassword.equals("")) return false;
		return realPassword.equals(Cracker.generateHash(password));
	}

	//return true if the account creation was successful, false otherwise
	//if successful, account is added to loginMap
	public boolean createAccount(String userName, String password){
		String realPassword = getPassword(userName);
		if(!realPassword.equals("")) return false;

		String query = "Insert Into Users Values (\"" + userName +"\" , \"" +
				Cracker.generateHash(password) + "\");";

		Statement stmt = database.getStatement();
		try {
			stmt.executeUpdate(query);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
}
