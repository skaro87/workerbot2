package se.skaro.teslbot.util.twitch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The Class TwitchModUtil. Used to check the mods for channels.
 */
@Component
public class TwitchModUtil {

	/** The api reader. */
	@Autowired
	private TwitchAPIReader apiReader;

	/** The mod list. */
	private Map<String, ChannelMods> modList;

	@PostConstruct
	public void postConstruct() {
		modList = new HashMap<String, ChannelMods>();
	}

	/**
	 * Checks if user is mod or owner.
	 *
	 * @param user the user
	 * @param channel the channel
	 * @return true, if is mod or owner
	 */
	public boolean isModOrOwner(String user, String channel) {

		if (channel.contains(user)) {
			return true;
		}

		if (!modList.containsKey(channel)) {
			modList.put(channel, new ChannelMods(readAPI(channel)));
		}

		else {
			if (modList.get(channel).hasExpried()) {
				modList.remove(channel);
				modList.put(channel, new ChannelMods(readAPI(channel)));
			}
		}

		boolean isUserMod = modList.get(channel).getMods().contains(user);

		if (modList.get(channel).hasExpried()) {
			modList.remove(channel);
		}

		return isUserMod;

	}

	/**
	 * Reads api.
	 *
	 * @param channel the channel
	 * @return the list
	 */
	public List<String> readAPI(String channel) {
		return apiReader.getModList(channel);
	}

}
