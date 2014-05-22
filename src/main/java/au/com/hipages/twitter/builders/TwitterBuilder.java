package au.com.hipages.twitter.builders;

import java.util.Date;
import java.util.List;

import au.com.hipages.twitter.domain.HipagesException;
import au.com.hipages.twitter.domain.TwitterStatus;

public interface TwitterBuilder {
	public TwitterBuilder setQuery(String query);
	public TwitterBuilder setUser(String user);
	public TwitterBuilder setSinceId(String sinceId);
	public TwitterBuilder setMaxId(String sinceId);
	public TwitterBuilder setCount(int count);
	public TwitterBuilder setUntil(Date date);
	public List<TwitterStatus> build() throws HipagesException;
	public TwitterBuilder setPage(int page);
}
