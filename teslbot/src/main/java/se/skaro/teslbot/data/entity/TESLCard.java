package se.skaro.teslbot.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "teslcards")
public class TESLCard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name", length = 200, nullable = false)
	private String name;
	
	@Column(name = "rarity", length = 50, nullable = false)
	private String rarity;
	
	@Column(name = "type", length = 50, nullable = false)
	private String type;
	
	@Column(name = "attributes", length = 200, nullable = false)
	private String attributes;
	
	@Column(name = "race", length = 200)
	private String race;
	
	@Column(name = "cost", nullable = false)
	private int cost;
	
	@Column(name = "attack", nullable = false)
	private int attack;
	
	@Column(name = "health", nullable = false)
	private int health;
	
	@Column(name = "text", length = 200, nullable = false)
	private String text;
	
	public String toString(){
		
		if (type.equalsIgnoreCase("Creature")){
			return name + " (" + cost + ") " + rarity + " " + attributes + " "+ attack + "/"+ health + " " + race + ". " + text;
		} 
		
		else if (type.equalsIgnoreCase("Action")){
			return name + " (" + cost + ") " + rarity + " " + attributes + " Action. " + text;
		}
		
		else if (type.equalsIgnoreCase("Item")){
			return name + " (" + cost + ") " + rarity + " " + attributes + " Item. " + text;
		}
		
		else if (type.equalsIgnoreCase("Support")){
			return name + " (" + cost + ") " + rarity + " " + attributes + " Support. " + text;
		}
		
		return null;
		
	}

}
