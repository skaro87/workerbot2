package se.skaro.teslbot.web.data.apicache;

import java.util.Optional;

public class ApiUser {

	private Optional<String> constructedRank = Optional.empty();
	private Optional<String> limitedRank = Optional.empty();
	private Optional<String> status = Optional.empty();
	private Optional<String> ign = Optional.empty();
	
	private Optional<Draft> draft = Optional.empty();

	public Optional<String> getConstructedRank() {
		return constructedRank;
	}

	public Optional<String> getLimitedRank() {
		return limitedRank;
	}

	public Optional<String> getStatus() {
		return status;
	}
	
	public Optional<String> getIgn() {
		return ign;
	}
	
	public Optional<Draft> getDraft() {
		return draft;
	}

	public void setConstructedRank(String constructedRank) {
		this.constructedRank = Optional.of(constructedRank);
	}

	public void setLimitedRank(String limitedRank) {
		this.limitedRank = Optional.of(limitedRank);
	}

	public void setStatus(String status) {
		this.status = Optional.of(status);
	}

	public void setDraft(Draft draft) {
		this.draft = Optional.of(draft);	
	}
	
	public void setIgn (String ign){
		this.ign = Optional.of(ign);
	}

}
