package se.skaro.teslbot.util.twitch;

import java.util.List;

/**
 * The Class ChannelMods. Used to store a list of mods for a channel. The list expires after 10 minutes.
 */
public class ChannelMods {
	
	/** The mods. */
	private List<String> mods;
	
	/** The time created. */
	private long timeCreated;
	
	/**
	 * Instantiates a new channel mods.
	 *
	 * @param mods the mods
	 */
	public ChannelMods(List<String> mods){
		timeCreated = System.currentTimeMillis();
		this.mods = mods;
	}

	/**
	 * Gets the mods.
	 *
	 * @return a list of the mods
	 */
	public List<String> getMods() {
		return mods;
	}

	/**
	 * Checks for expried.
	 *
	 * @return true, if successful
	 */
	public boolean hasExpried() {
		if (System.currentTimeMillis() > timeCreated + (1000 * 60 * 10)) {
			return true;
		}
		return false;
	}
	
	

}
