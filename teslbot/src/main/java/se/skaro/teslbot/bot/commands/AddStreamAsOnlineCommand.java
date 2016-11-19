package se.skaro.teslbot.bot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.skaro.teslbot.bot.ChatBot;
import se.skaro.teslbot.util.twitch.streamers.StreamersByGameAPIReader;

@Component
public class AddStreamAsOnlineCommand extends AbstractCommand{

	@Autowired
	private StreamersByGameAPIReader streams;
	
	@Override
	public void call(ChatBot bot, String sender, String message, String channel) {
		
		streams.setStreamAsOnline(sender);
		messageSender.sendMessageOrWhisper(bot, sender, sender+" stream status is set to online", channel);
		
	}

}
