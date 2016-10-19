package se.skaro.teslbot.bot;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.skaro.teslbot.bot.commands.Commands;
import se.skaro.teslbot.config.ExternalConfigComponents;
import se.skaro.teslbot.data.entity.User;
import se.skaro.teslbot.data.repository.UserRepository;

/**
 * The Class ChatBot. Uses PircBot to connect to an IRC server
 */
@Component
public class ChatBot extends PircBot {
	
	private static final Logger logger = LoggerFactory.getLogger(ChatBot.class);

	/** The config. */
	@Autowired
	private ExternalConfigComponents config;

	/** The commands. */
	@Autowired
	private Commands commands;

	/** The user repository. */
	@Autowired
	private UserRepository userRepository;

	/**
	 * Post construct. Used to start the bot after the class has been initialized by Spring.
	 */
	@PostConstruct
	public void postConstruct() {
		this.setVerbose(config.isBotVerbiose());
		this.setName(config.getName());
		this.setLogin(config.getName());

		try {
			this.connect(config.getServer(), config.getPort(), config.getOauth());
		} catch (IOException | IrcException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see org.jibble.pircbot.PircBot#onConnect()
	 */
	@Override
	protected void onConnect() {
		
		joinChannel(config.getChannelPrefix()+ config.getName());
		joinChannel(config.getChannelPrefix()+ "skaro87");

		
		ArrayList<User> users = (ArrayList<User>) userRepository.findAll();

		users.forEach(user -> {

			if (user.isInChannel()) {
				logger.info("Joining channel: "+config.getChannelPrefix() + user.getName());
				//TODO: REMOVE COMMENT | joinChannel(config.getChannelPrefix() + user.getName());
				sleepForChannelJoin();
			}
		
		});
		
		

		super.onConnect();
	}

	/**
	 * Sleep for channel join. Used to get under the limit for joining channels on twitch.
	 */
	private void sleepForChannelJoin() {
		
		try {
			//TODO: REMOVE COMMENT | Thread.sleep(config.getChannelJoinTimeout());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see org.jibble.pircbot.PircBot#onMessage(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void onMessage(String channel, String sender, String login, String hostname, String message) {

		if (message.startsWith(config.getMessagePrefix())) {

			String messageCommand = message.contains(" ") ? message.split(" ")[0].toLowerCase() : message.toLowerCase();

			if (commands.getCommands().containsKey(messageCommand)) {
				commands.getCommands().get(messageCommand).call(this, sender, message, channel);
			}

		}
	}

}
