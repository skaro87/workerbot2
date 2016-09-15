package se.skaro.teslbot.data.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ItemPriceSimple {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	private int platinum;
	private int platinumSales;
	private int gold;
	private int goldSales;
	
	public ItemPriceSimple(){}
	
	public ItemPriceSimple(String name, int platinum, int platinumSales, int gold, int goldSales) {
		super();
		this.name = name;
		this.platinum = platinum;
		this.platinumSales = platinumSales;
		this.gold = gold;
		this.goldSales = goldSales;
	}
	
	
	public String getListedPrice(Double ratio) {
		
		if (Double.valueOf(platinum) == 0 && Double.valueOf(gold) == 0){
			return name + " [No data found]";
		}

		if (Double.valueOf(platinum) == 0) {
			return name + " [" + gold + "g ("+goldSales+")]";

		} else if (Double.valueOf(gold) == 0) {
			return name + " [" + platinum + "p ("+platinumSales+")]";
		}

		if (Float.valueOf(platinum) * ratio >= Double.valueOf(gold)) {
			return name + " [" + gold + "g ("+goldSales+"), " + platinum + "p ("+platinumSales+")]";
		}

		return name + " [" + platinum+ "p ("+platinumSales+"), " + gold + "g ("+goldSales+")]";
	}
	
	public String getName(){
		return name;
	}
	
	public int getPlatinum() {
		return platinum;
	}

	public int getPlatinumSales() {
		return platinumSales;
	}

	public int getGold() {
		return gold;
	}

	public int getGoldSales() {
		return goldSales;
	}

	public String toString(){
		
		return name;
	}
	
	
	
	

}
