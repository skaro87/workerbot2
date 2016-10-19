package se.skaro.teslbot.web.guiddatacollection.json;

import com.google.gson.annotations.SerializedName;

public class DocXCard {
	
	@SerializedName(value = "name")
	private String name;
	
	@SerializedName(value = "uuid")
	private String guid;

	public String getName() {
		return name;
	}

	public String getGuid() {
		return guid;
	}
	
	
}
