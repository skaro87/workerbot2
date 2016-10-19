package se.skaro.teslbot.bot.commands;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import se.skaro.teslbot.bot.ChatBot;
import se.skaro.teslbot.data.entity.User;

/**
 * The Class JoinCommand. Used to have the bot join your channel
 */
@Component
public class JoinCommand extends AbstractCommand {

	/* (non-Javadoc)
	 * @see se.skaro.teslbot.bot.commands.AbstractCommand#call(se.skaro.teslbot.bot.ChatBot, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void call(ChatBot bot, String sender, String message, String channel) {

		List<String> channels = Arrays.asList(bot.getChannels());

		if (!channels.contains(config.getChannelPrefix() + sender)) {

			List<User> data = userRepo.findByName(sender);
			User user;
			
			if (data.isEmpty()){
				user = new User(sender, false);
				user.setGame(1);
			} else {
				user = data.get(0);
			}
	
			userRepo.save(user);
			joinChannel(bot, sender, channel);

		}
		
		else {
			messageSender.sendMessage(bot, sender, "I am already in your channel, "+sender, channel);
		}

	}

	/**
	 * Join channel.
	 *
	 * @param bot the bot
	 * @param sender the sender
	 * @param channel the channel
	 */
	private void joinChannel(ChatBot bot, String sender, String channel) {
		messageSender.sendMessage(bot, sender, "Joining channel #" + sender, channel);
		bot.joinChannel(config.getChannelPrefix() + sender);
		messageSender.sendMessage(bot, sender, config.getJoinMessage(), config.getChannelPrefix() + sender);
		
	}

}
