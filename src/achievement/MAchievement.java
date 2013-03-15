package achievement;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MAchievement {
	public static final int AMATEUR_AUTHOR = 0;
	public static final int PROLIFIC_AUTHOR = 1;
	public static final int PRODIGIOUS_AUTHOR = 2;
	public static final int QUIZ_MACHINE = 3;
	public static final int I_AM_THE_GREATEST = 4;
	public static final int PRACTICE_MAKES_PERFECT = 5;
	
	public static final int AMATEUR_AUTHOR_COUNT = 1;
	public static final int PROLIFIC_AUTHOR_COUNT = 5;
	public static final int PRODIGIOUS_AUTHOR_COUNT = 10;
	public static final int QUIZ_MACHINE_COUNT = 10;
	
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
