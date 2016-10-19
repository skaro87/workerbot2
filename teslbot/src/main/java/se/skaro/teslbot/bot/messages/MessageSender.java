package se.skaro.teslbot.bot.messages;

import java.util.List;
import java.util.Timer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.skaro.teslbot.bot.ChatBot;
import se.skaro.teslbot.config.ExternalConfigComponents;
import se.skaro.teslbot.data.entity.User;
import se.skaro.teslbot.data.repository.UserRepository;
import se.skaro.teslbot.util.twitch.mod.TwitchModUtil;

/**
 * The Class MessageSender. Used to send messages with the ChatBot
 */
@Component
public class MessageSender {

	/** The user repository. */
	@Autowired
	private UserRepository userRepository;

	/** The config. */
	@Autowired
	private ExternalConfigComponents config;
	
	@Autowired
	private TwitchModUtil twitchMod;

	private MessageTask messageTask;
	
	private Timer time;
	/**
	 * Send message. Sends a message or a whisper depending on the status of the sender in the channel. sendWhisper() is used to send only whispers
	 *
	 * @param bot the bot
	 * @param sender the sender of the original message. Used to send whispers.
	 * @param message the message to be sent.
	 * @param channel the channel to send the message to.
	 */
	
	@PostConstruct
	public void postConstruct(){
		time = new Timer();
		messageTask = new MessageTask(); 
		time.schedule(messageTask, 0, config.getMillisecondsBetweenMessages());
	}
	
	
	public void sendMessage(ChatBot bot, String sender, String message, String channel){
		sendMessageOrAddToQueue(new Message(bot, sender, message, channel, false));
	}
	
	public void sendMessageOrWhisper(ChatBot bot, String sender, String message, String channel) {
		
		String userToCheck = channel.replace(config.getChannelPrefix(), "");
		
		if (config.getGlobalWhisperSettings()) {
			List<User> users = userRepository.findByName(userToCheck);
			
			if (!users.isEmpty()) {
				User user = users.get(0);				
				
				if (user.isWhispers()) {
					
					if (!twitchMod.isModOrOwner(sender, channel)){
						sendMessageOrAddToQueue(new Message(bot, sender, message, channel, true));
						return;
					}
				}
			}
		}

		sendMessageOrAddToQueue(new Message(bot, sender, message, channel, false));

	}
	
	public void sendWhisper(ChatBot bot, String sender, String message, String channel) {
		sendMessageOrAddToQueue(new Message(bot, sender, message, channel, true));
	}
	
	private void sendMessageOrAddToQueue(Message message){
		messageTask.getMessageQueue().add(message);
	
	}
	
}
