package se.skaro.teslbot.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import se.skaro.teslbot.data.entity.TESLCard;

// TODO: Auto-generated Javadoc
/**
 * The Interface TESLCardRepository.
 */
public interface TESLCardRepository extends CrudRepository<TESLCard, Long> {
	 
	 /**
 	 * Find all card names.
 	 *
 	 * @return the list
 	 */
 	@Query("SELECT c.name FROM TESLCard c")
	 public List<String> findAllCardNames();
	 
	 /**
 	 * Find name with wild cards.
 	 *
 	 * @param cardName the card name
 	 * @return a list of Strings
 	 */
 	@Query("SELECT c.name FROM TESLCard c WHERE c.name LIKE %:cardName%")
	 public List<String> findByNameWithWildCards(@Param("cardName") String cardName);

	 	
	 /**
 	 * Find by name.
 	 *
 	 * @param formatedName the formated name
 	 * @return a list of Cards
 	 */
 	public List<TESLCard> findByName(String formatedName);

}
