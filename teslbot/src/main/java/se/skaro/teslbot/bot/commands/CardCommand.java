package se.skaro.teslbot.bot.commands;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.skaro.teslbot.bot.ChatBot;
import se.skaro.teslbot.data.entity.User;
import se.skaro.teslbot.data.repository.HEXCardRepository;
import se.skaro.teslbot.data.repository.TESLCardRepository;
import se.skaro.teslbot.data.repository.UserRepository;

/**
 * The Class CardCommand. Used to look up cards.
 */
@Component
public class CardCommand extends AbstractCommand {

	/** The tesl card repository. */
	@Autowired
	private TESLCardRepository teslcardRepository;
	
	/** The hex card repository. */
	@Autowired
	private HEXCardRepository hexcardRepository;
	
	/** The user repository. */
	@Autowired
	private UserRepository userRepository;

	/* (non-Javadoc)
	 * @see se.skaro.teslbot.bot.commands.AbstractCommand#call(se.skaro.teslbot.bot.ChatBot, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void call(ChatBot bot, String sender, String message, String channel) {

		String cardName = fixWhiteSpaces(getMessageWithoutCommand(config.getMessagePrefix() + "card", message));

		if (cardName.length() < MINIMUM_SEARCH_LENGTH) {
			messageSender.sendMessageOrWhisper(bot, sender, "You need at least " + MINIMUM_SEARCH_LENGTH + "characters to search for a card", channel);
		}

		else {
			
			int game = getGameForChannel(channel);
			
			switch (game) {
			case 0: 
				
				break;
			case 1: 
				findCardsHEX(bot, sender, message, channel, cardName);
				break;
			case 2: 
				findCardsTESL(bot, sender, message, channel, cardName);
				break;
			default: 
				
				break;
			}

			
		}

	}

	/**
	 * Gets the game for channel.
	 *
	 * @param channel the channel
	 * @return the game for channel
	 */
	private int getGameForChannel(String channel) {
		List<User> users = userRepository.findByName(channel.replace(config.getChannelPrefix(), ""));
		if (!users.isEmpty()){
			//TODO: Logic for finding more than one
			return users.get(0).getGame();
		}
		
		return 0;
	}

	/**
	 * Finds the closest match.
	 *
	 * @return the closest match String
	 */
	private static String findClosestMatch(Collection<String> collection, Object target) {
		int distance = Integer.MAX_VALUE;
		String closest = null;
		for (String compareObject : collection) {
			int currentDistance = StringUtils.getLevenshteinDistance(compareObject.toString(), target.toString());
			if (currentDistance < distance) {
				distance = currentDistance;
				closest = compareObject;
			}
		}
		return closest;
	}
	
	/**
	 * Find cards hex.
	 *
	 * @param bot the bot
	 * @param sender the sender
	 * @param message the message
	 * @param channel the channel
	 * @param cardName the card name to look for
	 */
	private void findCardsHEX (ChatBot bot, String sender, String message, String channel, String cardName) {

		String searchMessage = cardName.replaceAll("[^A-Za-z0-9 ]", "");
		String closestMatch = findClosestMatch(hexcardRepository.findByFormatedNameWithWildCards(searchMessage), searchMessage);
		
		if (closestMatch == null) {
			messageSender.sendMessageOrWhisper(bot, sender, "No card with name '" + cardName + "' found", channel);
		} else {
			//TODO: Duplicate cards found logic
			messageSender.sendMessageOrWhisper(bot, sender, hexcardRepository.findByName(closestMatch).toString(), channel);
		}
	}
	
	/**
	 * Find cards TES:L.
	 *
	 * @param bot the bot
	 * @param sender the sender
	 * @param message the message
	 * @param channel the channel
	 * @param cardName the card name to look for
	 */
	private void findCardsTESL (ChatBot bot, String sender, String message, String channel, String cardName) {

		String closestMatch = findClosestMatch(teslcardRepository.findByNameWithWildCards(cardName), cardName);
		if (closestMatch == null) {
			messageSender.sendMessageOrWhisper(bot, sender, "No card with name '" + cardName + "' found", channel);
		} else {
			messageSender.sendMessageOrWhisper(bot, sender, teslcardRepository.findByName(closestMatch).toString(), channel);
		}
	}

}
