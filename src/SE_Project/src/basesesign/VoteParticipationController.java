package basesesign;

import java.util.Vector;

public class VoteParticipationController {
	private String member_ID;
	private int worknote_number;
	private ServerConnector sc = null;
	private VoteList vl = null;
	private Vector<Integer> voted_tbl = new Vector<Integer>();
	
	public VoteParticipationController(String member_ID, int worknote_number, ServerConnector sc, VoteList l) {
		this.member_ID = member_ID;
		this.worknote_number = worknote_number;
		this.sc = sc;
		vl = l;
	}
	public void castVote(Vote vote, boolean assent) {
		String message;
		if(assent) message = "vote_cast_assent";
		else message = "vote_cast_opposite";
		System.out.println("client: Votintg Control");
		voted_tbl.add(vote.getVote_number());
		RequestPackage rp = new RequestPackage(vote, RequestPackage.VOTE_CAST, message, worknote_number, member_ID);
		sc.transfer(rp);
	}
	
	public boolean checkAvailability(int index, String mem_ID) {
		if(!vl.getVoteAt(index).isActivated()) return false;
		for(int i : voted_tbl) {
			if(vl.getVoteAt(index).getVote_number() == i) return false;
		}
//		if(vl.getVoteAt(index).getOwner().equals(mem_ID)) return true;
		return true;
	}
}
