package basesesign;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class NotificationCreationUI extends JFrame {
	private JLabel NtfCreateLabel;
	private JTextArea contentArea;
	private JButton CreateButton;
	private JButton CancelButton;
	private NotificationCreator controller = null;
	private JPanel title;
	private JPanel content;
	private JPanel bt;
	private JPanel createPanel;
	private JPanel cancelPanel;
	private int worknote_number;
	private String user;
	
	
	public NotificationCreationUI() {

		setTitle("공지사항 등록");

		title = new JPanel();

		NtfCreateLabel = new JLabel("공지사항 내용");
		NtfCreateLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));

		title.add(NtfCreateLabel);

		content = new JPanel();
		contentArea = new JTextArea(20, 50);
		content.add(contentArea);

		bt = new JPanel();
		bt.setLayout(new GridLayout(1, 2));

		createPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		CreateButton = new JButton("등록");
		CreateButton.setFont(new Font("맑은 고딕", Font.BOLD, 15));

		cancelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		CancelButton = new JButton("취소");
		CancelButton.setFont(new Font("맑은 고딕", Font.BOLD, 15));

		createPanel.add(CreateButton);
		cancelPanel.add(CancelButton);

		bt.add(createPanel);
		bt.add(cancelPanel);

		setLayout(new BorderLayout());
		add(title, BorderLayout.NORTH);
		add(content, BorderLayout.CENTER);
		add(bt, BorderLayout.EAST);

		setBounds(700, 400, 700, 400);

		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);

		CreateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(contentArea.getText().equals("")) {
					new ErrorMessage("내용을 입력하세요.");
					dispose();
				}
				Date today = new Date();
				//int notification_number, int worknote_number, String date, String contents, String owner
				Notification notif = new Notification(-1, worknote_number, today.getYear()+"-"+today.getMonth()+"-"+today.getDate(), contentArea.getText(), user);
				controller.crateNotification(notif);
				dispose();
			}
		});

		CancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose(); // 현재의 frame을 종료시키는 메서드.
			}
		});

	}
	
	public void setController(NotificationCreator c) {
		this.controller = c;
	}
	
	public void setWorknoteNumber(int n) {
		this.worknote_number = n;
	}
	
	public void setUser(String u) {
		this.user = u;
	}
}
