package se.skaro.teslbot.web.api;

public class Message {

	public Message(String message, String user) {
		this.message = message;
		this.user = user;
		removeAfterSeconds = (60 * 60);
	}

	public Message(String message, String user, int removeAfterSeconds) {
		this.message = message;
		this.user = user;
		this.removeAfterSeconds = removeAfterSeconds;
	}

	private String message;
	private String user;
	private int removeAfterSeconds;

	public String getMessage() {
		return "<div id=data>" + message + "</div>";
	}

	public String getUser() {
		return user.toLowerCase();
	}

	public int getRemoveAfterSeconds() {
		return removeAfterSeconds;
	}

}
