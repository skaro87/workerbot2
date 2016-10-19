package se.skaro.teslbot.web.json.object.collection;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import se.skaro.teslbot.web.json.object.util.Card;


public class CollectionEvent {

	@SerializedName(value = "Action")
	private String action;

	@SerializedName(value = "CardsAdded")
	private List<Card> cardsAdded;

	@SerializedName(value = "CardsRemoved")
	private List<Card> cardsRemoved;

	public String getAction() {
		return action;
	}

	public List<Card> getCardsAdded() {
		return cardsAdded;
	}

	public List<Card> getCardsRemoved() {
		return cardsRemoved;
	}

}
