package se.skaro.teslbot.bot.commands;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import se.skaro.teslbot.bot.ChatBot;
import se.skaro.teslbot.data.entity.TextCommand;

/**
 * The Class GenericTextCommand. Used for generic text commands, stored in the
 * database and read into the bot on startup.
 */
@Component
public class GenericTextCommand extends AbstractCommand {

	/**
	 * The messages. A map with the name of the command as key, and the response
	 * as value
	 */
	private HashMap<String, TextCommand> messages = new HashMap<String, TextCommand>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * se.skaro.teslbot.bot.commands.AbstractCommand#call(se.skaro.teslbot.bot.
	 * ChatBot, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void call(ChatBot bot, String sender, String message, String channel) {

		String syntax = message.contains(" ") ? message.split(" ")[0].toLowerCase() : message.toLowerCase();
		String reply = messages.get(syntax).getMessage();

		if (!messages.get(syntax).isHexOnly()) {
			messageSender.sendMessage(bot, sender, reply, channel);
		}

		else {
			if (isGameSetToHex(channel)) {
				messageSender.sendMessage(bot, sender, reply, channel);
			}
		}

	}

	/**
	 * Adds the command to the messages map.
	 *
	 * @param command
	 *            the name of the command
	 * @param response
	 *            the response
	 */
	public void AddCommandToMap(TextCommand command) {
		messages.put(config.getMessagePrefix() + command.getName(), command);
	}

}
