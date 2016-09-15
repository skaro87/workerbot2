package se.skaro.teslbot.data.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

	@Entity
	@Table (name="keyword")
	public final class Keyword {

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long id;
		
		private String name;
		private String explanation;
		
		
		public Keyword(String name, String explanation) {
			this.name = name;
			this.explanation = explanation;
		}
		public Keyword(){
			
			
		
		}
		public String getName() {
			return name;
		}
		public String getExplanation() {
			return explanation;
		}
		@Override
		public String toString() {
			return name+": "+explanation;
		}
			
		

	}