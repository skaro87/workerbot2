package se.skaro.teslbot.bot.commands.hex.api;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.skaro.teslbot.bot.ChatBot;
import se.skaro.teslbot.bot.commands.AbstractCommand;
import se.skaro.teslbot.util.twitch.streamers.StreamersByGameAPIReader;
import se.skaro.teslbot.web.data.apicache.ApiCache;
import se.skaro.teslbot.web.data.apicache.ApiUser;

@Component
public class StatusCommand extends AbstractCommand {

	@Autowired
	private ApiCache apiCache;

	@Autowired
	private StreamersByGameAPIReader streams;

	@Override
	public void call(ChatBot bot, String sender, String message, String channel) {

		if (isGameSetToHex(channel)) {
			String user = channel.replace(config.getChannelPrefix(), "");

			if (streams.getCurrentHEXStreamers().contains(user)) {

				Optional<ApiUser> apiUser = apiCache.getApiUser(user);
				if (apiUser.isPresent()) {
					if (apiUser.get().getStatus().isPresent()) {

						messageSender.sendMessage(bot, sender, apiUser.get().getStatus().get().getMessage(), channel);
						return;
					}
				}

				messageSender.sendMessage(bot, sender,
						"No status found for user " + StringUtils.capitalize(channel.replace(config.getChannelPrefix(), "")), channel);

			}

		}
	}

}
