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
 * 작성자 : 이원준
 */

// 메인 워크노트 화면 UI
public class WorknoteUI extends JFrame {
	// 기본 멤버
	private int worknote_number = 0;
	private String member_ID = null;
	private ServerConnector serverConnector;
	
	// 버튼
	private JButton ntfCreateButton = new JButton("공지 등록");
	private JButton ntfRemoveButton = new JButton("공지 삭제");
	private JButton voteCreateButton = new JButton("투표 등록");
	private JButton voteRemoveButton = new JButton("투표 삭제");
	private JButton voteCloseButton = new JButton("투표 마감");
	private JButton voteAssentButton = new JButton("찬성");
	private JButton voteOppositeButton = new JButton("반대");
	private JButton chatTransferButton = new JButton("전송");
	private JButton exitButton = new JButton("나가기");

	// 채팅 컴포넌트
	private JTextField chatField = new JTextField();
	private JTextArea chattingBoard = new JTextArea();
	private JScrollPane chatPane = new JScrollPane();

	// 투표 컴포넌트
	private JScrollPane votePane = new JScrollPane();
	private JPanel voteBasePanel = new JPanel();
	private JViewport voteViewport = new JViewport();
	private JPanel voteColumnPanel = new JPanel();
	private LinkedList<VoteBox> voteBoxes = new LinkedList<VoteBox>();

	// 공지사항 컴포넌트
	private JScrollPane notificationPane = new JScrollPane();
	private JPanel notificationBasePanel = new JPanel();
	private JViewport notificationViewport = new JViewport();
	private JPanel notificationColumnPanel = new JPanel();
	private LinkedList<NotificationBox> notificationBoxes = new LinkedList<NotificationBox>();

	// 접속 회원 컴포넌트
	private JScrollPane onlineUserPane = new JScrollPane();
	private JScrollPane offlineUserPane = new JScrollPane();
	private JTextArea onlinePanel = new JTextArea();
	private JTextArea offlinePanel = new JTextArea();

	// 공지사항 버튼 보더링
	private Border notifBorder = BorderFactory.createTitledBorder("공지사항");
	private JPanel notifBntPanel = new JPanel();

	// 투표 버튼 보더링
	private Border voteBorder = BorderFactory.createTitledBorder("투표");
	private JPanel voteBntPanel = new JPanel();

	// 출력을 위한 엔터티 클래스 변수
	private VoteList votes = new VoteList();
	private NotificationList notifs = new NotificationList();
	
	
	// votingUI
	private VotingUI assent_msg = null;
	private VotingUI opposite_msg = null;
	
	// 컨트롤 클래스 변수
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
		setTitle("워크노트");
		setLayout(null);

		System.out.println("client: make a connection with a server.");
		serverConnector = new ServerConnector(votes, notifs, this);
		
		// 컨트롤 객체 생성
		voteCreateController = new VoteCreator(this.member_ID, this.worknote_number, serverConnector);
		votingController = new VoteParticipationController(this.member_ID, this.worknote_number, serverConnector, votes);
		notifCreateController = new NotificationCreator(this.member_ID, this.worknote_number, serverConnector);
		notifRemoveController = new NotificationRemover(this.member_ID, this.worknote_number, serverConnector);
		
		
		// 공지사항 등록, 삭제 버튼 설정
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
				nrui = new NotificationRemoveUI("정말 삭제하시겠습니까?", WorknoteUI.this.member_ID);
				nrui.setController(notifRemoveController);
				nrui.setNotificationBoxes(notificationBoxes);
				nrui.setNotificationList(notifs);
				
			}
		});
		notifBntPanel.add(ntfCreateButton);
		notifBntPanel.add(ntfRemoveButton);
		this.add(notifBntPanel);

		// 투표 등록, 삭제 버튼 설정
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
		
		// 채팅보드 설정
		chatPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		chatPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		chatPane.setSize(600, 400);
		chatPane.setLocation(10, 90);
		
		chattingBoard.setEditable(false);
		
		JViewport temp = chatPane.getViewport();
		temp.add(chattingBoard);
		chatPane.setViewport(temp);
		
		this.add(chatPane);
		
		// 채팅 입력 창, 버튼 설정
		chatField.setSize(520, 40);
		chatField.setLocation(10, 500);
		this.add(chatField);
		
		chatTransferButton.setSize(75,40);
		chatTransferButton.setLocation(535, 500);
		this.add(chatTransferButton);
		
		// 투표 컴포넌트 설정
		votePane.setSize(400, 235);
		votePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		votePane.setLocation(620,10);
		
		voteBasePanel.setLayout(new BorderLayout(0,0));
		voteColumnPanel.setLayout(new BoxLayout(voteColumnPanel, BoxLayout.Y_AXIS));
		
		// 테스팅 코드.
		//public Vote(int vote_number, String title, String date, int assent_number, int opposite_number, String owner, boolean activation, boolean voted)
		//int vote_number, String title, String date, int assent_number, int opposite_number, String owner, boolean activation,boolean voted
//		VoteBox vb = new VoteBox(new Vote(123, worknote_number, "달걀이 먼저냐 닭이 먼저냐", "2023-01-04", 0, 0, "dnjswns648", false, false));
//		voteColumnPanel.add(vb);
		voteBasePanel.add(voteColumnPanel, BorderLayout.CENTER);
		
		votePane.setViewportView(voteBasePanel);
		
		this.add(votePane);
		
		// 공지사항 컴포넌트 설정
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
		
		// 투표 찬성, 반대, 마감 버튼 설정
		voteAssentButton.setSize(120, 40);
		voteAssentButton.setLocation(620, 500);
		voteAssentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				assent_msg = new VotingUI("정말 찬성 하시겠습니까?", WorknoteUI.this.member_ID);
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
				assent_msg = new VotingUI("정말 반대 하시겠습니까?", WorknoteUI.this.member_ID);
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
		
		// 온라인 회원 컴포넌트 설정
		onlineUserPane.setSize(200, 235);
		onlineUserPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		onlineUserPane.setLocation(1040, 10);
		this.add(onlineUserPane);
		
		offlineUserPane.setSize(200, 235);
		offlineUserPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		offlineUserPane.setLocation(1040, 255);
		this.add(offlineUserPane);
		
		// 나가기 버튼 설정
		exitButton.setSize(200, 40);
		exitButton.setLocation(1040, 500);
		this.add(exitButton);
		
		setVisible(true);

	}

	public void repaintContents() {
		//1. 투표 컴포넌트 그리기.
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
		
		//2. 공지사항 컴포넌트 그리기.
		notificationColumnPanel.removeAll();
		for(int i = 0; i < notifs.getNotificationList().size(); i++) {
			notificationBoxes.add(new NotificationBox(notifs.getNotificationAt(i)));
			notificationColumnPanel.add(notificationBoxes.get(i));
		}
		notificationColumnPanel.validate();
		
		this.repaint();
	}
}
