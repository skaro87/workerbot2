package se.skaro.teslbot.web.guiddatacollection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;


@Component
public class CardGuidData {

	private Map<String, String> guidToCard;

	@PostConstruct
	public void cardGuidData() {
		guidToCard = new HashMap<>();

		try (BufferedReader br = new BufferedReader(new FileReader(new File("guiddata")))) {
			String line = br.readLine();
			while (line != null) {
				String[] data = line.split("---");

				if (data.length == 2) {
					guidToCard.put(data[0], data[1]);
				}
				line = br.readLine();
			}

		} catch (FileNotFoundException e) {
			// TODO: Change the file to search for all cards?
			DataReciver guidDataReciver = new DataReciver("http://doc-x.net/hex/search.rb?output_format=json&set_id=");
			guidDataReciver.saveGUIDdata();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getCardName(String guid) {
		return guidToCard.get(guid);
	}

}
