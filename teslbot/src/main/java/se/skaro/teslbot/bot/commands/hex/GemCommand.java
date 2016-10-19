package se.skaro.teslbot.bot.commands.hex;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.skaro.teslbot.bot.ChatBot;
import se.skaro.teslbot.bot.commands.AbstractCommand;
import se.skaro.teslbot.data.entity.Gem;
import se.skaro.teslbot.data.repository.GemRepository;

/**
 * The Class GemCommand. Used to look up gems. HEX specific command.
 */
@Component
public class GemCommand extends AbstractCommand {

	/** The gem repository. */
	@Autowired
	private GemRepository repo;
	
	/* (non-Javadoc)
	 * @see se.skaro.teslbot.bot.commands.AbstractCommand#call(se.skaro.teslbot.bot.ChatBot, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void call(ChatBot bot, String sender, String message, String channel) {
		
		String name = fixWhiteSpaces(getMessageWithoutCommand(config.getMessagePrefix() +"gem", message));
		
		if (isInputLengthOkay(name, bot, sender, channel) && isGameSetToHex(channel)) {

			List<Gem> result = repo.findByNameWithWildCards(name);
			if (result.isEmpty()) {
				messageSender.sendMessageOrWhisper(bot, sender, "No gem with name " + name + " found", channel);
			} else if (result.size() == 1) {
				messageSender.sendMessageOrWhisper(bot, sender, result.get(0).toString(), channel);
			}

			else {

				StringBuilder sb = new StringBuilder();
				sb.append("Found multiple gems: ");
				String separator = "";
				for (Gem g : result) {
					if (g.getName().equalsIgnoreCase(name)) {
						sb.delete(0, sb.length());
						sb.append(g.toString());
						break;
					}

					sb.append(separator);
					sb.append(g.getName());
					separator = ", ";
				}
				messageSender.sendMessageOrWhisper(bot, sender, sb.toString(), channel);
			}

		} 

	}

}
