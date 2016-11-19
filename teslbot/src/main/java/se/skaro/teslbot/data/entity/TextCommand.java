package se.skaro.teslbot.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

// TODO: Auto-generated Javadoc
/**
 * The Class TextCommand. Used to get GenericTextCommands from the commands table
 */
@Entity
@Table (name="commands")
public final class TextCommand {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/** The name. */
	@Column(name = "name", length = 100)
	private String name;
	
	/** The message. */
	@Column(name = "message", length = 500)
	private String message;
	
	/** The help text. */
	@Column(name = "helptext", length = 500)
	private String helpText;
	
	/** Hex only commands. */
	@Column(name = "hex_only", nullable = false)
	private boolean hexOnly;

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Gets the help text.
	 *
	 * @return the help text
	 */
	public String getHelpText() {
		return helpText;
	}

	/**
	 * Checks if it is a whisper command.
	 *
	 * @return true, if the command is supposed to whisper
	 */
	public boolean isHexOnly() {
		return hexOnly;
	}
	
	
	
	
	
}
