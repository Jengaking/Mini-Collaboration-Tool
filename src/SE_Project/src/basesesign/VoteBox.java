package basesesign;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

// ��ǥ ������Ʈ ������ 400, 235
public class VoteBox extends JPanel{
	private Vote myVote = null;
	
	private JCheckBox selectBox = new JCheckBox();
	private JLabel titleLabel = new JLabel();
	private JLabel voteLabel = new JLabel();
	
	public VoteBox(Vote v) {
		this.setLayout(null);
		this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		this.setBackground(Color.white);
		
		myVote = v;
		
		selectBox.setSize(20, 20);
		selectBox.setLocation(5, 12);
		selectBox.setBackground(Color.white);
		this.add(selectBox);
		
		titleLabel.setText(v.getVote_title());
		titleLabel.setSize(300, 20);
		titleLabel.setLocation(30, 5);
		this.add(titleLabel);
		
		voteLabel.setText("����ǥ: "+v.getAssent_number()+", �ݴ�ǥ: "+v.getOpposite_number()+", �� ��ǥ��:"+v.getTotalVote()
				+"    �������: "+v.getDate());
		System.out.println("client: voteBox making( title: "+ v.getVote_title()+"; assent: "+ v.getAssent_number()+"; opposite: " + v.getOpposite_number());
		voteLabel.setSize(350, 20);
		voteLabel.setLocation(30, 20);
		this.add(voteLabel);
		
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	public boolean isChecked() {
		return this.selectBox.isSelected();
	}
}
