package se.skaro.teslbot.web.json.object.draft;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import se.skaro.teslbot.web.json.object.util.Card;


public class DraftPack {
	
	@SerializedName(value = "Cards")
	private List<Card> cards;
	
	@SerializedName(value = "User")
	private String user;

	public List<Card> getCards() {
		return cards;
	}
	
	public String getUser() {
		return user;
	}

	public int getPick() {
		return (17 - cards.size()) +1;
	}
	
	

}
