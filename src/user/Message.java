package user;

public class Message {
	private String sender;
	private String recipient;
	private String messageType;
	private String timeSent;
	private boolean seen;
	private String message;
	
	public String getSender() {
		return sender;
	}

	public String getRecipient() {
		return recipient;
	}

	public String getMessageType() {
		return messageType;
	}

	public String getTimeSent() {
		return timeSent;
	}

	public boolean isSeen() {
		return seen;
	}

	public String getMessage() {
		return message;
	}

	public Message(String sender, String recipient, String messageType,
			String timeSent, boolean seen, String message) {
		this.sender = sender;
		this.recipient = recipient;
		this.messageType = messageType;
		this.timeSent = timeSent;
		this.seen = seen;
		this.message = message;
	}
	
	public static Message generateMessage(String sender, String recipient, int messageType,
			String timeSent, int seen, String message) {
		boolean isSeen = false;
		if(seen == 1) isSeen = true;
		String mt = "Normal";
		if(messageType == UserUtils.FRIEND_REQUEST) mt = "Friend Request";
		if(messageType == UserUtils.CHALLENGE) mt = "Challenge";
		
		return new Message(sender, recipient, mt, timeSent, isSeen, message);
	}
}
