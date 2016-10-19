package se.skaro.teslbot.web.data.apicache;

public class Draft {

	private int pack;
	private int pick;

	private boolean cardPicked;
	private String lastPickedCard;

	public Draft() {
		pack = 1;
		pick = 1;
		cardPicked = false;
	}

	public void pickCard(String card) {
		lastPickedCard = card;
		cardPicked = true;
	}

	public void setPick(int pick) {

		if (pick < this.pick) {
			pack++;
			this.pick = 1;
		}
		cardPicked = false;
		this.pick = pick;
	}

	public int getPack() {
		return pack;
	}

	public int getPick() {
		return pick;
	}

	public String getLastPickedCard() {
		return lastPickedCard;
	}

	@Override
	public String toString() {
		if (cardPicked) {
			return "Draft. Pack " + pack + ", pick " + pick + ". Picked " + lastPickedCard;
		}
		return "Draft. Pack " + pack + ", pick " + pick;
	}

}
