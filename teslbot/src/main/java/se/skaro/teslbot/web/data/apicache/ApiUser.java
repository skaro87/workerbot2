package se.skaro.teslbot.web.data.apicache;

import java.util.Optional;

public class ApiUser {

	private Optional<String> constructedRank = Optional.empty();
	private Optional<String> limitedRank = Optional.empty();
	private Optional<Status> status = Optional.empty();

	public Optional<String> getConstructedRank() {
		return constructedRank;
	}

	public Optional<String> getLimitedRank() {
		return limitedRank;
	}

	public Optional<Status> getStatus() {
		return status;
	}

	public void setConstructedRank(String constructedRank) {
		this.constructedRank = Optional.of(constructedRank);
	}

	public void setLimitedRank(String limitedRank) {
		this.limitedRank = Optional.of(limitedRank);
	}

	public void setStatus(Status status) {
		this.status = Optional.of(status);
	}

}
