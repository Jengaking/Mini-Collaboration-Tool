package basesesign;

public class Member {
	private String member_ID;
	private String name;
	private String division;
	private String phone_number;
	private boolean online;
	private int serial;

	public Member(String member_ID, String name, String division, String phone_number, boolean online, int serial) {
		super();
		this.member_ID = member_ID;
		this.name = name;
		this.division = division;
		this.phone_number = phone_number;
		this.online = online;
		this.serial = serial;
	}

	// getters and setters
	public String getMember_ID() {
		return member_ID;
	}

	public void setMember_ID(String member_ID) {
		this.member_ID = member_ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public int getSerial() {
		return serial;
	}

	public void setSerial(int serial) {
		this.serial = serial;
	}

}
