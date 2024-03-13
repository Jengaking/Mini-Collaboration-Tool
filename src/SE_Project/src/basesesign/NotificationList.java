package basesesign;

import java.util.Vector;

public class NotificationList {
	private Vector<Notification> notificationList = new Vector<Notification>();
	
	public NotificationList() {}

	public Vector<Notification> getNotificationList() {
		return notificationList;
	}

	public void setNotificationList(Vector<Notification> notificationList) {
		this.notificationList = notificationList;
	}
	
	public void addNewNotification(Notification n) {
		this.notificationList.add(n);
	}
	
	public Notification getNotificationAt(int index) { return notificationList.get(index); }
	
	public Notification getNotificationOf(int notif_number) {
		for(int i = 0; i < notificationList.size(); i++) {
			if(notificationList.get(i).getNotification_number() == notif_number) {
				return notificationList.get(i);
			}
		}
		return null;
	}
	
	public Notification removeNotification(int notif_number) {
		for(int i = 0; i < notificationList.size(); i++) {
			if(notificationList.get(i).getNotification_number() == notif_number) {
				return notificationList.remove(i);
			}
		}
		return null;
	}
	
}
