package basesesign;

import java.io.Serializable;

public class ResponsePackage implements Serializable{
	private Object responseObj = null;
	private int responseType;
	public int assent = 0;
	public static final int ERROR_TYPE = -1;
	public static final int VOTE_CAST = 100;
	public static final int NOTIF_REMOVE = 12;
	public static final int VOTE_CREATE = 67;
	public static final int NOTIF_CREATE = 50;
	
	public ResponsePackage(Object responseObj, int responseType) {
		super();
		this.responseObj = responseObj;
		this.responseType = responseType;
	}

	public Object getResponseObj() {
		return responseObj;
	}

	public void setResponseObj(Object responseObj) {
		this.responseObj = responseObj;
	}

	public int getResponseType() {
		return responseType;
	}

	public void setResponseType(int responseType) {
		this.responseType = responseType;
	}
	
	
}
