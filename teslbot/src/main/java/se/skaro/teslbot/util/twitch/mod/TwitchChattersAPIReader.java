package se.skaro.teslbot.util.twitch.mod;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import se.skaro.teslbot.config.ExternalConfigComponents;
import se.skaro.teslbot.config.GsonFactory;
import se.skaro.teslbot.util.twitch.mod.json.TwitchApiJson;

/**
 * The Class TwitchAPIReader. Used to read the tmi.twitch api.
 */
@Component
public class TwitchChattersAPIReader {
	
	@Autowired
	private ExternalConfigComponents config;
	
	@Autowired 
	private GsonFactory gsonFactory;
	
	public List<String> getModList(String channel){
		String url = config.getTwitchApiUrl().replace("USER", channel.replace(config.getChannelPrefix(), ""));
		String jsonString = readURL(url);
		
		Gson gson = gsonFactory.getGson();
		
		TwitchApiJson json = gson.fromJson(jsonString, TwitchApiJson.class);
		
		return Arrays.asList(json.getChatters().getMods());
	}
	
	
	private String readURL(String url) {

		try (InputStream input = new URL(url).openStream()) {
			return IOUtils.toString(input, "UTF-8");
		}

		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

}
