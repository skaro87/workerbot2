package se.skaro.teslbot.bot.commands.hex;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.skaro.teslbot.bot.ChatBot;
import se.skaro.teslbot.bot.commands.AbstractCommand;
import se.skaro.teslbot.data.entity.ItemPriceSimple;
import se.skaro.teslbot.data.repository.ItemPriceSimpleRepository;

/**
 * The Class RatioCommand. Used to look up the current Platinum to Gold ratio. HEX specific command.
 */
@Component
public class RatioCommand extends AbstractCommand{
	
	/** The repo. */
	@Autowired
	private ItemPriceSimpleRepository repo;

	/* (non-Javadoc)
	 * @see se.skaro.teslbot.bot.commands.AbstractCommand#call(se.skaro.teslbot.bot.ChatBot, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void call(ChatBot bot, String sender, String message, String channel) {
		
		if (isGameSetToHex(channel)) {
			
			Double ratio = (double) 0;
			List<ItemPriceSimple> data = repo.findByName(config.getRatioItem());
			if (!data.isEmpty()){
				ratio = (Double.valueOf(data.get(0).getGold()) / Double.valueOf(data.get(0).getPlatinum()));
			}
			
			if (ratio == 0){
				messageSender.sendMessageOrWhisper(bot, sender, "Could not find the current ratio", channel);
			} else {
				messageSender.sendMessageOrWhisper(bot, sender, "1 platinum is currently worth "+String.format("%.2f", ratio)+" gold", channel);
			}
		}
		
	}

}
