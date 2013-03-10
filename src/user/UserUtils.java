package user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import quiz.Quiz;
import quiz.QuizUtils;

import admin.AdminUtils;
import admin.Announcement;

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
		
	public static void addFriend(String from, String to, DBConnection db){
		String friendMessage = from +  " wants to add you as a friend!";
		sendMessage(from, to, FRIEND_REQUEST, friendMessage, db);
	}
	
	public static void confirmFriendRequest(String user1, String user2, DBConnection db){
		String query1 = "Insert Into " + friendTable + " Values (\""
				+ user1 + "\", \"" + user2 + "\");";
		String query2 = "Insert Into " + friendTable + " Values (\""
				+ user2 + "\", \"" + user1 + "\");";
		
		DatabaseUtils.updateDatabase(db, query1);
		DatabaseUtils.updateDatabase(db, query2);
	}
	
	//0 if nonexistent 
	//1 if user1 sent one
	//2 if user2 sent one
	public static int checkFriendRequest(String user1, String user2, DBConnection db){
		String query1 = "Select * From " + messageTable + " Where Sender=\"" + user1 
				+ "\" And Recipient=\"" + user2 + "\" And MessageType="+FRIEND_REQUEST+";";
		String query2 = "Select * From " + messageTable + " Where Sender=\"" + user2 
				+ "\" And Recipient=\"" + user1 + "\" And MessageType="+FRIEND_REQUEST+";";
		
		ResultSet r1 = DatabaseUtils.getResultSetFromDatabase(db, query1);
		ResultSet r2 = DatabaseUtils.getResultSetFromDatabase(db, query2);
		try {
			r1.last();
			r2.last();
			if(r1.getRow()!=0) return 1;
			if(r2.getRow()!=0) return 2;
		} catch (SQLException e) {
		}
		return 0;
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
	
	public static boolean isUserAnAdministrator(String username, DBConnection db){
		ResultSet r = DatabaseUtils.getResultSetFromDatabase(db, "select userName from Administrators where userName = '"+username+"';");
		try {
			return r.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static int getNumberOfUsers(DBConnection db){
		String query = "SELECT * FROM Users;";
		return DatabaseUtils.getNumberOfResultsForQuery(db, query);
	}
	
	public static ArrayList<String> getAllUsernamesFromDatabase(DBConnection db){
		ArrayList<String> userNames = new ArrayList<String>();
		String query = "SELECT * FROM Users;";
		ResultSet r = DatabaseUtils.getResultSetFromDatabase(db, query);
		try {
			r.beforeFirst();
			while (r.next()) {
				String userName = r.getString(1);
				if (userName != null)
					userNames.add(userName);
			}
		} catch (SQLException e) { }
		return userNames;
	}
	
	public static void RemoveUser(DBConnection db, String userName){
		ArrayList<Quiz> quizzes = QuizUtils.getAllQuizzesCreatedByUser(db, userName);
		for (int i = 0; i < quizzes.size(); i++){
			QuizUtils.removeMQuizFromDatabase(db, quizzes.get(i).getID());
		}
		
		AdminUtils.RemoveAdministrator(db, userName);
		
		DatabaseUtils.updateDatabase(db, "Delete from Message where Sender = '"+userName+"' or Recipient = '"+userName+"';");
		DatabaseUtils.updateDatabase(db, "Delete from Friend where FriendOne = '"+userName+"' or FriendTwo = '"+userName+"';");
		DatabaseUtils.updateDatabase(db, "Delete from tAchievement where User = '"+userName+"';");
		DatabaseUtils.updateDatabase(db, "Delete from Users where UserName = '"+userName+"';");
	}

	public static String getUserLinkString(String username){
		return "<i><a href=\"UserProfileServlet?username="+username+"\">"+username+"</a></i>";
	}

}
