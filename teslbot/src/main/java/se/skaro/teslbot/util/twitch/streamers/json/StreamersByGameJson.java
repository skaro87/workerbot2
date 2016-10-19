package se.skaro.teslbot.util.twitch.streamers.json;

import java.util.Set;

import com.google.gson.annotations.SerializedName;

public class StreamersByGameJson {
	
	@SerializedName(value = "streams")
	private Set<Streams> streams;

	public Set<Streams> getStreams() {
		return streams;
	}
	
	

}
