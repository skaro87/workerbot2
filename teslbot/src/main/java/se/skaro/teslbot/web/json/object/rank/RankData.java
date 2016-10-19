package se.skaro.teslbot.web.json.object.rank;

import com.google.gson.annotations.SerializedName;

public class RankData {
	
	@SerializedName(value = "Tier")
	private int tier;
	
	@SerializedName(value = "Division")
	private int division;
	
	@SerializedName(value = "CosmicRank")
	private int cosmicRank;
	
	@SerializedName(value = "Type")
	private String type;
	
	@SerializedName(value = "User")
	private String user;

	public int getTier() {
		return tier;
	}

	public int getDivision() {
		return division;
	}

	public int getCosmicRank() {
		return cosmicRank;
	}

	public String getType() {
		return type;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getTier(tier));
		sb.append(" rank ");
		if (tier != 1){
			sb.append(getDivision(division));
		}
		else {
			sb.append(cosmicRank);
		}
		
		return sb.toString();
	}
	
	private String getTier(int tier){
		switch (tier){
		case 5: return "Bronze";
		case 4: return "Silver";
		case 3: return "Gold";
		case 2: return "Platinum";
		case 1: return "Cosmic";
		default: return "";
		}
	}
	
	private int getDivision(int division){
		return 5 - division;
	}
	
	

}
