package basesesign;

public class Worknote {
	private int worknote_number;
	private String owner;
	private String worknote_name;
	private String date;

	private NotificationList notif_list = new NotificationList();
	private ChattingBoard chat_board = new ChattingBoard();
	private VoteList vote_list = new VoteList();

	public Worknote(int worknote_number, String owner, String worknote_name, String date) {
		super();
		this.worknote_number = worknote_number;
		this.owner = owner;
		this.worknote_name = worknote_name;
		this.date = date;
	}

	// getters and setters
	public int getWorknote_number() {
		return worknote_number;
	}

	public void setWorknote_number(int worknote_number) {
		this.worknote_number = worknote_number;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getWorknote_name() {
		return worknote_name;
	}

	public void setWorknote_name(String worknote_name) {
		this.worknote_name = worknote_name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public NotificationList getNotif_list() {
		return notif_list;
	}

	public void setNotif_list(NotificationList notif_list) {
		this.notif_list = notif_list;
	}

	public ChattingBoard getChat_board() {
		return chat_board;
	}

	public void setChat_board(ChattingBoard chat_board) {
		this.chat_board = chat_board;
	}

	public VoteList getVote_list() {
		return vote_list;
	}

	public void setVote_list(VoteList vote_list) {
		this.vote_list = vote_list;
	}

	
	// add/get/remove new info
	public void addNewVote(Vote v) {
		this.vote_list.addNewVote(v);
	}

	public void addNewChat(Chat c) {
		this.chat_board.addNewChat(c);
	}

	public void addNewNotification(Notification n) {
		notif_list.addNewNotification(n);
	}

	public Notification getNotifAt(int index) {
		return this.notif_list.getNotificationAt(index);
	}

	public Notification getNotifOf(int notif_number) {
		return this.notif_list.getNotificationOf(notif_number);
	}
	
	public Vote getVoteOf(int vote_number) {
		return this.vote_list.getVoteOf(vote_number);
	}

	public Vote getVoteAt(int index) {
		return this.vote_list.getVoteAt(index);
	}
	
	public Chat getChatOf(int chat_number) {
		return this.chat_board.getChatOf(chat_number);
	}

	public Chat getChatAt(int index) {
		return this.chat_board.getChatAt(index);
	}
	
}
