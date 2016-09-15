package se.skaro.teslbot.bot.commands.hex;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.skaro.teslbot.bot.ChatBot;
import se.skaro.teslbot.bot.commands.AbstractCommand;
import se.skaro.teslbot.data.entity.Equipment;
import se.skaro.teslbot.data.repository.EquipmentRepository;

/**
 * The Class EquipmentCommand. Used to look up equipments. Hex specific command.
 */
@Component
public class EquipmentCommand extends AbstractCommand {
	
	/** The equipment repository. */
	@Autowired
	private EquipmentRepository repo;

	/* (non-Javadoc)
	 * @see se.skaro.teslbot.bot.commands.AbstractCommand#call(se.skaro.teslbot.bot.ChatBot, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void call(ChatBot bot, String sender, String message, String channel) {
		String name = "";

		if (message.toLowerCase().startsWith(config.getMessagePrefix() + "equip")) {
			name = fixWhiteSpaces(getMessageWithoutCommand(config.getMessagePrefix() +"equip", message));
		}
		if (message.toLowerCase().startsWith(config.getMessagePrefix() + "equipment")) {
			name = fixWhiteSpaces(getMessageWithoutCommand(config.getMessagePrefix() +"equipment", message));
		}
		if (isInputLengthOkay(name, bot, sender, channel) && isGameSetToHex(channel)) {

			List<Equipment> result = repo.findByNameWithWildCards(name);
			if (result.isEmpty()) {
				messageSender.sendMessage(bot, sender, "No equipment with name " + name + " found", channel, true);
			} else if (result.size() == 1) {
				messageSender.sendMessage(bot, sender, result.get(0).toString(), channel, true);
			}
			
			else if (result.size() > 5){
				messageSender.sendMessage(bot, sender, "Found "+result.size()+" equipments, please specify your search more", channel, true);
			}

			else {

				StringBuilder sb = new StringBuilder();
				sb.append("Found multiple equipments: ");
				String separator = "";
				for (Equipment g : result) {
					if (g.getName().equalsIgnoreCase(name)) {
						sb.delete(0, sb.length());
						sb.append(g.toString());
						break;
					}

					sb.append(separator);
					sb.append(g.getName());
					separator = ", ";
				}
				messageSender.sendMessage(bot, sender, sb.toString(), channel, true);
			}

		}

	}

}
