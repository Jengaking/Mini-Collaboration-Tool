package basesesign;

public class VoteCreator {
	private String member_ID;
	private int worknote_number;
	private ServerConnector sc = null;
	
	public VoteCreator(String member_ID, int worknote_number, ServerConnector sc) {
		this.member_ID = member_ID;
		this.worknote_number = worknote_number;
		this.sc = sc;
	}
	
	public void crateVote(Vote v) {
		RequestPackage rp = new RequestPackage(v, RequestPackage.VOTE_CREATE, "vote_creation", worknote_number, member_ID);
		sc.transfer(rp);
	}
}
