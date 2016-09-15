package se.skaro.teslbot.bot.commands.hex;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.skaro.teslbot.bot.ChatBot;
import se.skaro.teslbot.bot.commands.AbstractCommand;
import se.skaro.teslbot.data.entity.User;
import se.skaro.teslbot.data.repository.UserRepository;

/**
 * The Class SetIGNCommand. Used to set you HEX in game name in the user database. Only allows for a single word. HEX specific commands.
 */
@Component
public class SetIGNCommand extends AbstractCommand{
	
	/* (non-Javadoc)
	 * @see se.skaro.teslbot.bot.commands.AbstractCommand#call(se.skaro.teslbot.bot.ChatBot, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void call(ChatBot bot, String sender, String message, String channel) {
		
		String input = fixWhiteSpaces(getMessageWithoutCommand(config.getMessagePrefix() +"setign", message.toLowerCase()));		
		if (isInputLengthOkay(input, bot, sender, channel)){
			
			if(!input.matches("[a-zA-Z0-9]*") && input.length() < 200){
				messageSender.sendMessage(bot, sender, sender + ", you can only include a-z and 0-9 in a single word as your IGN", channel, true);
			} else {
				List<User> data = userRepo.findByName(sender);
				User user;
				
				if (data.isEmpty()){
					user = new User(sender, false, input);
				} else {
					user = data.get(0);
					user.setIgn(input);
				}
				
				userRepo.save(user);
				
				messageSender.sendMessage(bot, sender, "IGN updated for user "+sender, channel, true);
			}
			
			
		}
	}

}
