package se.skaro.teslbot.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name", length = 255, nullable = false)
	private String name;
	
	@Column(name = "ign", length = 255)
	private String ign = "";
	
	@Column(name = "inchannel", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean inChannel;
	
	@Column(name = "whispers", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean whispers;
	
	/**
	 * 0 = No game set, 1 = HEX, 2 = TES:L
	 */
	@Column(name = "game", nullable = false, columnDefinition = "int default 1")
	private int game;

	public User(String name, boolean inChannel) {
		this.name = name;
		this.inChannel = inChannel;
		game = 1;
	}
	
	public User(String name, boolean inChannel, String ign){
		this.name = name;
		this.inChannel = inChannel;
		this.ign = ign;
		game = 1;
	}
	
	public User(){}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getIgn() {
		return ign;
	}

	public boolean isInChannel() {
		return inChannel;
	}
	
	public void setInChannel(boolean setInChannel) {
		inChannel = setInChannel;
	}

	public boolean isWhispers() {
		return whispers;
	}

	public void setWhispers(boolean whispers) {
		this.whispers = whispers;
	}

	public int getGame() {
		return game;
	}

	public void setGame(int game) {
		this.game = game;
	}

	public void setIgn(String ign) {
		this.ign = ign;
	}
	
	
	
	
	
	

}
