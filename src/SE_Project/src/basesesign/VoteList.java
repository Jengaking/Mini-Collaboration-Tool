package basesesign;

import java.util.Vector;

public class VoteList {
	private Vector<Vote> voteList = new Vector<Vote>();

	public VoteList() {

	}

	public Vector<Vote> getVoteList() {
		return voteList;
	}

	public void setVoteList(Vector<Vote> voteList) {
		this.voteList = voteList;
	}

	public void addNewVote(Vote vote) {
		voteList.add(vote);
	}

	public Vote removeVote(int vote_number) {
		for (int i = 0; i < voteList.size(); i++) {
			if (voteList.get(i).getVote_number() == vote_number) {
				return voteList.remove(i);
			}
		}
		return null;
	}

	public Vote getVoteAt(int index) {
		return voteList.get(index);
	}

	public Vote getVoteOf(int vote_number) {
		for (int i = 0; i < voteList.size(); i++) {
			if (voteList.get(i).getVote_number() == vote_number) {
				return voteList.get(i);
			}
		}
		return null;
	}

	public void castAssent(int vote_number) {
		Vote v = null;
		for (int i = 0; i < voteList.size(); i++) {
			v = voteList.get(i);
			if (v.getVote_number() == vote_number) {
				v.setAssent_number(v.getAssent_number() + 1);
				break;
			}
		}
	}

	public void castOpposite(int vote_number) {
		Vote v = null;
		for (int i = 0; i < voteList.size(); i++) {
			v = voteList.get(i);
			if (v.getVote_number() == vote_number) {
				v.setOpposite_number(v.getOpposite_number() + 1);
				break;
			}
		}
	}
}
