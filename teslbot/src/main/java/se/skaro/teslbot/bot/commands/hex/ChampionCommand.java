package se.skaro.teslbot.bot.commands.hex;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.skaro.teslbot.bot.ChatBot;
import se.skaro.teslbot.bot.commands.AbstractCommand;
import se.skaro.teslbot.data.entity.Champion;
import se.skaro.teslbot.data.repository.ChampionRepository;

/**
 * The Class ChampionCommand. Used to look up champions/legends. HEX specific command.
 */
@Component
public class ChampionCommand extends AbstractCommand {

	/** The champion repository. */
	@Autowired
	private ChampionRepository repo;

	/* (non-Javadoc)
	 * @see se.skaro.teslbot.bot.commands.AbstractCommand#call(se.skaro.teslbot.bot.ChatBot, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void call(ChatBot bot, String sender, String message, String channel) {

		String name = "";

		if (message.toLowerCase().startsWith(config.getMessagePrefix() + "legend")) {
			name = fixWhiteSpaces(getMessageWithoutCommand(config.getMessagePrefix() +"legend", message));
		}
		if (message.toLowerCase().startsWith(config.getMessagePrefix() + "champion")) {
			name = fixWhiteSpaces(getMessageWithoutCommand(config.getMessagePrefix() +"champion", message));
		}

		if (isInputLengthOkay(name, bot, sender, channel) && isGameSetToHex(channel)) {

			List<Champion> result = repo.findByNameWithWildCards(name);
			if (result.isEmpty()) {
				messageSender.sendMessage(bot, sender, "No legend with name " + name + " found", channel, true);
			} else if (result.size() == 1) {
				messageSender.sendMessage(bot, sender, result.get(0).toString(), channel, true);
			}

			else {

				StringBuilder sb = new StringBuilder();
				sb.append("Found multiple champions: ");
				String separator = "";
				for (Champion c : result) {
					if (c.getName().equalsIgnoreCase(name)) {
						sb.delete(0, sb.length());
						sb.append(c.toString());
						break;
					}

					sb.append(separator);
					sb.append(c.getName());
					separator = ", ";
				}
				messageSender.sendMessage(bot, sender, sb.toString(), channel, true);
			}

		} 

	}

}
