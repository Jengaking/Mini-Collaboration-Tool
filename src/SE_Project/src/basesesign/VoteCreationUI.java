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

// 반드시 생성하고 worknote_number와 mem_id의 setter를 호출할것.
public class VoteCreationUI extends JFrame {
	private VoteCreator vc = null;
	private int worknote_num = -1;
	private String mem_id = null;
	
	private JButton createButton = null;
	private JPanel title =null;
	private JLabel votecreateLabel = null;
	private JPanel content = null;
	private JTextArea votecontent = null;
	private JPanel bt = null;
	private JPanel createPanel = null;
	private JPanel cancelPanel = null;
	private JButton cancelButton = null;
	
	public VoteCreationUI() {
		setTitle("투표 등록");

		title = new JPanel();

		votecreateLabel = new JLabel("투표 내용");
		votecreateLabel.setFont(new Font("휴먼편지체", Font.BOLD, 25));

		title.add(votecreateLabel);

		content = new JPanel();

		votecontent = new JTextArea(7, 20);

		content.add(votecontent);

		bt = new JPanel();
		bt.setLayout(new GridLayout(1, 2));

		createPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		createButton = new JButton("등록");
		
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(votecontent.getText().equals("")) {
					new ErrorMessage("내용을 입력하세요.");
					dispose();
				}
				Date today = new Date();
				@SuppressWarnings("deprecation")
				Vote nv = new Vote(-1, worknote_num, votecontent.getText(), today.getYear()+"-"+today.getMonth()+"-"+today.getDate()
						, 0, 0, mem_id, true);
				vc.crateVote(nv);
				dispose();
			}
		});
		cancelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		cancelButton = new JButton("취소");

		createPanel.add(createButton);
		cancelPanel.add(cancelButton);

		bt.add(createPanel);
		bt.add(cancelPanel);

		setLayout(new BorderLayout());
		add(title, BorderLayout.NORTH);
		add(content, BorderLayout.CENTER);
		add(bt, BorderLayout.SOUTH);

		setBounds(200, 200, 300, 250);

		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose(); // 현재의 frame을 종료시키는 메서드.
			}
		});
	}
	public void setController(VoteCreator c) {
		vc = c;
	}
	public void setMemberID(String s) {
		this.mem_id = s;
	}
	
	public void setWorknoteNumber(int wn) {
		this.worknote_num = wn;
	}
}