package se.skaro.teslbot.bot.commands.hex;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.skaro.teslbot.bot.ChatBot;
import se.skaro.teslbot.bot.commands.AbstractCommand;
import se.skaro.teslbot.data.entity.ItemPriceSimple;
import se.skaro.teslbot.data.repository.ItemPriceSimpleRepository;

@Component
public class PriceCommand extends AbstractCommand {

	@Autowired
	private ItemPriceSimpleRepository repo;

	@Override
	public void call(ChatBot bot, String sender, String message, String channel) {

		String input = fixWhiteSpaces(getMessageWithoutCommand(config.getMessagePrefix() + "price", message));
		if (isInputLengthOkay(input, bot, sender, channel) && isGameSetToHex(channel)) {

			List<ItemPriceSimple> data = repo.findByNameWithWildCards(input);

			if (!data.isEmpty()) {

				if (data.size() == 1) {
					messageSender.sendMessageOrWhisper(bot, sender, getPriceForItem(data.get(0)), channel);
				}

				else {
					data = getExactMatchIncludingAA(data, input);

					if (data.size() == 1) {
						messageSender.sendMessageOrWhisper(bot, sender, getPriceForItem(data.get(0)), channel);
					}

					else if (data.size() > 6){
						messageSender.sendMessageOrWhisper(bot, sender, "Found "+data.size()+" items. Please specify your search", channel);

					} else {
						
						double ratio = getRatio();
						StringBuilder sb = new StringBuilder();
			            String separator = "";
			            for (ItemPriceSimple item : data) {
			                sb.append(separator);
			                sb.append(item.getListedPrice(ratio));
			                separator = ", ";
			            }
			            messageSender.sendMessageOrWhisper(bot, sender, sb.toString(), channel);
			           
			            
					}

				}

			} else {
				messageSender.sendMessageOrWhisper(bot, sender, "No data found for item "+input, channel);
			}

		}

	}

	private List<ItemPriceSimple> getExactMatchIncludingAA(List<ItemPriceSimple> data, String message) {
		List<ItemPriceSimple> matches = new ArrayList<>();

		data.forEach(i -> {

			if (i.getName().replace("AA", "").trim().equalsIgnoreCase(message)) {
				matches.add(i);
			}

		});

		if (matches.isEmpty()){
			return data;
		}

		return matches;
	}

	private String getPriceForItem(ItemPriceSimple item) {

		double ratio = getRatio();

		return item.getListedPrice(ratio);
	}

	private double getRatio() {
		List<ItemPriceSimple> data = repo.findByName(config.getRatioItem());

		Double ratio = config.getRatioApprox();
		if (!data.isEmpty()) {
			ratio = (Double.valueOf(data.get(0).getGold()) / Double.valueOf(data.get(0).getPlatinum()));
		}
		return ratio;
	}

}
