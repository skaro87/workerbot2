package se.skaro.teslbot.bot.commands;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import se.skaro.teslbot.bot.ChatBot;
import se.skaro.teslbot.data.entity.User;

/**
 * The Class GameCommand. Used to set the game settings in the user repository. Channel owner only command.
 */
@Component
public class GameCommand extends AbstractCommand {

	/* (non-Javadoc)
	 * @see se.skaro.teslbot.bot.commands.AbstractCommand#call(se.skaro.teslbot.bot.ChatBot, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void call(ChatBot bot, String sender, String message, String channel) {

		String gameMessage = fixWhiteSpaces(
				getMessageWithoutCommand(config.getMessagePrefix() + "game", message).toLowerCase());

		if (channel.equalsIgnoreCase(config.getChannelPrefix() + sender) && gameMessage.length() >= MINIMUM_SEARCH_LENGTH) {

				//HEX
				if (compareStringFromConfigWithGameMessage(config.getHexGameString(), gameMessage)) {
					setGame(1, sender);
					messageSender.sendMessage(bot, sender, "Game set to HEX: Shards of Fate", channel, false);
				} 
				
				//TES:L
				else if (compareStringFromConfigWithGameMessage(config.getTeslGameString(), gameMessage)) {
					setGame(2, sender);
					messageSender.sendMessage(bot, sender, "Game set to The Elder Scrolls: Legends", channel, false);
				} 
				
				//None
				else if (compareStringFromConfigWithGameMessage(config.getNoneGameString(), gameMessage)) {
					setGame(0, sender);
					messageSender.sendMessage(bot, sender, "Game specific commands disabled", channel, false);
				} 
				
				//No match
				else {
					messageSender.sendMessage(bot, sender, config.getNoGameFoundMessage(), channel, false);
				}

			
		}
		
		else {
			List<User> users = userRepo.findByName(channel.replace(config.getChannelPrefix(),""));
			if (!users.isEmpty()){
				User user = users.get(0);
				switch (user.getGame()){
				case 0: 
					messageSender.sendMessage(bot, sender, config.getNoGameSetMessage(), channel, false);
					break;
				case 1: 
					messageSender.sendMessage(bot, sender, config.getHexGameMessage(), channel, false);
					break;
				case 2: 
					messageSender.sendMessage(bot, sender, config.getTeslGameMessage(), channel, false);
					break;
				default: 
					break;
				}	
				
			}
		}

	}
	
	/**
	 * Sets the game.
	 *
	 * @param game the game
	 * @param name the username
	 */
	private void setGame (int game, String name) {
		List<User> users = userRepo.findByName(name);
		
		if (!users.isEmpty()){
			//TODO: Logic if more than one user is found
			User user = users.get(0);			
			user.setGame(game);
			userRepo.save(user);
		}
	}
	
	/**
	 * Compare string from config with game message.
	 *
	 * @param configString the config string
	 * @param gameMessage the game message
	 * @return true, if successful
	 */
	private boolean compareStringFromConfigWithGameMessage(String configString, String gameMessage) {
		
		List<String> gameStrings = Arrays.asList(configString.split(","));
		
		if (gameStrings.contains(gameMessage)){
			return true;
		}
		
		return false;
	}

}
