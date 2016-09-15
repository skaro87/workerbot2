package se.skaro.teslbot.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import se.skaro.teslbot.data.entity.Champion;

public interface ChampionRepository extends CrudRepository<Champion, Long> {

	@Query("SELECT c FROM Champion c WHERE c.name LIKE %:name%")
	public List<Champion> findByNameWithWildCards(@Param("name") String name);

}
