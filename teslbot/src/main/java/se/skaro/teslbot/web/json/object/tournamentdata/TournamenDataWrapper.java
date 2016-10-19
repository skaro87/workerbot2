package se.skaro.teslbot.web.json.object.tournamentdata;

import com.google.gson.annotations.SerializedName;

public class TournamenDataWrapper {
	
	@SerializedName(value = "TournamentData")
	private TournamentData tournamentData;
	
	@SerializedName(value = "User")
	private String user;

	public TournamentData getTournamentData() {
		return tournamentData;
	}
	
	public String getUser() {
		return user;
	}
	
	

}
