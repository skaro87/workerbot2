package se.skaro.teslbot.bot.commands;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.skaro.teslbot.bot.ChatBot;
import se.skaro.teslbot.config.ExternalConfigComponents;
import se.skaro.teslbot.data.entity.User;

/**
 * The Class LeaveCommand. Used to make the bot leave your channel.
 */
@Component
public class LeaveCommand extends AbstractCommand {

	/** The config. */
	@Autowired
	private ExternalConfigComponents config;

	/* (non-Javadoc)
	 * @see se.skaro.teslbot.bot.commands.AbstractCommand#call(se.skaro.teslbot.bot.ChatBot, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void call(ChatBot bot, String sender, String message, String channel) {

		List<String> channels = Arrays.asList(bot.getChannels());

		if (channels.contains(config.getChannelPrefix() + sender)) {

			List<User> data = userRepo.findByName(sender);
			if (!data.isEmpty()) {
				data.forEach(user -> {
					if (user.getName().equalsIgnoreCase(sender)) {
						user.setInChannel(false);
						userRepo.save(user);
						leaveChannel(bot, sender, channel);
					}
				});

			} else {
				userRepo.save(new User(sender, false));
				leaveChannel(bot, sender, channel);
			}
		} else {
			messageSender.sendMessage(bot, sender, "I am not in your channel, "+sender, channel);
		}

	}

	/**
	 * Leave channel.
	 *
	 * @param bot the bot
	 * @param sender the sender
	 * @param channel the channel
	 */
	private void leaveChannel(ChatBot bot, String sender, String channel) {
		messageSender.sendMessage(bot, sender, "Leaving channel #" + sender, channel);
		messageSender.sendMessage(bot, sender, config.getLeaveMessage(), config.getChannelPrefix() + sender);
		bot.partChannel(config.getChannelPrefix() + sender);
	}

}
