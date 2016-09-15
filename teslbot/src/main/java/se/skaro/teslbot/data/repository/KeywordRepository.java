package se.skaro.teslbot.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import se.skaro.teslbot.data.entity.Keyword;


public interface KeywordRepository extends CrudRepository<Keyword, Long> {	
	
	@Query("SELECT k FROM Keyword k WHERE k.name LIKE %:name%")
	public List<Keyword> findByNameWithWildCards(@Param("name") String name);

}
