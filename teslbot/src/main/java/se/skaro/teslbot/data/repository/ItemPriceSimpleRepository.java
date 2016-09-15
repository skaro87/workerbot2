package se.skaro.teslbot.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import se.skaro.teslbot.data.entity.ItemPriceSimple;

public interface ItemPriceSimpleRepository extends CrudRepository<ItemPriceSimple, Long> {

	@Query("SELECT c FROM ItemPriceSimple c WHERE c.name LIKE %:itemName%")
	public List<ItemPriceSimple> findByNameWithWildCards(@Param("itemName") String cardName);
	
	public List<ItemPriceSimple> findByName(String name);
}
