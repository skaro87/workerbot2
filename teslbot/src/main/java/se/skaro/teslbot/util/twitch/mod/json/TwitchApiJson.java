package se.skaro.teslbot.util.twitch.mod.json;

// TODO: Auto-generated Javadoc
/**
 * The Class TwitchApiJson. Used to read JSON from the api with GSON.
 */
public class TwitchApiJson {
	
	/** The chatters. */
	private Chatters chatters;
	
	public Chatters getChatters(){
		return chatters;
	}
	
	

	public class Chatters{
		
		/** The moderators. */
		private String[] moderators;
		
		public String[] getMods(){
			return moderators;
		}
		
	}
	
	

}
