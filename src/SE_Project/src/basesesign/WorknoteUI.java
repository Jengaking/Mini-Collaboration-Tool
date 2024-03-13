package basesesign;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.border.Border;

/*
 * date : 2023-05-26
 * �ۼ��� : �̿���
 */

// ���� ��ũ��Ʈ ȭ�� UI
public class WorknoteUI extends JFrame {
	// �⺻ ���
	private int worknote_number = 0;
	private String member_ID = null;
	private ServerConnector serverConnector;
	
	// ��ư
	private JButton ntfCreateButton = new JButton("���� ���");
	private JButton ntfRemoveButton = new JButton("���� ����");
	private JButton voteCreateButton = new JButton("��ǥ ���");
	private JButton voteRemoveButton = new JButton("��ǥ ����");
	private JButton voteCloseButton = new JButton("��ǥ ����");
	private JButton voteAssentButton = new JButton("����");
	private JButton voteOppositeButton = new JButton("�ݴ�");
	private JButton chatTransferButton = new JButton("����");
	private JButton exitButton = new JButton("������");

	// ä�� ������Ʈ
	private JTextField chatField = new JTextField();
	private JTextArea chattingBoard = new JTextArea();
	private JScrollPane chatPane = new JScrollPane();

	// ��ǥ ������Ʈ
	private JScrollPane votePane = new JScrollPane();
	private JPanel voteBasePanel = new JPanel();
	private JViewport voteViewport = new JViewport();
	private JPanel voteColumnPanel = new JPanel();
	private LinkedList<VoteBox> voteBoxes = new LinkedList<VoteBox>();

	// �������� ������Ʈ
	private JScrollPane notificationPane = new JScrollPane();
	private JPanel notificationBasePanel = new JPanel();
	private JViewport notificationViewport = new JViewport();
	private JPanel notificationColumnPanel = new JPanel();
	private LinkedList<NotificationBox> notificationBoxes = new LinkedList<NotificationBox>();

	// ���� ȸ�� ������Ʈ
	private JScrollPane onlineUserPane = new JScrollPane();
	private JScrollPane offlineUserPane = new JScrollPane();
	private JTextArea onlinePanel = new JTextArea();
	private JTextArea offlinePanel = new JTextArea();

	// �������� ��ư ������
	private Border notifBorder = BorderFactory.createTitledBorder("��������");
	private JPanel notifBntPanel = new JPanel();

	// ��ǥ ��ư ������
	private Border voteBorder = BorderFactory.createTitledBorder("��ǥ");
	private JPanel voteBntPanel = new JPanel();

	// ����� ���� ����Ƽ Ŭ���� ����
	private VoteList votes = new VoteList();
	private NotificationList notifs = new NotificationList();
	
	
	// votingUI
	private VotingUI assent_msg = null;
	private VotingUI opposite_msg = null;
	
	// ��Ʈ�� Ŭ���� ����
	private VoteCreator voteCreateController;
	private VoteParticipationController votingController;
	private NotificationCreator notifCreateController;
	private NotificationRemover notifRemoveController;
	
	// voteCreation UI
	private VoteCreationUI vcui = null;
	private NotificationCreationUI ncui = null;
	private NotificationRemoveUI nrui = null;
	
	public WorknoteUI(String member_ID, int worknote_num) {
		System.out.println("client: worknote_UI_constructor.");
		this.member_ID = member_ID;
		this.worknote_number = worknote_num;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(1260, 590);
		setTitle("��ũ��Ʈ");
		setLayout(null);

		System.out.println("client: make a connection with a server.");
		serverConnector = new ServerConnector(votes, notifs, this);
		
		// ��Ʈ�� ��ü ����
		voteCreateController = new VoteCreator(this.member_ID, this.worknote_number, serverConnector);
		votingController = new VoteParticipationController(this.member_ID, this.worknote_number, serverConnector, votes);
		notifCreateController = new NotificationCreator(this.member_ID, this.worknote_number, serverConnector);
		notifRemoveController = new NotificationRemover(this.member_ID, this.worknote_number, serverConnector);
		
		
		// �������� ���, ���� ��ư ����
		notifBntPanel.setSize(296, 76);
		notifBntPanel.setBorder(notifBorder);
		notifBntPanel.setLocation(10, 10);
		notifBntPanel.setLayout(null);

		ntfCreateButton.setSize(133, 40);
		ntfCreateButton.setLocation(10, 20);
		ntfCreateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ncui = new NotificationCreationUI();
				ncui.setController(notifCreateController);
				ncui.setWorknoteNumber(worknote_number);
				ncui.setUser(WorknoteUI.this.member_ID);
			}
		});
		
		ntfRemoveButton.setSize(133, 40);
		ntfRemoveButton.setLocation(153, 20);
		ntfRemoveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nrui = new NotificationRemoveUI("���� �����Ͻðڽ��ϱ�?", WorknoteUI.this.member_ID);
				nrui.setController(notifRemoveController);
				nrui.setNotificationBoxes(notificationBoxes);
				nrui.setNotificationList(notifs);
				
			}
		});
		notifBntPanel.add(ntfCreateButton);
		notifBntPanel.add(ntfRemoveButton);
		this.add(notifBntPanel);

		// ��ǥ ���, ���� ��ư ����
		voteBntPanel.setBorder(voteBorder);
		voteBntPanel.setSize(296, 76);
		voteBntPanel.setLocation(315, 10);
		voteBntPanel.setLayout(null);

		voteCreateButton.setSize(133, 40);
		voteCreateButton.setLocation(10, 20);
		voteCreateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				vcui = new VoteCreationUI();
				vcui.setController(voteCreateController);
				vcui.setMemberID(member_ID);
				vcui.setWorknoteNumber(worknote_number);
			}
			
		});
		
		voteRemoveButton.setSize(133, 40);
		voteRemoveButton.setLocation(153, 20);

		voteBntPanel.add(voteCreateButton);
		voteBntPanel.add(voteRemoveButton);
		this.add(voteBntPanel);
		
		// ä�ú��� ����
		chatPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		chatPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		chatPane.setSize(600, 400);
		chatPane.setLocation(10, 90);
		
		chattingBoard.setEditable(false);
		
		JViewport temp = chatPane.getViewport();
		temp.add(chattingBoard);
		chatPane.setViewport(temp);
		
		this.add(chatPane);
		
		// ä�� �Է� â, ��ư ����
		chatField.setSize(520, 40);
		chatField.setLocation(10, 500);
		this.add(chatField);
		
		chatTransferButton.setSize(75,40);
		chatTransferButton.setLocation(535, 500);
		this.add(chatTransferButton);
		
		// ��ǥ ������Ʈ ����
		votePane.setSize(400, 235);
		votePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		votePane.setLocation(620,10);
		
		voteBasePanel.setLayout(new BorderLayout(0,0));
		voteColumnPanel.setLayout(new BoxLayout(voteColumnPanel, BoxLayout.Y_AXIS));
		
		// �׽��� �ڵ�.
		//public Vote(int vote_number, String title, String date, int assent_number, int opposite_number, String owner, boolean activation, boolean voted)
		//int vote_number, String title, String date, int assent_number, int opposite_number, String owner, boolean activation,boolean voted
//		VoteBox vb = new VoteBox(new Vote(123, worknote_number, "�ް��� ������ ���� ������", "2023-01-04", 0, 0, "dnjswns648", false, false));
//		voteColumnPanel.add(vb);
		voteBasePanel.add(voteColumnPanel, BorderLayout.CENTER);
		
		votePane.setViewportView(voteBasePanel);
		
		this.add(votePane);
		
		// �������� ������Ʈ ����
		notificationPane.setSize(400, 235);
		notificationPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		notificationPane.setLocation(620, 255);
		
		notificationBasePanel.setLayout(new BorderLayout(0,0));
		notificationColumnPanel.setLayout(new BoxLayout(notificationColumnPanel, BoxLayout.Y_AXIS));
		
//		NotificationBox nb = new NotificationBox();
//		notificationColumnPanel.add(nb);
		notificationBasePanel.add(notificationColumnPanel);
		
		notificationPane.setViewportView(notificationBasePanel);		
		this.add(notificationPane);
		
		// ��ǥ ����, �ݴ�, ���� ��ư ����
		voteAssentButton.setSize(120, 40);
		voteAssentButton.setLocation(620, 500);
		voteAssentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				assent_msg = new VotingUI("���� ���� �Ͻðڽ��ϱ�?", WorknoteUI.this.member_ID);
				assent_msg.setController(votingController);
				assent_msg.setVoteList(votes);
				assent_msg.setAssent(true);
				assent_msg.setVoteBoxes(voteBoxes);
			}
		});
		this.add(voteAssentButton);
		
		voteOppositeButton.setSize(120, 40);
		voteOppositeButton.setLocation(760, 500);
		voteOppositeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				assent_msg = new VotingUI("���� �ݴ� �Ͻðڽ��ϱ�?", WorknoteUI.this.member_ID);
				assent_msg.setController(votingController);
				assent_msg.setVoteList(votes);
				assent_msg.setAssent(false);
				assent_msg.setVoteBoxes(voteBoxes);
			}
		});
		this.add(voteOppositeButton);
		
		voteCloseButton.setSize(120, 40);
		voteCloseButton.setLocation(900, 500);
		this.add(voteCloseButton);
		
		// �¶��� ȸ�� ������Ʈ ����
		onlineUserPane.setSize(200, 235);
		onlineUserPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		onlineUserPane.setLocation(1040, 10);
		this.add(onlineUserPane);
		
		offlineUserPane.setSize(200, 235);
		offlineUserPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		offlineUserPane.setLocation(1040, 255);
		this.add(offlineUserPane);
		
		// ������ ��ư ����
		exitButton.setSize(200, 40);
		exitButton.setLocation(1040, 500);
		this.add(exitButton);
		
		setVisible(true);

	}

	public void repaintContents() {
		//1. ��ǥ ������Ʈ �׸���.
		System.out.println("client: repaint() is called.");
		voteColumnPanel.removeAll();
		voteBoxes = new LinkedList<VoteBox>();
		
		for(int i = 0; i < votes.getVoteList().size(); i++) {
			voteBoxes.add(new VoteBox(votes.getVoteAt(i)));
			voteColumnPanel.add(voteBoxes.get(i));
			System.out.println("client: " + "votes.getVoteAt("+i+")-> " + votes.getVoteAt(i).getVote_title()+";"
					+ " assent : " + votes.getVoteAt(i).getAssent_number()+"; opposite : " + votes.getVoteAt(i).getOpposite_number());
		}
		voteColumnPanel.validate();
		
		//2. �������� ������Ʈ �׸���.
		notificationColumnPanel.removeAll();
		for(int i = 0; i < notifs.getNotificationList().size(); i++) {
			notificationBoxes.add(new NotificationBox(notifs.getNotificationAt(i)));
			notificationColumnPanel.add(notificationBoxes.get(i));
		}
		notificationColumnPanel.validate();
		
		this.repaint();
	}
}
