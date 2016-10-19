package se.skaro.teslbot.web.data;

import se.skaro.teslbot.web.json.object.tournamentdata.Game;

public class Match {

	private String yourName;
	private String opponentsName;

	private int yourWins;
	private int opponentsWins;

	public Match(Game data, String user) {
		
		if (data.getPlayerOne().equalsIgnoreCase(user)){
			yourName = data.getPlayerOne();
			opponentsName = data.getPlayerTwo();
		} else {
			yourName = data.getPlayerTwo();
			opponentsName = data.getPlayerOne();
		}
		
		
		yourWins = 0;
		opponentsWins = 0;
		addWin(data.getGameOneWinner());
		addWin(data.getGameTwoWinner());
		addWin(data.getGameThreeWinner());
	}

	private void addWin(String gameWinner) {
		if (gameWinner.equalsIgnoreCase(yourName)) {
			yourWins++;
		} else if (gameWinner.equalsIgnoreCase(opponentsName)) {
			opponentsWins++;
		}

	}
	
	

	public int getYourWins() {
		return yourWins;
	}

	public int getOpponentsWins() {
		return opponentsWins;
	}

	@Override
	public String toString() {
		return yourName + " - " + opponentsName + " (" + yourWins + "-" + opponentsWins + ")";
	}
	
	public String toStringWithSpans(){
		return "<span id='players'><span id='myname'>" + yourName + "</span><span id='separator'> - </span><span id='opponentsname'>" + opponentsName + "</span></span><span id='score'> <span id='parentheses'>(</span><span id='mywins'>" + yourWins + "</span><span id='separator'>-</span><span id='opponentswins'>" + opponentsWins + "</span><span id='parentheses'>)</span></span>";
	}

	public boolean isOver() {
		if (yourWins == 2 || opponentsWins == 2){
			return true;
		}
		return false;
	}

}