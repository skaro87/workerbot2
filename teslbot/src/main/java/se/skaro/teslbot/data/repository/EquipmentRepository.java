package se.skaro.teslbot.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import se.skaro.teslbot.data.entity.Equipment;

public interface EquipmentRepository extends CrudRepository<Equipment, Long> {
	
	@Query("SELECT e FROM Equipment e WHERE e.name LIKE %:name%")
	public List<Equipment> findByNameWithWildCards(@Param("name") String name);

}
