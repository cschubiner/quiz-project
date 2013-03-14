package achievement;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MAchievement {
	private String Name, Description, ImageName;
	private  int mAchievementID;
	
	public String getName() {
		return Name;
	}

	public String getDescription() {
		return Description;
	}

	public String getImageName() {
		return ImageName;
	}

	public int getmAchievementID() {
		return mAchievementID;
	}

	public MAchievement(ResultSet r) {
		try {
			mAchievementID = r.getInt(1);
			Name = r.getString(2);
			Description = r.getString(3);
			ImageName = r.getString(4);
		
		} catch (SQLException e) {
			System.out.println("error constructing machievement");
		}
	}

}
