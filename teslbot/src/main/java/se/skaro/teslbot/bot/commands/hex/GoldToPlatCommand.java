package se.skaro.teslbot.bot.commands.hex;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.skaro.teslbot.bot.ChatBot;
import se.skaro.teslbot.bot.commands.AbstractCommand;
import se.skaro.teslbot.data.entity.ItemPriceSimple;
import se.skaro.teslbot.data.repository.ItemPriceSimpleRepository;

/**
 * The Class GoldToPlatCommand. Used to look up an input in gold to platinum. Uses the ratio from the prize data. HEX specific command.
 */
@Component
public class GoldToPlatCommand extends AbstractCommand {

	/** The repo. */
	@Autowired
	private ItemPriceSimpleRepository repo;
	
	/* (non-Javadoc)
	 * @see se.skaro.teslbot.bot.commands.AbstractCommand#call(se.skaro.teslbot.bot.ChatBot, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void call(ChatBot bot, String sender, String message, String channel) {
		
		String input = fixWhiteSpaces(getMessageWithoutCommand(config.getMessagePrefix() + "gtp", message));

		if (isGameSetToHex(channel)) {
			
			String inputWithoutLetters = input.replaceAll("[^0-9.]", "").trim();
			
			if (!inputWithoutLetters.isEmpty()){
				double ammountToCheck = Double.parseDouble(inputWithoutLetters);
				
				
				if (ammountToCheck <= 0){
					messageSender.sendMessage(bot, sender, "Only positive numbers allowed", channel, true);
				} else {
					
					Double ratio = config.getRatioApprox();
					List<ItemPriceSimple> data = repo.findByName(config.getRatioItem());
					if (!data.isEmpty()){
						ratio = (Double.valueOf(data.get(0).getGold()) / Double.valueOf(data.get(0).getPlatinum()));
					}
					
					messageSender.sendMessage(bot, sender, String.format("%.0f", ammountToCheck) + " gold is worth "+ String.format("%.2f", (ammountToCheck / ratio)) + " platinum", channel, true);
					
				}
				
				
			} else {
				messageSender.sendMessage(bot, sender, "Only positive numbers allowed", channel, true);
			}
		}

	}
	

}
