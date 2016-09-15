package se.skaro.teslbot.data.repository;

import org.springframework.data.repository.CrudRepository;

import se.skaro.teslbot.data.entity.TextCommand;

public interface TextCommandRepository extends CrudRepository<TextCommand, Long>{

}
