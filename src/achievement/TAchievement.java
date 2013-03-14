package achievement;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TAchievement {
	private  int mAchievementID, tAchievementID;
	private String userName, DateIssued;
	
	public int getmAchievementID() {
		return mAchievementID;
	}

	public int gettAchievementID() {
		return tAchievementID;
	}

	public String getUserName() {
		return userName;
	}

	public String getDateIssued() {
		return DateIssued;
	}

	public TAchievement(ResultSet r) {
		try {
			tAchievementID = r.getInt(1);
			mAchievementID = r.getInt(2);
			userName = r.getString(3);
			DateIssued = r.getString(4);
		} catch (SQLException e) {
			System.out.println("error constructing TAchievement");
		}
	}

}
