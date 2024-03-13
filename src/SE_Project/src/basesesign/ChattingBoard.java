package basesesign;

import java.util.Vector;

public class ChattingBoard {
	private Vector<Chat> chatList = new Vector<Chat>();

	public ChattingBoard() {
	}

	public Vector<Chat> getChatList() {
		return chatList;
	}

	public void setChatList(Vector<Chat> chatList) {
		this.chatList = chatList;
	}

	public void addNewChat(Chat nc) {
		this.chatList.add(nc);
	}

	public Chat removeChat(int chat_number) {
		Chat c = null;
		for (int i = 0; i < chatList.size(); i++) {
			if ((c = chatList.get(i)).getChat_number() == chat_number) {
				return chatList.remove(i);
			}
		}
		return null;
	}
	
	public Chat getChatOf(int chat_num) {
		for(int i = 0; i < chatList.size(); i++) {
			if(chatList.get(i).getChat_number() == chat_num) {
				return chatList.get(i);
			}
		}
		return null;
	}
	
	public Chat getChatAt(int index) { return chatList.get(index); }
}
