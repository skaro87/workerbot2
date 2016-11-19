package se.skaro.teslbot.web.data.apicache;

import org.apache.commons.lang3.StringUtils;

import se.skaro.teslbot.config.ApiOutputMessageStrings;

public class Draft extends Status{

	private int pack;
	private int pick;

	private boolean cardPicked;
	private String lastPickedCard;

	public Draft(String ign) {
		this.ign = ign;
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

	@Override
	public String getMessage() {
		if (cardPicked) {
			return ign + " is currently drafting. Pack " + pack + ", pick " + pick + ". Picked " + lastPickedCard;
		}
		return ign + " is currently drafting. Pack " + pack + ", pick " + pick;
	}

	@Override
	public String sendMessage() {
		if (pack == 1 && pick == 1){
			return replacePlaceholders(ApiOutputMessageStrings.getDraftStartMessage());
		}
		return null;
	}

	@Override
	protected String replacePlaceholders(String message) {
		return message.replace("YOURNAME", StringUtils.capitalize(ign));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + pack;
		result = prime * result + pick;
		return result;
	}

	
	
	
	

}
