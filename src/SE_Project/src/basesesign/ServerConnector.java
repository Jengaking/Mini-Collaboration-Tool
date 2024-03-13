package basesesign;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import serverside.ServerController;

public class ServerConnector implements Runnable {
	private Socket socket = null;
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;
	private VoteList votes = null;
	private NotificationList notifs = null;
	private WorknoteUI parent = null;

	public ServerConnector(VoteList vl, NotificationList nl, WorknoteUI parentsF) {
		try {
			System.out.println("client: server connector constructor.");

			socket = new Socket(ServerController.IP_ADDR, ServerController.SERVER_PORT);
			System.out.println("client: socket is now connected.");

			oos = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("client: output stream is connected.");
			
			InputStream is = socket.getInputStream();
			System.out.println("client: input stream is extracted.");
			ois = new ObjectInputStream(is);
			System.out.println("client: input stream is connected.");
			
			// 연결 테스트 코드;
//			oos.writeObject(new String("test contents"));
//			oos.flush();
			//
			this.votes = vl;
			this.notifs = nl;
			this.parent = parentsF;

			Thread th = new Thread(this);
			th.start();

		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public void transfer(RequestPackage rp) {
		try {
			oos.writeObject(rp);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		ResponsePackage rpp = null;
		while (true) {
			System.out.println("client: listening...");
			try {
				rpp = (ResponsePackage)ois.readObject();
				System.out.println("client: response package just arrived(response type : " + rpp.getResponseType()+")");
				switch (rpp.getResponseType()) {
				case ResponsePackage.NOTIF_CREATE:
					this.notifs.addNewNotification((Notification)rpp.getResponseObj());
					break;
					
				case ResponsePackage.NOTIF_REMOVE:
					this.notifs.removeNotification(((Notification)rpp.getResponseObj()).getNotification_number());
					break;
					
				case ResponsePackage.VOTE_CAST:
					System.out.println("client: VOTE_CAST, vote info = " + (Vote)(rpp.getResponseObj()));
					if(rpp.assent == 1) {
						this.votes.castAssent(((Vote)rpp.getResponseObj()).getVote_number());
					} else {
						this.votes.castOpposite(((Vote)rpp.getResponseObj()).getVote_number());
					}
					break;
					
				case ResponsePackage.VOTE_CREATE:
					System.out.println("client: listen_VOTE_CREATE");
					this.votes.addNewVote((Vote)rpp.getResponseObj());
					break;
					
				default:
					break;
				}
				System.out.println("client: response package was processed.");
				System.out.println("client: now repaintContents() will be called.");
				
				parent.repaintContents();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return;
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
	}
}
