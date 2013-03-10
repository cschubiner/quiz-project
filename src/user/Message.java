package user;

public class Message {
	private String sender;
	private String recipient;
	private int messageType;
	private String timeSent;
	private boolean seen;
	private String message;
	
	public String getSender() {
		return sender;
	}

	public String getRecipient() {
		return recipient;
	}

	public int getMessageType() {
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

	public Message(String sender, String recipient, int messageType,
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
		if(seen==0){
			return new Message(sender, recipient, messageType, timeSent, false, message);
		}
		return new Message(sender, recipient, messageType, timeSent, true, message);
	}
}
