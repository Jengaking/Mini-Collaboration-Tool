package basesesign;

import java.io.Serializable;

public class Notification implements Serializable{
	private int notification_number;
	private int worknote_number;
	private String date;
	private String contents;
	private String owner;
	
	public Notification(int notification_number, int worknote_number, String date, String contents, String owner) {
		super();
		this.notification_number = notification_number;
		this.worknote_number = worknote_number;
		this.date = date;
		this.contents = contents;
		this.owner = owner;
	}

	
	//getters and setters
	public int getNotification_number() {
		return notification_number;
	}

	public void setNotification_number(int notification_number) {
		this.notification_number = notification_number;
	}

	public int getWorknote_number() {
		return worknote_number;
	}

	public void setWorknote_number(int worknote_number) {
		this.worknote_number = worknote_number;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	
}
