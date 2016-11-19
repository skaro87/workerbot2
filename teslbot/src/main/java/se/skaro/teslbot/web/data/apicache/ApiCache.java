package se.skaro.teslbot.web.data.apicache;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import se.skaro.teslbot.data.repository.UserRepository;

@Component
public class ApiCache {

	private Cache<String, Optional<ApiUser>> apiCache;;
	
	@Autowired UserRepository userRepository;

	@PostConstruct
	public void buildCache() {
		apiCache = CacheBuilder.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES).maximumSize(1000).build();		
	}

	private void putOrUpdateApiUser(String key, Optional<ApiUser> apiUser) {
		apiCache.put(key, apiUser);
	}

	public Optional<ApiUser> getApiUser(String key) {
		try {
			return apiCache.get(key.toLowerCase(), () -> {
				return Optional.empty();
			});
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public void updateConstructedRank(String key, String rank, String ign) {
		Optional<ApiUser> apiUser = getApiUser(key);
		if (!apiUser.isPresent()) {
			apiUser = Optional.of(new ApiUser());
		}
		apiUser.get().setConstructedRank(rank);
		putOrUpdateApiUser(key, apiUser);
	}

	public void updateLimitedRank(String key, String rank, String ign) {
		Optional<ApiUser> apiUser = getApiUser(key);
		if (!apiUser.isPresent()) {
			apiUser = Optional.of(new ApiUser());
		}
		apiUser.get().setLimitedRank(rank);
		putOrUpdateApiUser(key, apiUser);
	}

	public void updateStatus(String key, Status status, String ign) {
		Optional<ApiUser> apiUser = getApiUser(key);
		if (!apiUser.isPresent()) {
			apiUser = Optional.of(new ApiUser());
		}
		status.setIgn(ign);
		apiUser.get().setStatus(status);
		putOrUpdateApiUser(key, apiUser);
	}

}
