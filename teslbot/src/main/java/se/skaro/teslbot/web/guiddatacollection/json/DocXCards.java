package se.skaro.teslbot.web.guiddatacollection.json;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class DocXCards {
	
	@SerializedName(value = "cards")
	private List<DocXCard> cards;

	public List<DocXCard> getCards() {
		return cards;
	}

}
