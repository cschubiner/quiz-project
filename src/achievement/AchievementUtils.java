package achievement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.DBConnection;

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
		String query = "Select * from `tAchievement` where User = '"+userName+"' ORDER BY DateIssued DESC LIMIT "+limit+";";
		ArrayList<TAchievement> tas = getTAchievementsForQuery(db, query);
		
		ArrayList<MAchievement> achievements = new ArrayList<MAchievement>();
		for (TAchievement ta : tas){
			achievements.add(getMAchievementByID(db, ta.getmAchievementID()));
		}
		
		return achievements;
	}
	
	public static ArrayList<MAchievement> getAllAchievementsForUser(DBConnection db, String userName){
		String query = "Select * from `tAchievement` where User = '"+userName+"' ORDER BY DateIssued DESC;";
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
