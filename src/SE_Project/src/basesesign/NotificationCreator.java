package basesesign;

public class NotificationCreator {
	private String member_ID;
	private int worknote_number;
	private ServerConnector sc = null;
	
	public NotificationCreator(String member_ID, int worknote_number, ServerConnector sc) {
		this.member_ID = member_ID;
		this.worknote_number = worknote_number;
		this.sc = sc;
	}
	
	public void crateNotification(Notification n) {
		RequestPackage rp = new RequestPackage(n, RequestPackage.NOTIF_CREATE, "vote_creation", worknote_number, member_ID);
		sc.transfer(rp);
	}
}
