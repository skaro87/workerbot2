package se.skaro.teslbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * The Class Application. Main class for the bot. Runs without arguments, all settings are done in application.context
 */
@SpringBootApplication
@EnableAutoConfiguration
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args); 
	}
}
