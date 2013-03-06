package user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import database.DBConnection;
import database.DatabaseUtils;

public class UserUtils {
	public static final String userTable = "Users";
	public static final String friendTable = "Friend";
	public static final String messageTable = "Message";
	public static final int USERNAME = 1;
	public static final int PASSWORD = 2;
	
	public static final int NORMAL_MESSAGE = 0;
	public static final int FRIEND_REQUEST = 1;
	public static final int CHALLENGE = 2;

	private static HashMap<String, String> getProfile(String username, DBConnection db){
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

	public static void addFriend(String from, String to, DBConnection db){
		String friendMessage = from +  " wants to add you as a friend!";
		sendMessage(from, to, FRIEND_REQUEST, friendMessage, db);
	}
	
	
	public static void sendMessage(String from, String to, int type, String message, DBConnection db){
		String query = "Insert Into " + messageTable + " Values (" + 
				"\"" + from+"\", \""+to+"\", \"" + message + "\", \"";
		Date d = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		query += sdf.format(d) + "\", 0, " + type + ");";
		
		DatabaseUtils.updateDatabase(db, query);
	}

	public static HashSet<String> findFriends(String username, DBConnection db){

		String query = "Select FriendTwo From " + friendTable + " Where FriendOne =\"" + username + "\";";
		return getUsersFromDatabaseWithQuery(db, query);
	}


	private static HashSet<String> getUsersFromDatabaseWithQuery(
			DBConnection db, String query) {
		HashSet<String> friends = new HashSet<String>();

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
