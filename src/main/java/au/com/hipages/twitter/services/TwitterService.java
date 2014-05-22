package au.com.hipages.twitter.services;

import java.util.Date;
import java.util.List;

import au.com.hipages.twitter.builders.TwitterBuilderFactory;
import au.com.hipages.twitter.domain.HipagesException;
import au.com.hipages.twitter.domain.TwitterStatus;

public abstract class TwitterService {
	
	public static List<TwitterStatus> getTweetsFromQuery(String query, String sinceId, int count, long until, String maxId) throws HipagesException {
		return TwitterBuilderFactory.getTwitterBuilderInstance()
				.setCount(count)
				.setQuery(query)
				.setSinceId(sinceId)
				.setMaxId(maxId)
				.setUntil(until == -1 ? null : new Date(until))
				.build();
	}
	
	public static List<TwitterStatus> getTweetsFromUser(String user, String sinceId, int count, long until, int page, String maxId) throws HipagesException {
		return TwitterBuilderFactory.getTwitterBuilderInstance()
				.setCount(count)
				.setSinceId(sinceId)
				.setUser(user)
				.setMaxId(maxId)
				.setUntil(until == -1 ? null : new Date(until))
				.setPage(page)
				.build();
	}
	
}
