package se.skaro.teslbot.bot.commands.hex;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.skaro.teslbot.bot.ChatBot;
import se.skaro.teslbot.bot.commands.AbstractCommand;
import se.skaro.teslbot.data.entity.Keyword;
import se.skaro.teslbot.data.repository.KeywordRepository;

/**
 * The Class KeywordCommand. Used to look up a keyword. HEX specific command.
 */
@Component
public class KeywordCommand extends AbstractCommand {

	/** The keyword repository. */
	@Autowired
	private KeywordRepository repo;

	/* (non-Javadoc)
	 * @see se.skaro.teslbot.bot.commands.AbstractCommand#call(se.skaro.teslbot.bot.ChatBot, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void call(ChatBot bot, String sender, String message, String channel) {

		String name = fixWhiteSpaces(getMessageWithoutCommand(config.getMessagePrefix() + "keyword", message));

		if (isInputLengthOkay(name, bot, sender, channel) && isGameSetToHex(channel)) {

			List<Keyword> result = repo.findByNameWithWildCards(name);
			if (result.isEmpty()) {
				messageSender.sendMessageOrWhisper(bot, sender, "No keyword with name " + name + " found", channel);
			} else if (result.size() == 1) {
				messageSender.sendMessageOrWhisper(bot, sender, result.get(0).toString(), channel);
			}

			else {

				StringBuilder sb = new StringBuilder();
				sb.append("Found multiple keywords: ");
				String separator = "";
				for (Keyword k : result) {
					if (k.getName().equalsIgnoreCase(name)) {
						sb.delete(0, sb.length());
						sb.append(k.toString());
						break;
					}

					sb.append(separator);
					sb.append(k.getName());
					separator = ", ";
				}
				messageSender.sendMessageOrWhisper(bot, sender, sb.toString(), channel);
			}

		}

	}

}
