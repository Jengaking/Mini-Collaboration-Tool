package basesesign;

import java.io.Serializable;

public class Vote implements Serializable{
	private int vote_number;
	private int worknote_number;
	private String vote_title;
	private String date;
	private int assent_number;
	private int opposite_number;
	private String owner;
	private boolean activation;

	public Vote(int vote_number, int worknote_number, String title, String date, int assent_number, int opposite_number, String owner,
			boolean activation) {
		super();
		this.vote_number = vote_number;
		this.vote_title = title;
		this.date = date;
		this.assent_number = assent_number;
		this.opposite_number = opposite_number;
		this.owner = owner;
		this.activation = activation;
		this.worknote_number = worknote_number;
	}

	// getters and setters
	public int getVote_number() {
		return vote_number;
	}

	public void setVote_number(int vote_number) {
		this.vote_number = vote_number;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getAssent_number() {
		return assent_number;
	}

	public void setAssent_number(int assent_number) {
		this.assent_number = assent_number;
	}

	public int getOpposite_number() {
		return opposite_number;
	}

	public void setOpposite_number(int opposite_number) {
		this.opposite_number = opposite_number;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public boolean isActivated() {
		return activation;
	}

	public void setActivation(boolean activation) {
		this.activation = activation;
	}

	public String getVote_title() {
		return vote_title;
	}

	public void setVote_title(String vote_title) {
		this.vote_title = vote_title;
	}

	public int getTotalVote() {
		return this.assent_number + this.opposite_number;
	}
	
	public String toString() {
		return "vote_num = "+this.vote_number+"; wn_num = "+this.worknote_number+"; data = " + date.toString()+"; title = "
				+this.vote_title+"; assent = " + this.assent_number+"; opposite = " + this.opposite_number + "; activated = " +this.activation;
	}

	public int getWorknoteNumber() { return this.worknote_number; }
	
	public void setWorknoteNumber(int worknote_number) { this.worknote_number = worknote_number; }
}
