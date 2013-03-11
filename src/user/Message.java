package user;

public class Message {
	private String sender;
	private String recipient;
	private String messageType;
	private String timeSent;
	private boolean seen;
	private String message;
	
	public static final String NORMAL_MESSAGE = "Normal";
	public static final String FRIEND_REQUEST = "Friend Request";
	public static final String QUIZ_CHALLENGE = "Challenge";
	
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
		String mt = NORMAL_MESSAGE;
		if(messageType == UserUtils.FRIEND_REQUEST) mt = FRIEND_REQUEST;
		if(messageType == UserUtils.CHALLENGE) mt = QUIZ_CHALLENGE;
		
		return new Message(sender, recipient, mt, timeSent, isSeen, message);
	}
}
