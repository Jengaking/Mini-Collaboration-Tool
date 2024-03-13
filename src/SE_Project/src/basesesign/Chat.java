package basesesign;

public class Chat {
	private int chat_number;
	private int worknote_number;
	private String contents;
	private String sender_name;
	private String owner;
	private String date;

	public Chat(int chat_number, int worknote_number, String contents, String sender_name, String owner, String date) {
		super();
		this.chat_number = chat_number;
		this.worknote_number = worknote_number;
		this.contents = contents;
		this.sender_name = sender_name;
		this.owner = owner;
		this.date = date;
	}

	// getters and setters
	public int getChat_number() {
		return chat_number;
	}

	public void setChat_number(int chat_number) {
		this.chat_number = chat_number;
	}

	public int getWorknote_number() {
		return worknote_number;
	}

	public void setWorknote_number(int worknote_number) {
		this.worknote_number = worknote_number;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getSender_name() {
		return sender_name;
	}

	public void setSender_name(String sender_name) {
		this.sender_name = sender_name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
