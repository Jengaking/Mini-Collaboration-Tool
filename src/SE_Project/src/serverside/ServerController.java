package serverside;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import basesesign.Notification;
import basesesign.RequestPackage;
import basesesign.ResponsePackage;
import basesesign.Vote;

/*
 * author : 이원준
 * date : 2023-05-29
 * classify : Server Side package
 */

public class ServerController {
	public static final int SERVER_PORT = 8021;
	public static final String IP_ADDR = "127.0.0.1";

	private ServerSocket listener = null;
	private Socket socket1 = null;
	private Socket socket2 = null;

	private ObjectInputStream oip1 = null;
	private ObjectOutputStream oop1 = null;

	private ObjectInputStream oip2 = null;
	private ObjectOutputStream oop2 = null;

	private SQLController sqlc = new SQLController();

	private ExecutorService threadPool = Executors.newFixedThreadPool(10);

	// insert 는 statement에서 execute
	// update votes set assent_number = vote.assent_number
	public static final String sqlBody_vote_cast_assent = "UPDATE votes set assent_number = ";
	public static final String sqlBody_vote_cast_opposite = "UPDATE votes SET opposite_number = ";
	public static final String sqlBody_vote_create = "INSERT INTO votes VALUES(";
	public static final String sqlBody_notif_remove = "DELETE FROM notification WHERE ";
	public static final String sqlBody_notif_create = "INSERT INTO notification VALUES(";

	public ServerController() {
		try {
			System.out.println("server: ServerController has been generated.");
			listener = new ServerSocket(SERVER_PORT);
			System.out.println("server: ServerSocket. now available.");
			socket1 = listener.accept();
			System.out.println("sever: client 1. now accepted");

			try {
				Thread.sleep(100L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			oop1 = new ObjectOutputStream(socket1.getOutputStream());
			System.out.println("server: client 1. ObjectOutputStream is connected.");

			InputStream is = socket1.getInputStream();
			oip1 = new ObjectInputStream(is);
			System.out.println("sever: client 1. ObjectInputStream is connected.");
			
//			try {
//				String recived = (String) oip1.readObject();
//				System.out.println(recived);
//			} catch (IOException e) {
//				System.out.println(e.getMessage());
//				e.printStackTrace();
//			} catch (ClassNotFoundException e) {
//				System.out.println(e.getMessage());
//				e.printStackTrace();
//			}
			
			socket2 = listener.accept();
			System.out.println("sever: client 2. now accepted");

			oop2 = new ObjectOutputStream(socket2.getOutputStream());
			oip2 = new ObjectInputStream(socket2.getInputStream());
			System.out.println("server: client 2. Streams are connected.");

			ClientHandler ch1 = new ClientHandler(socket1, oip1, oop1, oip2, oop2);
			ClientHandler ch2 = new ClientHandler(socket2, oip2, oop2, oip1, oop1);

			threadPool.execute(ch1);
			System.out.println("server: client 1. thread is running now");
			threadPool.execute(ch2);
			System.out.println("server: client 2. thread is running now");

		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}

class ClientHandler implements Runnable {
	private Socket clientSocket = null;
	private ObjectInputStream ois;
	private ObjectInputStream ois2;

	private ObjectOutputStream oos;
	private ObjectOutputStream oos2;

	private static SQLController sqlc = new SQLController();

	public ClientHandler(Socket cs, ObjectInputStream ois, ObjectOutputStream oos, ObjectInputStream ois2,
			ObjectOutputStream oos2) {
		this.clientSocket = cs;
		this.ois = ois;
		this.oos = oos;
		this.ois2 = ois2;
		this.oos2 = oos2;
	}

	public void run() {
		System.out.println("server: client run method is called and now approaching to loop.");
		while (true) {
			try {
				System.out.println("server: client listening loop is now waiting.");
				RequestPackage rp = (RequestPackage)ois.readObject();
				System.out.println("server : requestType = " + rp.getRequestType());
				System.out.println("server: sever recieved a RequestPackage.");
				ResponsePackage rpp = null;
				/*
				 * NOTIF_CREATE = 50; NOTIF_REMOVE = 12; VOTE_CREATE = 67; VOTE_REMOVE = 0;
				 * VOTE_CAST = 100; VOTE_CLOSE = 0; CHAT_TRANS = 0; ERROR_TYPE = -1;
				 */
				switch (rp.getRequestType()) {
				case 50:
					System.out.println("server: RequestType = " + rp.getRequestType() + " -> crate a notification. (" + rp.getUser() +")");
					rpp = notifCreation(rp);
					break;
				case 12:
					System.out.println("server: RequestType = " + rp.getRequestType() + " -> remove a notification. (" + rp.getUser()+")");
					rpp = notifRemoval(rp);
					break;
				case 67:
					System.out.println("server: RequestType = " + rp.getRequestType() + " -> crate a vote.(" + rp.getUser()+")");
					rpp = voteCreation(rp);
					break;
				case 100:
					System.out.println("server: RequestType = " + rp.getRequestType() + " -> cast a voting. (" + rp.getUser()+")");
					rpp = voteCasting(rp);
					System.out.println("server: Vote info : " + (Vote)rpp.getResponseObj());
					break;
				default:
					System.err.println("Not defined functions.");
				}
				if(rpp != null) System.out.println("Server: Response Type will be returned ( response Type : " + rpp.getResponseType()+")");
				else System.out.println("Server: rpp is a null");
				
				oos.writeObject(rpp);
				oos.flush();
				
				if(rpp.getResponseType() == 100) {
					Vote v = (Vote)(rpp.getResponseObj());
					rpp.setResponseObj(v);
				}
				oos2.writeObject(rpp);
				oos2.flush();

			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				return;
			} catch (ClassNotFoundException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				return;
			}
		}
	}

	// 새로운 투표 객체를 DB에 삽입한다.
	// 이 객체를 모든 사용자에게 전송한다.
	// "INSERT INTO votes VALUES(";
	/*
	 * private int vote_number; private String vote_title; private String date;
	 * private int assent_number; private int opposite_number; private String owner;
	 * private boolean activation; private boolean voted;
	 */
	public synchronized ResponsePackage voteCreation(RequestPackage rp) {
		ResponsePackage rsp = new ResponsePackage(null, -1);
		Vote vote = (Vote)(rp.getRequestObj());
		vote.setVote_number(((int)(Math.random()*3000)+1));
		System.out.println(vote.getVote_title());
		System.out.println("server: Vote creation : vote info: " + vote);
		String sql = ServerController.sqlBody_vote_create + vote.getVote_number() + ", " + vote.getWorknoteNumber()
				+ ", \'" + vote.getDate() + "\', \'" + vote.getVote_title() + "\', " + vote.getAssent_number() + ", "
				+ vote.getOpposite_number() + ", \'" + vote.getOwner() + "\', " + vote.isActivated() + ");";
		vote.setActivation(true);
		if (sqlc.doInsert(sql)) {
			rsp.setResponseObj(vote);
			rsp.setResponseType(67);
		}
		return rsp;
	}

	// 전달받은 투표 객체의 정보와 같은 튜플을 찾아 투표 표 값을 변경하고 이걸 모든 사용자에게 전달한다.
	public synchronized ResponsePackage voteCasting(RequestPackage rp) {
		ResponsePackage rsp = new ResponsePackage(null, -1);
		Vote vote = (Vote)rp.getRequestObj();
		String sql = null;
		System.out.println("server: vote casting: vote info = " + vote);
		if (rp.getRequestMessage().equals("vote_cast_assent")) {
			vote.setAssent_number(vote.getAssent_number());
			rsp.assent = 1;
			sql = ServerController.sqlBody_vote_cast_assent + (vote.getAssent_number()+1);
		} else if (rp.getRequestMessage().equals("vote_cast_opposite")) {
			vote.setOpposite_number(vote.getOpposite_number());
			rsp.assent = 0;
			sql = ServerController.sqlBody_vote_cast_opposite + (vote.getOpposite_number()+1);
		}

		sql = sql + " WHERE vote_number = " + vote.getVote_number() + " AND worknote_number = "
				+ vote.getWorknoteNumber() + ";";
		System.out.println("server : voteCastring - sql = " + sql);
		if (sqlc.doUpdate(sql)) {
			rsp.setResponseObj(vote);
			rsp.setResponseType(ResponsePackage.VOTE_CAST);
		}
		rsp.setResponseType(100);
		return rsp;
	}

	// INSERT INTO notification VALUES(
	/*
	 * private int notification_number; private int worknote_number; private String
	 * date; private String contents; private String owner;
	 */

	public synchronized ResponsePackage notifCreation(RequestPackage rp) {
		ResponsePackage rsp = new ResponsePackage(null, -1);
		Notification notif = (Notification) rp.getRequestObj();
		notif.setNotification_number(((int)(Math.random()*3000)+1));
		System.out.println(notif.getContents());
		
		String sql = ServerController.sqlBody_notif_create + notif.getNotification_number() + ","
				+ notif.getWorknote_number() + ", \'" + notif.getDate() + "\', \'" + notif.getContents() + "\', \'"
				+ notif.getOwner() + "\');";
		
		System.out.println(sql);
		
		if (sqlc.doInsert(sql)) {
			rsp.setResponseObj(notif);
			rsp.setResponseType(ResponsePackage.NOTIF_CREATE);
		}
		return rsp;
	}

	public synchronized ResponsePackage notifRemoval(RequestPackage rp) {
		ResponsePackage rsp = new ResponsePackage(null, -1);
		Notification notif = (Notification) rp.getRequestObj();
		String sql = ServerController.sqlBody_notif_remove + "notification_number = " + notif.getNotification_number()
				+ " AND worknote_number = " + notif.getWorknote_number() + ";";
		if (sqlc.doDelete(sql)) {
			rsp.setResponseObj(notif);
			rsp.setResponseType(ResponsePackage.NOTIF_REMOVE);
		}

		return rsp;
	}
}