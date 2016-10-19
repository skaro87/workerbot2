package se.skaro.teslbot.bot.commands.hex.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.skaro.teslbot.bot.ChatBot;
import se.skaro.teslbot.bot.commands.AbstractCommand;
import se.skaro.teslbot.web.api.key.AESkeyEncrypt;

@Component
public class ApiKeyCommand extends AbstractCommand {

	@Autowired
	private AESkeyEncrypt aesKeyEncryptor;

	@Override
	public void call(ChatBot bot, String sender, String message, String channel) {

		try {
			String key = aesKeyEncryptor.encrypt(sender);
			messageSender.sendWhisper(bot, sender,
					"Use this line in api.ini in your HEX root folder send WorkerBot api data: api.workerbot.net?key="
							+ key + "|All",
					channel);

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: Error handling
		}

	}

}
