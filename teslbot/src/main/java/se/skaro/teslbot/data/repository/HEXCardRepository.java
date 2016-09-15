package se.skaro.teslbot.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import se.skaro.teslbot.data.entity.HEXCard;

// TODO: Auto-generated Javadoc
/**
 * The Interface HEXCardRepository.
 */
public interface HEXCardRepository extends CrudRepository<HEXCard, Long> {
	 
	 /**
 	 * Find all card names.
 	 *
 	 * @return a list of strings with all card names. Used to compare card names
 	 */
 	@Query("SELECT c.name FROM HEXCard c")
	 public List<String> findAllCardNames();
	 
 	
	 /**
 	 * Find name with wild cards. Used to do a %card% search.
 	 *
 	 * @param cardName the name of the card
 	 * @return a list of Strings with all matching card names
 	 */
 	@Query("SELECT c.name FROM HEXCard c WHERE c.formatedName LIKE %:cardName%")
	 public List<String> findByFormatedNameWithWildCards(@Param("cardName") String cardName);

	 	
	 /**
 	 * Find by name.
 	 *
 	 * @param formatedName the formated name
 	 * @return a list of cards
 	 */
 	public List<HEXCard> findByName(String formatedName);

}
