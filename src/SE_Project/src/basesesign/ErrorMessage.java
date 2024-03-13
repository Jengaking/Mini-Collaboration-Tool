package basesesign;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ErrorMessage extends JFrame {
	private JLabel messageLabel = new JLabel();
	private JButton yesBnt = new JButton("¿¹");
	
	public ErrorMessage(String msg) {
		super();
		messageLabel.setText(msg);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(301,300);
		this.setLayout(null);
		
		this.messageLabel.setSize(400, 200);
		messageLabel.setLocation(5,30);
		this.add(messageLabel);
		
		yesBnt.setSize(133,40);
		yesBnt.setLocation(5, 200);
		yesBnt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		this.add(yesBnt);
		
		
		this.setVisible(true);
	}
}
