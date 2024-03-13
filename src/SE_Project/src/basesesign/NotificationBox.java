package basesesign;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NotificationBox extends JPanel{
	private JCheckBox selectBox = new JCheckBox();
	private JLabel titleLabel = new JLabel();
	private JLabel notificationLabel = new JLabel();
	
	public NotificationBox(Notification n) {
		this.setLayout(null);
		this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		this.setBackground(Color.white);
		
		selectBox.setSize(20, 20);
		selectBox.setLocation(5, 12);
		selectBox.setBackground(Color.white);
		this.add(selectBox);
		
		titleLabel.setText(n.getContents());
		titleLabel.setSize(70, 20);
		titleLabel.setLocation(30, 5);
		this.add(titleLabel);
		
		notificationLabel.setText("작성일: " + n.getDate() + ", 작성자 : "+ n.getOwner());
		notificationLabel.setSize(200, 20);
		notificationLabel.setLocation(30, 20);
		this.add(notificationLabel);
		
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	public boolean isChecked() {
		return this.selectBox.isSelected();
	}
}
