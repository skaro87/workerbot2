package se.skaro.teslbot.util.twitch.streamers.json;

import com.google.gson.annotations.SerializedName;

public class Streams {
	
	@SerializedName(value = "channel")
	private Channel channel;

	public Channel getChannel() {
		return channel;
	}

	
}
