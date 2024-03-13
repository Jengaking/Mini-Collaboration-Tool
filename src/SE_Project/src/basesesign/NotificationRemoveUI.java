package basesesign;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class NotificationRemoveUI extends JFrame{
	private JLabel messageLabel = new JLabel();
	private JButton yesBnt = new JButton("예");
	private JButton noBnt = new JButton("아니오");
	private Font mfont = new Font("Monospaced", Font.BOLD, 20);
	private NotificationRemover control = null;
	private NotificationList nl = null;
	private LinkedList<NotificationBox> nb = null;
	private String mem_ID = null;
	
	public NotificationRemoveUI(String msg, String mem_ID) {
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setSize(301,300);
		this.setLayout(null);
		this.mem_ID = mem_ID;
		messageLabel.setFont(mfont);
		this.messageLabel.setText(msg);
		this.messageLabel.setSize(400, 200);
		messageLabel.setLocation(5,30);
		this.add(messageLabel);
		
		yesBnt.setSize(133,40);
		yesBnt.setLocation(5, 200);
		yesBnt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = -1;
				for(int i = 0; i < nb.size(); i++) {
					if(nb.get(i).isChecked()) {
						index = i;
						break;
					}
				}
				
				System.out.println("client: notification removal index = " + index);
				
				if(index == -1) {
					new ErrorMessage("경고: 투표를 선택하세요.");
					dispose();
					return;
				}
				
				System.out.println("client: notif Owner() = " + nl.getNotificationAt(index).getOwner()+" user_id = " + mem_ID);
				if(nl.getNotificationAt(index).getOwner().equals(NotificationRemoveUI.this.mem_ID))
					control.removeNotification(nl.getNotificationAt(index));
				else {
					new ErrorMessage("삭제 권한이 없습니다.");
				}
				dispose();
			}
		});
		
		noBnt.setSize(133,40);
		noBnt.setLocation(150, 200);
		noBnt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		
		this.add(yesBnt);
		this.add(noBnt);
		
		this.setVisible(true);
	}
	
	public void setController(NotificationRemover c) {
		this.control = c;
	}
	
	public void setNotificationBoxes(LinkedList<NotificationBox> v) {
		this.nb = v;
	}
	
	
	
	public void setNotificationList(NotificationList l) { this.nl = l; }
	
}
