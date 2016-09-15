package se.skaro.teslbot.data.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

	@Entity
	@Table (name="champion")
	public final class Champion {
		
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long id;
		
		private String name;
		
		@Column(name = "champion_set")
		private String set;
		private int health;
		private String race;
		@Column(name = "class")
		private String championClass;
		private String charge;

		
		
		public String getName() {
			return name;
		}

		public String getSet() {
			return set;
		}

		public int getHealth() {
			return health;
		}

		public String getRace() {
			return race;
		}

		public String getChampionClass() {
			return championClass;
		}

		public String getCharge() {
			return charge;
		}

		@Override
		public String toString() {
			return name + ", " + set + ", " + health + " health. " + race
					+ " " + championClass + ". " + charge;
		}
		
		

	}

