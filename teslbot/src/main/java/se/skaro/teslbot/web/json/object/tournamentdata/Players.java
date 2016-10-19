package se.skaro.teslbot.web.json.object.tournamentdata;

import com.google.gson.annotations.SerializedName;

public class Players {
	
	@SerializedName(value = "Wins")
	private int wins;
	
	@SerializedName(value = "Losses")
	private int losses;
	
	@SerializedName(value = "Points")
	private int points;
	
	@SerializedName(value = "Name")
	private String name;

	public int getWins() {
		return wins;
	}

	public int getLosses() {
		return losses;
	}

	public int getPoints() {
		return points;
	}

	public String getName() {
		return name;
	}
	
	

}
