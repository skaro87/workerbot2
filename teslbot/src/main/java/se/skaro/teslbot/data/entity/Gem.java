package se.skaro.teslbot.data.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gem")
public class Gem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String socket;
	private String text;

	public Gem(String name, String socket, String text) {
		this.name = name;
		this.socket = socket;
		this.text = text;
	}

	public Gem() {
	}
	

	public String getName() {
		return name;
	}

	public String getSocket() {
		return socket;
	}

	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return socket + " " + name + ": " + text;
	}

}
