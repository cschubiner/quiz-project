package admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import quiz.Quiz;

import database.DBConnection;
import database.DatabaseUtils;

public class AdminUtils {

	public static void AddAnnouncementToDatabase(DBConnection db, Announcement a){
		DatabaseUtils.updateDatabase(db, "INSERT INTO `Announcements` (`Message`, `Title`) " +
				"VALUES ('"+a.message+"', '"+a.title+"');");
	}

	public static ArrayList<Announcement> getXMostRecentAnnouncements(DBConnection db, int howManyToGet){
		ArrayList<Announcement> announcements = new ArrayList<Announcement>();
		String query = "(select * from Announcements order by id DESC limit "+howManyToGet+");";
		ResultSet r = DatabaseUtils.getResultSetFromDatabase(db, query);
		try {
			r.beforeFirst();

			while (r.next()) {
				Announcement a = new Announcement(r.getString("Title"),r.getString("Message"));
				announcements.add(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return announcements;
	}
	
	public static void MakeUserAnAdministrator(DBConnection db, String userName){
		DatabaseUtils.updateDatabase(db, "INSERT INTO `Administrators` (`userName`) VALUES ('"+userName+"');");
	}
	
	public static void RemoveAdministrator(DBConnection db, String userName){
		DatabaseUtils.updateDatabase(db, "DELETE FROM Administrators where userName = '"+userName+"';");
	}
}
