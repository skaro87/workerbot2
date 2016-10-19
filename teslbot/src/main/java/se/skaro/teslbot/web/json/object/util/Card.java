package se.skaro.teslbot.web.json.object.util;

import com.google.gson.annotations.SerializedName;

public class Card {
	
	@SerializedName(value = "Guid")
	private Guid guid;

	public Guid getGuid() {
		return guid;
	}

	
}
