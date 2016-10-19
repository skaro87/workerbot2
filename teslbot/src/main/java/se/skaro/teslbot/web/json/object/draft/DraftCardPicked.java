package se.skaro.teslbot.web.json.object.draft;

import com.google.gson.annotations.SerializedName;

import se.skaro.teslbot.web.json.object.util.Card;


public class DraftCardPicked {
	
	@SerializedName(value = "Card")
	private Card card;
	
	@SerializedName(value = "User")
	private String user;

	public Card getCard() {
		return card;
	}

	public String getUser() {
		return user;
	}
	
	
	
	

}
