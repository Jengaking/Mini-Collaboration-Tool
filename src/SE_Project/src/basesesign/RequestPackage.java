package basesesign;

import java.io.Serializable;

public class RequestPackage implements Serializable{
	private Object requestObj = null;
	private int requestType;
	// requestMessage -> 투표 찬성 : vote_cast_assent, 투표 반대 : vote_cast_opposite
	private String requestMessage = null;
	private int worknote_number;
	private String user = null;
	
	public static final int NOTIF_CREATE = 50;
	public static final int NOTIF_REMOVE = 12;
	public static final int VOTE_CREATE = 67;
	public static final int VOTE_REMOVE = 0;
	public static final int VOTE_CAST = 100;
	public static final int VOTE_CLOSE = 0;
	public static final int CHAT_TRANS = 0;
	
	public static final int ERROR_TYPE = -1;
	
	
	public RequestPackage(Object requestObj, int requestType, String message, int worknote_number, String user) {
		super();
		this.requestObj = requestObj;
		this.requestType = requestType;
		this.requestMessage = message;
		this.worknote_number = worknote_number;
		this.user = user;
	}
	
	public Object getRequestObj() {
		return requestObj;
	}
	public void setRequestObj(Object requestObj) {
		this.requestObj = requestObj;
	}
	public int getRequestType() {
		return requestType;
	}
	public void setRequestType(int requestType) {
		this.requestType = requestType;
	}
	
	public void setRequestMessage(String message) {
		this.requestMessage = message;
	}
	
	public String getRequestMessage() {
		return this.requestMessage;
	}

	public int getWorknote_number() {
		return worknote_number;
	}

	public void setWorknote_number(int worknote_number) {
		this.worknote_number = worknote_number;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	
}
