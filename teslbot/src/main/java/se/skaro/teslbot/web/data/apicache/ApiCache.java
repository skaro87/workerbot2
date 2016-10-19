package se.skaro.teslbot.web.data.apicache;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import se.skaro.teslbot.bot.ChatBot;
import se.skaro.teslbot.bot.messages.MessageSender;
import se.skaro.teslbot.config.ExternalConfigComponents;
import se.skaro.teslbot.util.twitch.streamers.StreamersByGameAPIReader;

@Component
public class ApiCache {

	private Cache<String, Optional<ApiUser>> apiCache;

	@Autowired
	private ChatBot bot;

	@Autowired
	private MessageSender messageSender;

	@Autowired
	private StreamersByGameAPIReader streamerApi;

	@Autowired
	private ExternalConfigComponents config;

	@PostConstruct
	public void buildCache() {
		apiCache = CacheBuilder.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES).maximumSize(1000).build();
	}

	private void putOrUpdateApiUser(String key, Optional<ApiUser> apiUser) {
		System.out.println("Putting " + key + " in apiCache");
		apiCache.put(key, apiUser);
	}

	public Optional<ApiUser> getApiUser(String key) {
		try {
			return apiCache.get(key.toLowerCase(), () -> {
				return Optional.empty();
			});
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	public void updateDraft(String key, Draft draft, String ign){
		Optional<ApiUser> apiUser = getApiUser(key);
		Optional<Draft> apiDraft;
		
		if (!apiUser.isPresent()) {
			apiUser = Optional.of(new ApiUser());
			apiDraft = Optional.of(draft);	
		}
		
		else {
			apiDraft = apiUser.get().getDraft();
			if (!apiDraft.isPresent()){
				apiDraft = Optional.of(draft);	
			}
			
		}
		
		apiUser.get().setDraft(draft);
		apiUser.get().setStatus(draft.toString());
		apiUser.get().setIgn(ign);
		System.out.println("Updating draft");
		putOrUpdateApiUser(key, apiUser);
	}

	public void updateConstructedRank(String key, String rank, String ign) {
		Optional<ApiUser> apiUser = getApiUser(key);
		if (!apiUser.isPresent()) {
			apiUser = Optional.of(new ApiUser());
		}
		apiUser.get().setConstructedRank(rank);
		apiUser.get().setIgn(ign);
		System.out.println("Updating constructed rank");
		putOrUpdateApiUser(key, apiUser);
	}

	public void updateLimitedRank(String key, String rank, String ign) {
		Optional<ApiUser> apiUser = getApiUser(key);
		if (!apiUser.isPresent()) {
			apiUser = Optional.of(new ApiUser());
		}
		apiUser.get().setLimitedRank(rank);
		apiUser.get().setIgn(ign);
		System.out.println("Updating constructed rank");
		putOrUpdateApiUser(key, apiUser);
	}

	public void updateStatus(String key, String status, String ign) {
		Optional<ApiUser> apiUser = getApiUser(key);
		if (!apiUser.isPresent()) {
			apiUser = Optional.of(new ApiUser());
		}
		apiUser.get().setStatus(status);
		apiUser.get().setIgn(ign);
		putOrUpdateApiUser(key, apiUser);
	}

	public void updateStatusAndSendMessage(String key, String status, String ign) {
		Optional<ApiUser> lastApiUserState = getApiUser(key);

		if (streamerApi.getCurrentHEXStreamers().contains(key)) {
			if (lastApiUserState.isPresent()) {
				
				if (lastApiUserState.get().getStatus().isPresent()) {
					
					if (!lastApiUserState.get().getStatus().get().equals(status)) {
						messageSender.sendMessage(bot, "skaro87", status, "#skaro87");
					}
				} 
				
				else {
					messageSender.sendMessage(bot, "skaro87", status, "#skaro87");
				}
			} 
			
			else {
				messageSender.sendMessage(bot, "skaro87", status, "#skaro87");
			}
		}

		updateStatus(key, status, ign);

	}

}
