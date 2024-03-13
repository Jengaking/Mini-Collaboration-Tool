package basesesign;

import java.util.Vector;

public class MemberList {
	private Vector<Member> memberList = new Vector<Member>();

	public MemberList() {
	}

	// getters and setters
	public Vector<Member> getMemberList() {
		return memberList;
	}

	public void setMemberList(Vector<Member> memberList) {
		this.memberList = memberList;
	}

	public void addNewMember(Member m) {
		this.memberList.add(m);
	}

	public Member getMemberAt(int index) {
		return memberList.get(index);
	}

	public Member getMemberOf(int serial) {
		for (int i = 0; i < memberList.size(); i++) {
			if (memberList.get(i).getSerial() == serial) {
				return memberList.get(i);
			}
		}
		return null;
	}

	public Member removeMember(int serial) {
		for (int i = 0; i < memberList.size(); i++) {
			if (memberList.get(i).getSerial() == serial) {
				return memberList.remove(i);
			}
		}
		return null;
	}
}