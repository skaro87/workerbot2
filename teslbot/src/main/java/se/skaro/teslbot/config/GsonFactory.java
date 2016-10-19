package se.skaro.teslbot.config;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class GsonFactory {
	
	private Gson gson;
	
	@PostConstruct
	public void build(){
		GsonBuilder gsonBuilder = new GsonBuilder();
		gson = gsonBuilder.create();
	}
	
	public Gson getGson(){
		return gson;
	}

}
