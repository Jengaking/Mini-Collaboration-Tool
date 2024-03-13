package basesesign;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class VotingUI extends JFrame{
	private JLabel messageLabel = new JLabel();
	private JButton yesBnt = new JButton("예");
	private JButton noBnt = new JButton("아니오");
	private Font mfont = new Font("Monospaced", Font.BOLD, 20);
	private VoteParticipationController control = null;
	private VoteList vl = null;
	private LinkedList<VoteBox> vb = null;
	private String mem_ID = null;
	private boolean assent = true;
	
	public VotingUI(String msg, String mem_ID) {
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
				System.out.println("client: vote list size : " + vb.size());
				
				for(int i = 0; i < vb.size(); i++) {
					System.out.println("client: vote list["+i+"] = " + vb.get(i).isChecked());
					if(vb.get(i).isChecked()) {
						index = i;
						break;
					}
				}
				
				System.out.println("client: vote casting index = " + index);
				if(index == -1) {
					new ErrorMessage("경고: 투표를 선택하세요.");
					dispose();
					return;
				}
				
				System.out.println("client: activation = " + vl.getVoteAt(index).isActivated());
				
				if(control.checkAvailability(index, VotingUI.this.mem_ID)) {
					control.castVote(vl.getVoteAt(index), assent);
					dispose();
				} else {
					new ErrorMessage("경고: 투표할 수 없습니다.");
					dispose();
					return;
				}
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
	
	public void setController(VoteParticipationController c) {
		this.control = c;
	}
	
	public void setVoteBoxes(LinkedList<VoteBox> v) {
		this.vb = v;
	}
	
	public void setAssent(boolean assent) {
		this.assent = assent;
	}
	
	public void setVoteList(VoteList l) { this.vl = l; }
	
}
