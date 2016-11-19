package se.skaro.teslbot.web.data.apicache;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import se.skaro.teslbot.web.json.object.tournamentdata.Game;
import se.skaro.teslbot.web.json.object.tournamentdata.TournamentData;

public class Tournament extends Status {

	private String format;
	private Optional<Match> currentMatch;
	private int wins = 0;
	private int losses = 0;
	private int round = 0;
	private int matchesRemaining = 0;
	private int players = 0;

	public Tournament(TournamentData data, String ign) {
		this.ign = ign;
		this.players = data.getPlayers().size();
		format = getFormat(data);
		if (!data.getGames().isEmpty()) {
			parseGames(data);
		}
	}

	private void parseGames(TournamentData data) {

		Match match = null;

		for (Game game : data.getGames()) {

			if (!game.isOver()) {
				matchesRemaining++;
			}

			if (game.getPlayerOne().equalsIgnoreCase(ign) || game.getPlayerTwo().equalsIgnoreCase(ign)) {
				round++;
				match = new Match(game, ign, "Constructed Tournament");
				if (match.isOver()) {
					if (match.getYourWins() == 2) {
						wins++;
					} else {
						losses++;
					}
				}

			}

		}

		if (match != null) {
			currentMatch = Optional.of(match);
		}

	}

	private String getFormat(TournamentData data) {
		if (data.getFormat() == 34) {
			return "Draft";
		} else if (data.getFormat() == 0) {
			return "Constructed Tournament";
		}

		return "Tournament";
	}

	private String getMatchOrMatchesRemaining() {
		if (matchesRemaining == 1) {
			return "1 match remaining";
		}
		return matchesRemaining + " matches remaining";
	}

	private String getMatchOrMatchesRemainingWithSpans() {
		if (matchesRemaining == 1) {
			return "<span id='matchesremaining'><span id='number'>1</span> <span id='matches'>match</span> <span id='remaining'>remaining</span></span>";
		}
		return "<span id='matchesremaining'><span id='number'>" + matchesRemaining
				+ "</span> <span id='matches'>matches</span> <span id='remaining'>remaining</span></span>";
	}

	@Override
	public String toString() {
		if (currentMatch.isPresent()) {
			if (currentMatch.get().isOver()) {
				return ("Round " + round + " (" + wins + "-" + losses + ") |  " + getMatchOrMatchesRemaining());
			} else {
				return "Round " + round + " (" + wins + "-" + losses + ") | " + currentMatch.get().toString();
			}

		}

		else {
			if (round > 0) { // If tournament has started
				return getMatchOrMatchesRemaining();
			}

			else { // Before tournament starts
				return "Waiting for " + format + " to start (" + "<span id='number'>" + players
						+ "</span> <span id='players'>players</span><span id='parentheses'>)</span>";

			}
		}
	}

	public String toStringWithSpans() {
		if (currentMatch.isPresent()) {
			if (currentMatch.get().isOver()) {
				return ("<span id='tournamentrecord'><span id='round'>Round</span> <span id='roundnumber'>" + round
						+ "</span> <span id='parentheses'>(</span><span id='tournamentwins'>" + wins
						+ "</span><span id='separator'>-</span><span id='tournamentlosses'>" + losses
						+ "</span><span id='parentheses'>)</span> <span id='vbar'>|</span> "
						+ getMatchOrMatchesRemainingWithSpans() + "</span></span>");
			} else {
				return "<span id='tournamentrecord'><span id='record'><span id='round'>Round</span> <span id='roundnumber'>"
						+ round + "</span> <span id='parentheses'>(</span><span id='tournamentwins'>" + wins
						+ "</span><span id='separator'>-</span><span id='tournamentlosses'>" + losses
						+ "</span><span id='parentheses'>)</span></span> <span id='vbar'>|</span> "
						+ currentMatch.get().toStringWithSpans() + "</span></span>";
			}

		}

		else {
			if (round > 0) { // If tournament has started
				return "<span id='tournamentrecord'>" + getMatchOrMatchesRemainingWithSpans() + "</span>";
			}

			else { // Before tournament starts
				return "<span id='waitingfortournament'>Waiting for tournament to start</span><span id='parentheses'>(</span>"
						+ "<span id='number'>" + players
						+ "</span> <span id='players'>players</span><span id='parentheses'>)</span>";

			}
		}
	}

	@Override
	public String getMessage() {
		return format + ": " + toString();
	}

	@Override
	public String sendMessage() {
		return "sendMessage(): " + toString();
	}

	@Override
	protected String replacePlaceholders(String message) {
		return message;
	}

	public Optional<Match> getCurrentMatch() {
		return currentMatch;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + losses;
		result = prime * result + round;
		result = prime * result + wins;
		return result;
	}

	
	
	

	
}
