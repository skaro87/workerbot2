package se.skaro.teslbot.bot.commands;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.skaro.teslbot.bot.commands.hex.ChampionCommand;
import se.skaro.teslbot.bot.commands.hex.EquipmentCommand;
import se.skaro.teslbot.bot.commands.hex.GemCommand;
import se.skaro.teslbot.bot.commands.hex.GoldToPlatCommand;
import se.skaro.teslbot.bot.commands.hex.IGNCommand;
import se.skaro.teslbot.bot.commands.hex.ImageCommand;
import se.skaro.teslbot.bot.commands.hex.KeywordCommand;
import se.skaro.teslbot.bot.commands.hex.PlatToGoldCommand;
import se.skaro.teslbot.bot.commands.hex.PriceCommand;
import se.skaro.teslbot.bot.commands.hex.RatioCommand;
import se.skaro.teslbot.bot.commands.hex.SetIGNCommand;
import se.skaro.teslbot.bot.commands.hex.WhoIsCommand;
import se.skaro.teslbot.bot.commands.hex.api.ApiKeyCommand;
import se.skaro.teslbot.bot.commands.hex.api.RankCommand;
import se.skaro.teslbot.bot.commands.hex.api.StatusCommand;
import se.skaro.teslbot.config.ExternalConfigComponents;
import se.skaro.teslbot.data.entity.TextCommand;
import se.skaro.teslbot.data.repository.TextCommandRepository;

/**
 * The Class Commands. Used to store a HashMap of commands that the bot can use.
 */
@Component
public class Commands {

	/** The commands. */
	private HashMap<String, AbstractCommand> commands;

	/** The config. */
	@Autowired
	private ExternalConfigComponents config;

	@Autowired
	private TextCommandRepository textCommandRepo;

	// HashMap of commands for the bot. Add new commands @Autowired here

	@Autowired
	private JoinCommand joinCommand;

	@Autowired
	private LeaveCommand leaveCommand;

	@Autowired
	private CardCommand cardCommand;

	@Autowired
	private WhisperCommand whisperCommand;

	@Autowired
	private GameCommand gameCommand;

	@Autowired
	private GenericTextCommand genericTextCommand;
	
	//HEX only commands
	
	@Autowired
	private ChampionCommand championCommand;
	
	@Autowired
	private EquipmentCommand equipmentCommand;
	
	@Autowired
	private GemCommand gemCommand;
	
	@Autowired
	private GoldToPlatCommand gtpCommand;
	
	@Autowired
	private IGNCommand ignCommand;
	
	@Autowired
	private ImageCommand imageCommand;
	
	@Autowired
	private KeywordCommand keywordCommand;
	
	@Autowired
	private PlatToGoldCommand ptgCommand;
	
	@Autowired
	private PriceCommand priceCommand;
	
	@Autowired
	private RatioCommand ratioCommand;
	
	@Autowired
	private SetIGNCommand setIgnCommand;
	
	@Autowired
	private WhoIsCommand whoIsCommand;
	
	// API
	
	@Autowired
	private RankCommand rankCommand;
	
	@Autowired
	private StatusCommand statusCommand;
	
	@Autowired
	private ApiKeyCommand apiKeyCommand;

	/**
	 * Gets the commands.
	 *
	 * @return the commands
	 */
	public HashMap<String, AbstractCommand> getCommands() {
		return commands;
	}

	/**
	 * Post construct. Used to add all commands to the list.
	 */
	@PostConstruct
	public void postConstruct() {
		commands = new HashMap<>();
		commands.put(config.getMessagePrefix() + "join", joinCommand);
		commands.put(config.getMessagePrefix() + "leave", leaveCommand);
		commands.put(config.getMessagePrefix() + "card", cardCommand);
		commands.put(config.getMessagePrefix() + "whispers", whisperCommand);
		commands.put(config.getMessagePrefix() + "game", gameCommand);
		
		//HEX only commands
		commands.put(config.getMessagePrefix() + "champion", championCommand);
		commands.put(config.getMessagePrefix() + "legend", championCommand);
		commands.put(config.getMessagePrefix() + "equipment", equipmentCommand);
		commands.put(config.getMessagePrefix() + "equip", equipmentCommand);
		commands.put(config.getMessagePrefix() + "gem", gemCommand);
		commands.put(config.getMessagePrefix() + "gtp", gtpCommand);
		commands.put(config.getMessagePrefix() + "ign", ignCommand);
		commands.put(config.getMessagePrefix() + "img", imageCommand);
		commands.put(config.getMessagePrefix() + "keyword", keywordCommand);
		commands.put(config.getMessagePrefix() + "ptg", ptgCommand);
		commands.put(config.getMessagePrefix() + "price", priceCommand);
		commands.put(config.getMessagePrefix() + "ratio", ratioCommand);
		commands.put(config.getMessagePrefix() + "setign", setIgnCommand);
		commands.put(config.getMessagePrefix() + "whois", whoIsCommand);
		
		// API commands
		commands.put(config.getMessagePrefix() + "rank", rankCommand);
		commands.put(config.getMessagePrefix() + "constructedrank", rankCommand);
		commands.put(config.getMessagePrefix() + "limitedrank", rankCommand);
		commands.put(config.getMessagePrefix() + "status", statusCommand);
		commands.put(config.getMessagePrefix() + "apikey", apiKeyCommand);
		

		//Generic Text Commands
		Iterable<TextCommand> textCommands = textCommandRepo.findAll();
		textCommands.forEach(t -> {
			genericTextCommand.AddCommandToMap(t);
			commands.put(config.getMessagePrefix() + t.getName(), genericTextCommand);
		});

	}

}
