package se.skaro.teslbot.bot.commands;

import java.util.List;

import org.springframework.stereotype.Component;

import se.skaro.teslbot.bot.ChatBot;
import se.skaro.teslbot.data.entity.User;

/**
 * The Class WhisperCommand. Used to change the whisper settings for a user.
 */
@Component
public class WhisperCommand extends AbstractCommand {

	/* (non-Javadoc)
	 * @see se.skaro.teslbot.bot.commands.AbstractCommand#call(se.skaro.teslbot.bot.ChatBot, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void call(ChatBot bot, String sender, String message, String channel) {

		String whisperMessage = fixWhiteSpaces(
				getMessageWithoutCommand(config.getMessagePrefix() + "whispers", message));

		if (whisperMessage.toLowerCase().contains("on") || whisperMessage.toLowerCase().contains("off")) {

			User user = null;
			List<User> users = userRepo.findByName(sender);
			if (users.isEmpty()) {
				user = new User(sender, false);
			} else {
				for (User u : users) {
					if (u.getName().toLowerCase().equals(sender.toLowerCase())) {
						user = u;
					}
				}
			}

			if (user != null) {

				if (whisperMessage.toLowerCase().contains("on")) {
					user.setWhispers(true);
					messageSender.sendMessage(bot, sender, "Whispers on for channel "+config.getChannelPrefix()+sender, channel);
				} else {
					user.setWhispers(false);
					messageSender.sendMessage(bot, sender, "Whispers off for channel "+config.getChannelPrefix()+sender, channel);
				}
				userRepo.save(user);
				
			}
		} else {
			messageSender.sendMessage(bot, sender, "Invalid message. Use on/off to set whisper settings" + config.getChannelPrefix()+sender, channel);
		}

	}

}
