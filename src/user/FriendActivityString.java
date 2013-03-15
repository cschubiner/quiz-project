package user;

public class FriendActivityString implements Comparable{
	private String string, dateTime;

	public String getString() {
		return string;
	}

	public String getDateTime() {
		return dateTime;
	}

	
	
	public FriendActivityString(String string, String dateTime) {
		this.string = string;
		this.dateTime = dateTime;
	}
	
	@Override 
    public int compareTo(Object o) {
		FriendActivityString friendOther = (FriendActivityString) o; 
        return dateTime.compareToIgnoreCase(friendOther.getDateTime());
    }
}
