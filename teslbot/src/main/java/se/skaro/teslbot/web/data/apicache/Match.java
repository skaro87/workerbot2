package se.skaro.teslbot.web.data.apicache;

import org.apache.commons.lang3.StringUtils;

import se.skaro.teslbot.config.ApiOutputMessageStrings;
import se.skaro.teslbot.web.json.object.tournamentdata.Game;

public class Match extends Status{

	private String opponentsName;
	private String format;

	private int yourWins;
	private int opponentsWins;

	public Match(Game data, String ign, String format) {
		this.format = format;
		
		if (data.getPlayerOne().equalsIgnoreCase(ign)){
			this.ign = data.getPlayerOne();
			opponentsName = data.getPlayerTwo();
		} else {
			this.ign = data.getPlayerTwo();
			opponentsName = data.getPlayerOne();
		}
		
		yourWins = 0;
		opponentsWins = 0;
		addWin(data.getGameOneWinner());
		addWin(data.getGameTwoWinner());
		addWin(data.getGameThreeWinner());
	}

	private void addWin(String gameWinner) {
		if (gameWinner.equalsIgnoreCase(ign)) {
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

	public String toStringWithSpans(){
		return "<span id='players'><span id='myname'>" + ign + "</span><span id='separator'> - </span><span id='opponentsname'>" + opponentsName + "</span></span><span id='score'> <span id='parentheses'>(</span><span id='mywins'>" + yourWins + "</span><span id='separator'>-</span><span id='opponentswins'>" + opponentsWins + "</span><span id='parentheses'>)</span></span>";
	}

	public boolean isOver() {
		if (yourWins == 2 || opponentsWins == 2){
			return true;
		}
		return false;
	}
	
	public boolean isInGameOne() {
		return (yourWins + opponentsWins == 0);
	}

	@Override
	public String toString() {
		return ign + " - " + opponentsName + " (" + yourWins + "-" + opponentsWins + ")";
	}
	
	@Override
	public String getMessage() {
		
		return StringUtils.capitalize(format) + ": " + toString();
	}
	
	public String sendMessage(){
		
		System.out.println("yourName "+ign + "("+yourWins+") opName "+opponentsName+"("+opponentsWins+")");
		
		if (isInGameOne()){
			return replacePlaceholders(ApiOutputMessageStrings.getMatchStartMessage());
		}
		
		if (isOver()){
			if (opponentsWins == 2){
				return replacePlaceholders(ApiOutputMessageStrings.getMatchLossMessage());
			}
			if (yourWins == 2){
				return replacePlaceholders(ApiOutputMessageStrings.getMatchWinMessage());
			}
			
		}
		return getMessage();
	}

	@Override
	protected String replacePlaceholders(String message) {
		return message.replace("YOURNAME", StringUtils.capitalize(ign))
				.replace("OPNAME", StringUtils.capitalize(opponentsName))
				.replace("YOURWINS", yourWins+"")
				.replace("OPWINS", opponentsWins+"")
				.replace("FORMAT", format)
				;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + opponentsWins;
		result = prime * result + yourWins;
		return result;
	}

	
	

}