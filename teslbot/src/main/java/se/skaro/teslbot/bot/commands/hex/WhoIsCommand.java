package se.skaro.teslbot.bot.commands.hex;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.skaro.teslbot.bot.ChatBot;
import se.skaro.teslbot.bot.commands.AbstractCommand;
import se.skaro.teslbot.data.entity.User;
import se.skaro.teslbot.data.repository.UserRepository;

/**
 * The Class WhoIsCommand. Used to check who a user is on twitch based on HEX in game name. HEX specific command.
 */
@Component
public class WhoIsCommand extends AbstractCommand {

	/** The repo. */
	@Autowired
	private UserRepository repo;

	/* (non-Javadoc)
	 * @see se.skaro.teslbot.bot.commands.AbstractCommand#call(se.skaro.teslbot.bot.ChatBot, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void call(ChatBot bot, String sender, String message, String channel) {

		String input = fixWhiteSpaces(getMessageWithoutCommand(config.getMessagePrefix() + "whois", message));

		if (!input.isEmpty() && isGameSetToHex(channel)) {
			input = input.replace("@", "");

			List<User> data = repo.findByIgn(input);

			if (data.isEmpty()) {
				messageSender.sendMessage(bot, sender, "No user with IGN " + input + " found", channel);
			} else {

				if (data.get(0).getIgn().isEmpty()) {
					messageSender.sendMessage(bot, sender, "No user with IGN " + input + " found", channel);
				} else {
					messageSender.sendMessage(bot, sender,
							data.get(0).getIgn() + " is " + data.get(0).getName() + " on twitch", channel);
				}
			}

		} else {
			isInputLengthOkay(input, bot, sender, channel);
		}

	}

}
