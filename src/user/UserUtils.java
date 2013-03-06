package user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;

import database.DBConnection;

public class UserUtils {
	public static final String userTable = "Users";
	public static final String friendTable = "Friend";
	public static final int USERNAME = 1;
	public static final int PASSWORD = 2;
	
	public static HashMap<String, String> getProfile(String username, DBConnection db){
		HashMap<String, String> result = new HashMap<String, String>();
		
		String query = "Select * From " + userTable + " Where UserName = \""+username+ "\";";
		Statement stmt = db.getStatement();
		
		try {
			ResultSet r = stmt.executeQuery(query);
			r.first();
			result.put("UserName", r.getString(USERNAME));
			
		} catch (SQLException e) {
			//do nothing
		}
		return result;
	}
	
	
	public static HashSet<String> findFriends(String username, DBConnection db){
		HashSet<String> friends = new HashSet<String>();
		
		String query = "Select FriendTwo From " + friendTable + " Where FriendOne =\"" + username + "\";";
		Statement stmt = db.getStatement();
		
		try{
			ResultSet r = stmt.executeQuery(query);
			r.beforeFirst();
			while(r.next()){
				friends.add(r.getString(1));
			}
		}catch(SQLException e){
			//do nothing
		}
		return friends;
	}
	
	public static String getUserLinkString(String username){
		return "<i><a href=\"UserProfileServlet?username="+username+"\">"+username+"</a></i>";
	}
	
}
