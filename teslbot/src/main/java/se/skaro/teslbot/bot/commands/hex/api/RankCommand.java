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
public class RankCommand extends AbstractCommand {

	@Autowired
	private ApiCache db;

	@Autowired
	private StreamersByGameAPIReader streams;

	@Override
	public void call(ChatBot bot, String sender, String message, String channel) {

		if (isGameSetToHex(channel)) {
			String user = channel.replace(config.getChannelPrefix(), "");

			if (streams.getCurrentHEXStreamers().contains(user)) {
				String output = "No rank data found for user " + user;

				System.out.println("Rank command for user");

				Optional<ApiUser> apiUser = db.getApiUser(user);

				if (apiUser.isPresent()) {
					System.out.println(user + ", Constructed: " + apiUser.get().getConstructedRank().isPresent()
							+ ". Limited: " + apiUser.get().getLimitedRank().isPresent());

					output = compileMessage(apiUser.get(), StringUtils.capitalize(user));

				}

				messageSender.sendMessageOrWhisper(bot, sender, output, channel);

			}

		}
	}

	private String compileMessage(ApiUser apiUser, String user) {

		if (apiUser.getConstructedRank().isPresent() && !apiUser.getLimitedRank().isPresent()) {
			return user + " is currently " + apiUser.getConstructedRank().get() + " in constructed";
		}

		else if (!apiUser.getConstructedRank().isPresent() && apiUser.getLimitedRank().isPresent()) {
			return user + " is currently " + apiUser.getLimitedRank().get() + " in limited";
		}

		else if (apiUser.getConstructedRank().isPresent() && apiUser.getLimitedRank().isPresent()) {
			return user + " is currently " + apiUser.getConstructedRank().get() + " in constructed and "
					+ apiUser.getLimitedRank().get() + " in limited";
		}

		return "No rank data found for user " + user;
	}

}