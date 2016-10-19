package se.skaro.teslbot.bot.commands.hex;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import se.skaro.teslbot.bot.ChatBot;
import se.skaro.teslbot.bot.commands.AbstractCommand;
import se.skaro.teslbot.data.entity.User;

/**
 * The Class IGNCommand. Used to look up in game names using twitch names. HEX specific command.
 */
@Component
public class IGNCommand extends AbstractCommand {

	/* (non-Javadoc)
	 * @see se.skaro.teslbot.bot.commands.AbstractCommand#call(se.skaro.teslbot.bot.ChatBot, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void call(ChatBot bot, String sender, String message, String channel) {

		String input = fixWhiteSpaces(getMessageWithoutCommand(config.getMessagePrefix() + "ign", message));

		if (isGameSetToHex(channel)) {

			if (!input.isEmpty()) {
				input = input.replace("@", "");

				List<User> data = userRepo.findByName(input);

				if (data.isEmpty()) {
					messageSender.sendMessageOrWhisper(bot, sender,
							"@"+sender+", IGN for user " + input + " not registed. To do so, type !setign {username}", channel);
				} else {

					if (data.get(0).getIgn().isEmpty()) {
						messageSender.sendMessage(bot, sender,
								"@"+sender+", IGN for user " + input + " not registed. To do so, type !setign {username}", channel);
					} else {
						messageSender.sendMessageOrWhisper(bot, sender, "@"+sender+", IGN for user " + input + " is " + StringUtils.capitalize(data.get(0).getIgn()),
								channel);
					}
				}

			} else {
				if (input.isEmpty()) {
					List<User> data = userRepo.findByName(sender);

					if (data.isEmpty()) {
						messageSender.sendMessage(bot, sender,
								"@" + sender + ", you have not registerd your IGN. To do so, type !setign {username}",
								channel);
					} else {
						if (data.get(0).getIgn().isEmpty()) {
							messageSender.sendMessage(bot, sender,
									"IGN for user " + sender + " not registed. To do so, type !setign {username}",
									channel);
						} else {
							messageSender.sendMessage(bot, sender,
									"IGN for user " + sender + " is " + StringUtils.capitalize(data.get(0).getIgn()), channel);
						}
					}
				}
			}
		}

	}

}
