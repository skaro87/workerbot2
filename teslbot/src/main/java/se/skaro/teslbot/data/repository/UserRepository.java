package se.skaro.teslbot.data.repository;

import java.util.List;
import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import se.skaro.teslbot.data.entity.User;
/**
 * The Interface UserRepository.
 */
public interface UserRepository extends CrudRepository<User, Long> {	
	
	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return a list of Users
	 */
	
	@Cacheable("users")
	List<User> findByName(String name);
	
	List<User> findByIgn(String ign);
	

}
