package se.skaro.teslbot.config;

import java.util.Random;

public class ApiOutputMessageStrings {
	
	private final static String[] MATCHSTARTMESSAGES = {
			"A match just started. YOURNAME is playing FORMAT against OPNAME",
			"A FORMAT match just started. YOURNAME is playing against OPNAME",
			"A FORMAT match just started. YOURNAME is playing OPNAME",
			"YOURNAME just started a FORMAT match agianst OPNAME",
			">> Initializing FORMAT Match subroutine >> User1: YOURNAME >> User2: OPNAME"
			};
	
	private final static String[] MATCHWINMESSAGES = {
			"YOURNAME won the FORMAT match against OPNAME YOURWINS games to OPWINS",
			"YOURNAME defated OPNAME YOURWINS to OPWINS in a FORMAT match",
			"YOURNAME defated OPNAME YOURWINS to OPWINS playing FORMAT"
	};
	
	private final static String[] MATCHLOSSMESSAGES = {
			"YOURNAME lost the FORMAT match against OPNAME OPWINS games to YOURWINS",
			"OPNAME defeated YOURNAME OPWINS game to YOURWINS playing FORMAT",
			"OPNAME defeated YOURNAME OPWINS game to YOURWINS in a FORMAT match"
	};
	
	private final static String[] DRAFTSTARTMESSAGES = {
			"YOURNAME started drafting",
			"YOURNAME just started to play in a draft",
			">> Initializing Draft subroutine. >>YOURNAME.inDraft == true"
	};
	
	private final static String[] TOURNAMENTSTARTMESSAGES = {
			"Tournament round one is about to start! Good luck everyone playing. YOURNAME is playing against OPNAME",
	};
	
	
	
	public static String getMatchStartMessage(){
		return getRandom(MATCHSTARTMESSAGES);
	}
	
	public static String getMatchWinMessage(){
		return getRandom(MATCHWINMESSAGES);
	}
	
	public static String getMatchLossMessage(){
		return getRandom(MATCHLOSSMESSAGES);
	}
	
	public static String getDraftStartMessage(){
		return getRandom(DRAFTSTARTMESSAGES);
	}
	
	public static String getTournamentStartMessage(){
		return getRandom(TOURNAMENTSTARTMESSAGES);
	}
	
	
	private static String getRandom(String[] list)
	{
	    Random random = new Random();
	    return list[random.nextInt(list.length)];

	}

}
