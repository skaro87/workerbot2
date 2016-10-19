package se.skaro.teslbot.web.json.object.tournamentdata;

import com.google.gson.annotations.SerializedName;

public class Game {

	@SerializedName(value = "PlayerOne")
	private String playerOne;

	@SerializedName(value = "PlayerTwo")
	private String playerTwo;

	@SerializedName(value = "GameOneWinner")
	private String gameOneWinner;

	@SerializedName(value = "GameTwoWinner")
	private String gameTwoWinner;

	@SerializedName(value = "GameThreeWinner")
	private String gameThreeWinner;

	public boolean isOver() {
		
		if (!gameOneWinner.equals("") && !gameTwoWinner.equals("")) {
			if (gameOneWinner.equals(gameTwoWinner) || gameOneWinner.equals(gameThreeWinner)
					|| gameTwoWinner.equals(gameThreeWinner)) {
				return true;
			}
		}
		return false;
	}

	public String getPlayerOne() {
		return playerOne;
	}

	public String getPlayerTwo() {
		return playerTwo;
	}

	public String getGameOneWinner() {
		return gameOneWinner;
	}

	public String getGameTwoWinner() {
		return gameTwoWinner;
	}

	public String getGameThreeWinner() {
		return gameThreeWinner;
	}

	@Override
	public String toString() {
		return "Games [playerOne=" + playerOne + ", playerTwo=" + playerTwo + ", gameOneWinner=" + gameOneWinner
				+ ", gameTwoWinner=" + gameTwoWinner + ", gameThreeWinner=" + gameThreeWinner + "]";
	}

}
