package se.skaro.teslbot.bot.commands.hex;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import se.skaro.teslbot.bot.ChatBot;
import se.skaro.teslbot.bot.commands.AbstractCommand;
import se.skaro.teslbot.data.entity.HEXCard;
import se.skaro.teslbot.data.repository.HEXCardRepository;

/**
 * The Class ImageCommand. Used to get a link to an image, or send a call to the image plugin. HEX specific command.
 */
@Component
public class ImageCommand extends AbstractCommand {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ImageCommand.class);

	/** The HEX card repository. */
	@Autowired
	private HEXCardRepository repo;
	
	@Autowired
	private SimpMessagingTemplate socketMessager;

	/* (non-Javadoc)
	 * @see se.skaro.teslbot.bot.commands.AbstractCommand#call(se.skaro.teslbot.bot.ChatBot, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void call(ChatBot bot, String sender, String message, String channel) {

		String name = fixWhiteSpaces(getMessageWithoutCommand(config.getMessagePrefix() + "img", message));

		if (isInputLengthOkay(name, bot, sender, channel) && isGameSetToHex(channel)) {
			
			boolean searchForAA = false;
			
			if (name.toLowerCase().startsWith("aa") || name.toLowerCase().endsWith("aa")) {
				name = fixWhiteSpaces(name.toLowerCase().replace("aa", ""));
				searchForAA = true;
			}
			
			HEXCard card = findCardsHEX(bot, sender, message, channel, name);
			
			if (card != null){
				sendResponse(card, bot, sender, channel, searchForAA);
			}

		}

	}
	
	/**
	 * Find cards hex.
	 *
	 * @param bot the bot
	 * @param sender the sender
	 * @param message the message
	 * @param channel the channel
	 * @param cardName the card name
	 * @return the HEX card
	 */
	private HEXCard findCardsHEX (ChatBot bot, String sender, String message, String channel, String cardName) {

		String searchMessage = cardName.replaceAll("[^A-Za-z0-9 ]", "");
		String closestMatch = findClosestMatch(repo.findByFormatedNameWithWildCards(searchMessage), searchMessage);
		
		if (closestMatch == null) {
			messageSender.sendMessageOrWhisper(bot, sender, "No card with name '" + cardName + "' found", channel);
			return null;
		} else {
			//TODO: Multiple cards found
			return repo.findByName(closestMatch).get(0);
		}
	}
	
	/**
	 * Find closest match.
	 *
	 * @param collection the collection
	 * @param target the target
	 * @return the string
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
	 * Send response.
	 *
	 * @param card the card
	 * @param bot the bot
	 * @param sender the sender
	 * @param channel the channel
	 * @param aa the aa
	 */
	private void sendResponse(HEXCard card, ChatBot bot, String sender, String channel, boolean aa) {
		
		String path = cardInfo(card, aa);
		String link = config.getImgHostUrl() + path.toLowerCase();
		
		if (!channel.endsWith(sender)) {
			messageSender.sendMessageOrWhisper(bot, sender, link, channel);

		} else {
			System.out.println("sending socket");
			socketMessager.convertAndSend("/workerbot/img/" + channel.replace(config.getChannelPrefix(), ""), link);

		}
	}

	/**
	 * Card info.
	 *
	 * @param card the card
	 * @param aa the aa
	 * @return the string
	 */
	private String cardInfo(HEXCard card, boolean aa) {

		String name = card.getName().replaceAll(" ", "-").replaceAll("'", "-").replaceAll(",", "");

		if (card.getAa().equalsIgnoreCase("yes") && aa) {
			return ("/cards/aa/" + name + "-aa.png").toLowerCase();
		}

		String set = card.getSet().replaceAll(" ", "-");

		return ("/cards/" + set + "/" + name + ".png").toLowerCase();
	}

}
