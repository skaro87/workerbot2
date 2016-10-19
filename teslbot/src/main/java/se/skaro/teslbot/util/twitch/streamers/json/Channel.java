package se.skaro.teslbot.util.twitch.streamers.json;

import com.google.gson.annotations.SerializedName;

public class Channel {
	
	@SerializedName(value = "name")
	private String name;

	public String getName() {
		return name;
	}

	
}
