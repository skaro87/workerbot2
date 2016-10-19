package se.skaro.teslbot.web.json.object.util;

import com.google.gson.annotations.SerializedName;

public class Guid {
	
	@SerializedName(value = "m_Guid")
	private String guid;

	@Override
	public String toString() {
		return guid;
	}
	
	

}
