package se.skaro.teslbot.util.twitch.mod;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * The Class TwitchModUtil. Used to check the mods for channels.
 */
@Component
public class TwitchModUtil {

	/** The api reader. */
	@Autowired
	private TwitchChattersAPIReader apiReader;

	/** The mod list. */
	private LoadingCache<String, Optional<List<String>>> modList;

	@PostConstruct
	public void postConstruct() {
		modList = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(10, TimeUnit.MINUTES)
				.build(new CacheLoader<String, Optional<List<String>>>() {
					@Override
					public Optional<List<String>> load(String key) {
						List<String> mods = readAPI(key);
						return Optional.of(mods);
					}
				});
		
		//System.out.println("------------" +isModOrOwner("Celendine", "Skaro87"));
		
	}

	/**
	 * Checks if user is mod or owner.
	 *
	 * @param user
	 *            the user
	 * @param channel
	 *            the channel
	 * @return true, if is mod or owner
	 */
	public boolean isModOrOwner(String user, String channel) {

		if (channel.contains(user)) {
			return true;
		}

		Optional<List<String>> channelMods;
		try {
			channelMods = modList.get(channel);
			if (channelMods.isPresent()) {
				if (channelMods.get().contains(user)) {
					return true;
				}
			}
		} catch (ExecutionException e) {
			//TODO: Error handling
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Reads api.
	 *
	 * @param channel
	 *            the channel
	 * @return the list
	 */
	public List<String> readAPI(String channel) {
		return apiReader.getModList(channel);
	}

}
