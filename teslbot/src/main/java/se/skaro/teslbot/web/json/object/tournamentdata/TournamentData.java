package se.skaro.teslbot.web.json.object.tournamentdata;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class TournamentData {
	
	@SerializedName(value = "ID")
	private String id;
	
	@SerializedName(value = "Games")
	private List<Game> games;
	
	@SerializedName(value = "Players")
	private List<Players> players;
	
	@SerializedName(value = "Style")
	private int style;
	
	@SerializedName(value = "Format")
	private int format;

	public String getId(){
		return id;
	}
	public List<Game> getGames() {
		return games;
	}
	public List<Players> getPlayers() {
		return players;
	}
	public int getStyle() {
		return style;
	}
	public int getFormat() {
		return format;
	}
	
}


