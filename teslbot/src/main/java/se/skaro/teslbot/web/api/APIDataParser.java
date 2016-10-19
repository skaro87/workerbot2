package se.skaro.teslbot.web.api;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import se.skaro.teslbot.config.GsonFactory;
import se.skaro.teslbot.web.data.Match;
import se.skaro.teslbot.web.data.apicache.ApiCache;
import se.skaro.teslbot.web.data.apicache.ApiUser;
import se.skaro.teslbot.web.data.apicache.Draft;
import se.skaro.teslbot.web.guiddatacollection.CardGuidData;
import se.skaro.teslbot.web.json.object.draft.DraftCardPicked;
import se.skaro.teslbot.web.json.object.draft.DraftPack;
import se.skaro.teslbot.web.json.object.rank.RankData;
import se.skaro.teslbot.web.json.object.tournamentdata.Game;
import se.skaro.teslbot.web.json.object.tournamentdata.TournamenDataWrapper;
import se.skaro.teslbot.web.json.object.tournamentdata.TournamentData;


@Component
public class APIDataParser {

	@Autowired
	private CardGuidData guidData;
	
	@Autowired
	private ApiCache apiCache;

	@Autowired
	private GsonFactory gsonFactory;
	
	private Gson gson;

	@PostConstruct
	public void postConstruct() {
		gson = gsonFactory.getGson();
	}

	public Message getMessage(String json, String user) {

		if (json.contains("\"Message\":\"Tournament\"")) {
			return parseTournamentData(json, user);
		}

		else if (json.contains("\"Message\":\"DraftPack\"")) {
			return parseDraftPackData(json, user);
		}

		else if (json.contains("\"Message\":\"DraftCardPicked\"")) {
			return parseDraftCardPickedData(json, user);
		}
		else if (json.contains("\"Message\":\"Ladder\"")){
			return parseRankData(json, user);
		}

		return null;
	}
	
	//Not done
	public synchronized Message parseTournamentData(String json, String user) {
		TournamenDataWrapper tournamentDataWrapper = gson.fromJson(json, TournamenDataWrapper.class);
		TournamentData tournamentData = tournamentDataWrapper.getTournamentData();

		String ign = tournamentDataWrapper.getUser();
		
		

		// Constructed
		if (tournamentData.getFormat() == 0) {

			// Ladder match
			if (tournamentData.getGames().size() == 1) {
				

			}

			// Lixil? tournament
			else {

				if (tournamentData.getGames().isEmpty()) {
					
				} else {


				}
			} // Tournament end

		} // Constructed end

		else if (tournamentData.getFormat() == 34) {
			if (tournamentData.getPlayers().size() < 8) {
				return new Message(
						"<span id='waitingfordraft'><span id='tournamentmessage'>Waiting for draft</span> <span id='parentheses'>(</span><span id='players'>"
								+ tournamentData.getPlayers().size()
								+ "</span><span id='separator'>/</span><span id='players'>8</span><span id='parentheses'>)</span></span>",
						ign);
			}

			//Currently drafting
			else if (tournamentData.getPlayers().size() == 8 && tournamentData.getGames().size() != 0) {
				
			}
		}

		return null;

	}

	//Not done
	public synchronized Message tournamentDataLogic(TournamentData tournamentData, String user, String ign) {

		Match currentMatch = null;
		int round = 0;
		int wins = 0;
		int losses = 0;
		int matchesRemaining = 0;

		for (Game game : tournamentData.getGames()) {

			if (!game.isOver()) {
				matchesRemaining++;
			}

			if (game.getPlayerOne().equalsIgnoreCase(user) || game.getPlayerTwo().equalsIgnoreCase(user)) {
				round++;
				currentMatch = new Match(game, user);
				if (currentMatch.isOver()) {
					if (currentMatch.getYourWins() == 2) {
						wins++;
					} else {
						losses++;
					}
				}

			}

		}

		if (currentMatch != null) {

			if (currentMatch.isOver()) {
				return new Message("<span id='tournamentrecord'><span id='round'>Round</span> <span id='roundnumber'>" + round
						+ "</span> <span id='parentheses'>(</span><span id='tournamentwins'>" + wins
						+ "</span><span id='separator'>-</span><span id='tournamentlosses'>" + losses
						+ "</span><span id='parentheses'>)</span> <span id='vbar'>|</span> "
						+ getMatchOrMatchesRemaining(matchesRemaining) + "</span></span>", user);
			}

			else {
				return new Message("<span id='tournamentrecord'><span id='record'><span id='round'>Round</span> <span id='roundnumber'>"
						+ round + "</span> <span id='parentheses'>(</span><span id='tournamentwins'>" + wins
						+ "</span><span id='separator'>-</span><span id='tournamentlosses'>" + losses
						+ "</span><span id='parentheses'>)</span></span> <span id='vbar'>|</span> " + currentMatch.toStringWithSpans()
						+ "</span></span>", user);
			}
		}

		else {
			return new Message("<span id='tournamentrecord'>" + getMatchOrMatchesRemaining(matchesRemaining) + "</span>", user);
		}
	}

	//Implemented
	public synchronized Message parseDraftCardPickedData(String json, String user) {
		Draft draft = getCurrentDraft(user);
		DraftCardPicked cardPicked = gson.fromJson(json, DraftCardPicked.class);
		
		draft.pickCard(guidData.getCardName(cardPicked.getCard().getGuid().toString()));
		System.out.println("Picked "+guidData.getCardName(cardPicked.getCard().getGuid().toString()));
		apiCache.updateDraft(user, draft, cardPicked.getUser());
		
		return new Message(
				"<span id='draftcardpicked'><span id='picked'>Picked</span> <span id='cardname'>"
						+ guidData.getCardName(cardPicked.getCard().getGuid().toString() + "</span></span>"),
				cardPicked.getUser(), 60);
	}

	//Implemented
	public synchronized Message parseDraftPackData(String json, String user) {
		Draft draft = getCurrentDraft(user);
		DraftPack draftPack = gson.fromJson(json, DraftPack.class);
		
		draft.setPick(draftPack.getPick());
		apiCache.updateDraft(user, draft, draftPack.getUser());
		
		return new Message(
				"<span id='draftpick'><span id='pick'>Pick</span> <span id='number'>" + draftPack.getPick()
						+ "</span><span id='separator'>/</span><span id='cardsinpack'>3</span></span>",
				draftPack.getUser(), 60);
	}
	
	//Implemented
	private Draft getCurrentDraft(String user){
		Draft draft = null;
		Optional<ApiUser> apiUser = apiCache.getApiUser(user);
		if (apiUser.isPresent()){
			if (apiUser.get().getDraft().isPresent()){
				draft = apiUser.get().getDraft().get();
			}
		} 
		
		if (draft == null) { // Draft starting
			draft = new Draft();
		}
		
		return draft;
	}
	
	//Implemented
	private Message parseRankData(String json, String user) {
		RankData rankData = gson.fromJson(json, RankData.class);
		
		if (rankData.getType().equals("Constructed")){
			apiCache.updateConstructedRank(user, rankData.toString(), rankData.getUser());
		} 
		else if (rankData.getType().equals("Limited")){
			apiCache.updateLimitedRank(user, rankData.toString(), rankData.getUser());
		}
		
		return null;
	}

	private String getMatchOrMatchesRemaining(int matchsRemaining) {
		if (matchsRemaining == 1) {
			return "<span id='matchesremaining'><span id='number'>1</span> <span id='matches'>match</span> <span id='remaining'>remaining</span></span>";
		}
		return "<span id='matchesremaining'><span id='number'>" + matchsRemaining
				+ "</span> <span id='matches'>matches</span> <span id='remaining'>remaining</span></span>";
	}

}
