package se.skaro.teslbot.web.guiddatacollection;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import se.skaro.teslbot.web.guiddatacollection.json.DocXCards;


public class DataReciver {

	private Gson gson;
	private String url;
	File file;

	public DataReciver(String url) {
		file = new File("guiddata");
		GsonBuilder gsonBuilder = new GsonBuilder();
		gson = gsonBuilder.create();
		this.url = url;
	}

	public void saveGUIDdata() {
		System.out.println("Downloading GUID Data");
		DocXCards cards = gson.fromJson(readURL(url), DocXCards.class);
		appendToFile(cards);
		System.out.println("Download Complete");
	}

	private String readURL(String url) {

		try (InputStream input = new URL(url).openStream()) {
			return IOUtils.toString(input, "UTF-8");
		}

		catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	@SuppressWarnings("deprecation")
	private void appendToFile(DocXCards cards) {
		
		//Empties file
		try {
			FileUtils.write(file, "");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//Gets the data
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {

			cards.getCards().forEach(card -> {
				try {
					writer.write(card.getGuid() + "---" + card.getName());
					writer.newLine();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
