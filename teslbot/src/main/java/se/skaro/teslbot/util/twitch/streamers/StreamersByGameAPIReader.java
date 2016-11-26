package se.skaro.teslbot.util.twitch.streamers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import se.skaro.teslbot.config.ExternalConfigComponents;
import se.skaro.teslbot.config.GsonFactory;
import se.skaro.teslbot.util.twitch.streamers.json.StreamersByGameJson;

@Component
public class StreamersByGameAPIReader {
	
	@Autowired
	private ExternalConfigComponents config;
	
	@Autowired
	private GsonFactory gsonFactory;
	
	private Gson gson;
	
	private LocalDateTime lastApiCall;
	private HashSet<String> liveChannels;
	
	@PostConstruct
	public void postConstruct(){
		gson = gsonFactory.getGson();
		liveChannels = new HashSet<>();
		lastApiCall = LocalDateTime.now().minusMinutes(15);
		getCurrentHEXStreamers();
		getCurrentHEXStreamers();
	}
	
	public synchronized Set<String> getCurrentHEXStreamers(){
		
		if (lastApiCall.plusMinutes(10).isBefore(LocalDateTime.now())){
			
			liveChannels.clear();
			StreamersByGameJson data = gson.fromJson(readUrl(), StreamersByGameJson.class);
			data.getStreams().forEach(s -> {
				liveChannels.add(s.getChannel().getName());
			});
			
			lastApiCall = LocalDateTime.now();
		}
		
		return liveChannels;
	}
	
	public synchronized Set<String> setStreamAsOnline(String user){
		getCurrentHEXStreamers();
		liveChannels.add(user);
		return liveChannels;
	}
	
	
	private String readUrl(){
		
		String url = config.getTwitchGameUrl();
		String clientId = config.getTwitchClientId();
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);

		// add request header
		request.addHeader("Client-ID", clientId);

		try {
			HttpResponse response = client.execute(request);
			return EntityUtils.toString(response.getEntity());
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
