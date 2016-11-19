package se.skaro.teslbot.web.api;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import se.skaro.teslbot.config.GsonFactory;
import se.skaro.teslbot.web.data.apicache.ApiCache;
import se.skaro.teslbot.web.data.apicache.ApiUser;
import se.skaro.teslbot.web.data.apicache.Draft;
import se.skaro.teslbot.web.data.apicache.Match;
import se.skaro.teslbot.web.data.apicache.Status;
import se.skaro.teslbot.web.data.apicache.Tournament;
import se.skaro.teslbot.web.guiddatacollection.CardGuidData;
import se.skaro.teslbot.web.json.object.draft.DraftCardPicked;
import se.skaro.teslbot.web.json.object.draft.DraftPack;
import se.skaro.teslbot.web.json.object.rank.RankData;
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

	//private Timer timer;

	@PostConstruct
	public void postConstruct() {
		gson = gsonFactory.getGson();
		//timer = new Timer();
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
		} else if (json.contains("\"Message\":\"Ladder\"")) {
			return parseRankData(json, user);
		}

		return null;
	}

	// Not done
	public synchronized Message parseTournamentData(String json, String user) {
		TournamenDataWrapper tournamentDataWrapper = gson.fromJson(json, TournamenDataWrapper.class);
		TournamentData tournamentData = tournamentDataWrapper.getTournamentData();

		String ign = tournamentDataWrapper.getUser();

		// Constructed
		if (tournamentData.getFormat() == 0) {

			// Ladder match
			if (tournamentData.getGames().size() == 1) {
				return constructedLadder(tournamentData, user, ign);

			}

			// Constructed tournament
			else {

				if (tournamentData.getGames().isEmpty()) {
					return new Message(
							"Waiting for tournament to start. " + tournamentData.getPlayers().size() + " players",
							user);

				} else {
					return constructedTournament(tournamentData, user, ign);

				}
			} // Constructed tournament end

		} // Constructed end

		// Herofall limited
		else if (tournamentData.getFormat() == 34) {

			if (tournamentData.getPlayers().size() < 8) { // Waiting for draft

				return new Message(
						"<span id='waitingfordraft'><span id='tournamentmessage'>Waiting for draft</span> <span id='parentheses'>(</span><span id='players'>"
								+ tournamentData.getPlayers().size()
								+ "</span><span id='separator'>/</span><span id='players'>8</span><span id='parentheses'>)</span></span>",
						ign);
			}

			// Playing matches in draft
			else if (tournamentData.getPlayers().size() == 8 && tournamentData.getGames().size() != 0) {
				draftTournament(tournamentData, user, ign);
			}
		}

		return null;

	}

	// Done
	private Message constructedLadder(TournamentData tournamentData, String user, String ign) {
		Match match = new Match(tournamentData.getGames().get(0), ign, "Constructed Ladder");	
		apiCache.updateStatus(user, match, ign);
		return new Message(match.toString(), user);
	}

	// Not done
	public synchronized Message constructedTournament(TournamentData tournamentData, String user, String ign) {
		Tournament tournament = new Tournament(tournamentData, ign);
		Optional<Match> match = tournament.getCurrentMatch();
		
		/*
		if (match.isPresent()) {
			if (match.get().isInGameOne() || match.get().isOver()) {
				apiCache.updateStatus(user, tournament, ign);
				return new Message(tournament.toStringWithSpans(), user);
			}
		}
		*/
		apiCache.updateStatus(user, tournament, ign);
		return new Message(tournament.toStringWithSpans(), user);

	}

	private void draftTournament(TournamentData tournamentData, String user, String ign) {

	}

	// Implemented
	public synchronized Message parseDraftCardPickedData(String json, String user) {
		DraftCardPicked cardPicked = gson.fromJson(json, DraftCardPicked.class);
		Draft draft = getCurrentDraft(user, cardPicked.getUser());

		draft.pickCard(guidData.getCardName(cardPicked.getCard().getGuid().toString()));
		apiCache.updateStatus(user, draft, cardPicked.getUser());

		return new Message(
				"<span id='draftcardpicked'><span id='picked'>Picked</span> <span id='cardname'>"
						+ guidData.getCardName(cardPicked.getCard().getGuid().toString() + "</span></span>"),
				cardPicked.getUser(), 60);
	}

	// Implemented
	public synchronized Message parseDraftPackData(String json, String user) {
		DraftPack draftPack = gson.fromJson(json, DraftPack.class);
		Draft draft = getCurrentDraft(user, draftPack.getUser());
		draft.setPick(draftPack.getPick());
		apiCache.updateStatus(user, draft, draftPack.getUser());

		return new Message(
				"<span id='draftpick'><span id='pick'>Pick</span> <span id='number'>" + draftPack.getPick()
						+ "</span><span id='separator'>/</span><span id='cardsinpack'>3</span></span>",
				draftPack.getUser(), 60);
	}

	// Implemented
	private Draft getCurrentDraft(String user, String ign) {
		Draft draft = null;
		Optional<ApiUser> apiUser = apiCache.getApiUser(user);
		if (apiUser.isPresent()) {
			Optional<Status> apiStatus = apiUser.get().getStatus();
			if (apiStatus.isPresent()) {
				if (apiStatus.get().getClass().isInstance(Draft.class)) {
					draft = (Draft) apiStatus.get();
				}
			}
		}

		if (draft == null) { // Draft starting
			draft = new Draft(ign);
			apiCache.updateStatus(user, draft, ign);
		}

		return draft;
	}

	// Implemented
	private Message parseRankData(String json, String user) {
		RankData rankData = gson.fromJson(json, RankData.class);

		if (rankData.getType().equals("Constructed")) {
			apiCache.updateConstructedRank(user, rankData.toString(), rankData.getUser());
		} else if (rankData.getType().equals("Limited")) {
			apiCache.updateLimitedRank(user, rankData.toString(), rankData.getUser());
		}

		return null;
	}
	
}
