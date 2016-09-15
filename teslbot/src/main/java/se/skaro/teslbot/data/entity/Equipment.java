package se.skaro.teslbot.data.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the CARD database table.
 * 
 */
@Entity
@Table (name="equipment")
public final class Equipment  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String affectedCardName;
	private String shard;
	private String equipmentSlot;
	private String rarity;
	private String equipmentLocation;
	
	@Column(length=1024)   
	private String text;
	
	private String formatedAffectedCardName;
	private String formatedName;

	public Equipment() {

	}

	public String getAffectedCardName() {
		return affectedCardName;
	}

	public String getShard() {
		return shard;
	}

	public String getEquipmentSlot() {
		return equipmentSlot;
	}

	public String getRarity() {
		return rarity;
	}

	public String getEquipmentLocation() {
		return equipmentLocation;
	}

	public String getText() {
		return text;
	}
	

	public String getFormatedAffectedCardName() {
		return formatedAffectedCardName;
	}

	public String getFormatedName() {
		return formatedName;
	}

	@Override
	public String toString() {
		return "[" + name + " (" + equipmentSlot + ", " + rarity + ") " + text + ". Found in: " + equipmentLocation + "]".replaceAll("\n", "");
	}

	public String getName() {
		return name;
	}
	
}