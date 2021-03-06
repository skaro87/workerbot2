package se.skaro.teslbot.web.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import se.skaro.teslbot.util.twitch.streamers.StreamersByGameAPIReader;
import se.skaro.teslbot.web.api.APIDataParser;
import se.skaro.teslbot.web.api.Message;
import se.skaro.teslbot.web.api.key.AESkeyEncrypt;

@Controller
public class EventController {

	@Autowired
	private SimpMessagingTemplate socketMessager;

	@Autowired
	private APIDataParser parser;

	@Autowired
	private AESkeyEncrypt aesKeyEncryptor;

	@Autowired
	private StreamersByGameAPIReader stream;

	// Get data from the API
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<String> postEvent(@RequestBody String json, @RequestParam String key) {

		try {
			String user = aesKeyEncryptor.decrypt(key);
			
				Message output = parser.getMessage(json, user);
				if (output != null) {
					if (stream.getCurrentHEXStreamers().contains(user)) {
					socketMessager.convertAndSend("/workerbot/autostatus/" + key, output);
					}
					return new ResponseEntity<String>(HttpStatus.ACCEPTED);
				
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>(HttpStatus.OK);

	}

}
