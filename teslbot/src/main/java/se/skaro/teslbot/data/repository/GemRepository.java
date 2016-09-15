package se.skaro.teslbot.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import se.skaro.teslbot.data.entity.Gem;


public interface GemRepository extends CrudRepository<Gem, Long> {
	
	@Query("SELECT g FROM Gem g WHERE g.name LIKE %:name%")
	public List<Gem> findByNameWithWildCards(@Param("name") String name);

}
