package achievement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import login.AccountManager;
import user.UserUtils;
import database.DBConnection;
import database.DatabaseUtils;

public class AchievementUtils {
	public static ArrayList<MAchievement> getMAchievementsForQuery(DBConnection db, String query){
		ArrayList<MAchievement> achievements = new ArrayList<MAchievement>();
		Statement stmt = db.getStatement();
		try {
			ResultSet r = stmt.executeQuery(query);
			r.beforeFirst();
			while (r.next()) {
				MAchievement achievement = new MAchievement(r);
				if (achievement!=null)
					achievements.add(achievement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return achievements;
	}
	public static ArrayList<TAchievement> getTAchievementsForQuery(DBConnection db, String query){
		ArrayList<TAchievement> achievements = new ArrayList<TAchievement>();
		Statement stmt = db.getStatement();
		try {
			ResultSet r = stmt.executeQuery(query);
			r.beforeFirst();
			while (r.next()) {
				TAchievement achievement = new TAchievement(r);
				if (achievement!=null)
					achievements.add(achievement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return achievements;
	}

	public static ArrayList<MAchievement> getXRecentlyAchievedAchievementsForUser(DBConnection db, String userName, int limit){
		String query = "Select * from `tAchievement` where User = '"+userName+"' group by mAchievementID ORDER BY DateIssued DESC LIMIT "+limit+";";
		ArrayList<TAchievement> tas = getTAchievementsForQuery(db, query);
		
		ArrayList<MAchievement> achievements = new ArrayList<MAchievement>();
		for (TAchievement ta : tas){
			achievements.add(getMAchievementByID(db, ta.getmAchievementID()));
		}
		
		return achievements;
	}
	
	private static boolean achievementExists(DBConnection db, String userName, int achievementID){
		String query = "Select Count(*) From tAchievement Where User=\"" + userName + "\" And mAchievementID =" + achievementID + ";";
		ResultSet r = DatabaseUtils.getResultSetFromDatabase(db, query);
		int num=0;
		try {
			if(r.next()){
				num = r.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return !(num==0);
	}
	
	public static void checkCreateQuizAchievements(DBConnection db, String userName){
		String query = "Select Count(*) From mQuiz Where Author =\""+userName +"\";";
		ResultSet r = DatabaseUtils.getResultSetFromDatabase(db, query);
		int numQuizCreated = 0;
		try {
			if(r.next()){
				numQuizCreated = r.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(numQuizCreated == MAchievement.AMATEUR_AUTHOR_COUNT){
			if(!achievementExists(db, userName, MAchievement.AMATEUR_AUTHOR)){
				awardUserAchievement(db, userName, MAchievement.AMATEUR_AUTHOR);
			}
		}else if (numQuizCreated == MAchievement.PROLIFIC_AUTHOR_COUNT){
			if(!achievementExists(db, userName, MAchievement.PROLIFIC_AUTHOR)){
				awardUserAchievement(db, userName, MAchievement.PROLIFIC_AUTHOR);
			}
		}else if (numQuizCreated == MAchievement.PRODIGIOUS_AUTHOR_COUNT){
			if(!achievementExists(db, userName, MAchievement.PRODIGIOUS_AUTHOR)){
				awardUserAchievement(db, userName, MAchievement.PRODIGIOUS_AUTHOR);
			}
		}
	}
	
	public static void awardUserAchievement(DBConnection db, String userName, int mAchievementID){
		String query = "INSERT INTO `tAchievement` (`mAchievementID`, `User`, `DateIssued`) VALUES ("+mAchievementID+", '"+userName+"', '"+DatabaseUtils.getTimestamp()+"');";
		DatabaseUtils.updateDatabase(db, query);
		UserUtils.sendMessage(AccountManager.ADMIN, userName, UserUtils.NORMAL_MESSAGE, "You earned an Achievement!", db);
	}
	
	public static ArrayList<MAchievement> getAllAchievementsForUser(DBConnection db, String userName){
		String query = "Select * from `tAchievement` where User = '"+userName+"' group by mAchievementID ORDER BY DateIssued DESC;";
		ArrayList<TAchievement> tas = getTAchievementsForQuery(db, query);
		ArrayList<MAchievement> achievements = new ArrayList<MAchievement>();
		for (TAchievement ta : tas){
			achievements.add(getMAchievementByID(db, ta.getmAchievementID()));
		}
		return achievements;
	}
	
	public static MAchievement getMAchievementByID(DBConnection db, int mAchievementID){
		return getMAchievementsForQuery(db, "Select * from `mAchievement` where mAchievementID = "+mAchievementID+";").get(0);
	}
}
