package au.com.hipages.twitter.builders.twitter4JBuilder;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;

import twitter4j.MediaEntity;
import twitter4j.MediaEntity.Size;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import au.com.hipages.twitter.builders.TwitterBuilder;
import au.com.hipages.twitter.domain.HipagesException;
import au.com.hipages.twitter.domain.StatusImage;
import au.com.hipages.twitter.domain.TwitterStatus;

public class Twitter4JBuilder implements TwitterBuilder {
	private static final String CustomerKey = 		"EdLlPzNMCZ7bFBKa1MmdZyM9v";
	private static final String CustomerSecret = 	"H5YppLU3lsenXIMNIizqRPI1BuN48HfYBC0a2uXq2fLUvfuqH1";
	private static final String Token = 			"2496401227-3UqW7ElSAwyZSxqcLU1VutaIsSBAMiTwBOcghxk";
	private static final String TokenSecret = 		"TxSu7xoq2E9u7VIV8cnAU4R4UtMdp2ZyOWiQodPbPit4m";
	
	private String query = null;
	private String user = null;
	private String sinceId = null;
	private String maxId = null;
	private int count = -1;
	private Date until = null;
	private int page = -1;

	@Override
	public TwitterBuilder setQuery(String query) {
		this.query = query;
		return this;
	}

	@Override
	public TwitterBuilder setSinceId(String sinceId) {
		if (sinceId != null && !sinceId.equals("-1")) this.sinceId = sinceId;
		return this;
	}

	@Override
	public TwitterBuilder setMaxId(String maxId) {
		if (maxId != null && !maxId.equals("-1")) this.maxId = maxId;
		return this;
	}

	@Override
	public TwitterBuilder setCount(int count) {
		this.count = count;
		return this;
	}

	@Override
	public TwitterBuilder setUser(String user) {
		this.user = user;
		return this;
	}

	@Override
	public TwitterBuilder setUntil(Date date) {
		this.until = date;
		return this;
	}

	@Override
	public TwitterBuilder setPage(int page) {
		this.page = page;
		return this;
	}
	
	private static boolean oAuthKeysAreSet = false;
	
	@Override
	public List<TwitterStatus> build() throws HipagesException {
		try {
	        Twitter twitter = TwitterFactory.getSingleton();
	        if (!oAuthKeysAreSet) {
		        twitter.setOAuthConsumer(
	        		CustomerKey, 
	        		CustomerSecret
	        	);
		        twitter.setOAuthAccessToken(new AccessToken(
		        		Token, 
		        		TokenSecret
		        	)
		        );
		        oAuthKeysAreSet = true;
	        }
	        List<Status> statusses = null;
	        if (user != null) {
        		Paging paging = new Paging();
        		if (count != -1) paging.setCount(count);
        		if (sinceId != null) paging.setSinceId(Long.parseLong(sinceId));
		        if (page != -1) paging.setPage(page);
		        if (maxId != null) paging.setMaxId(Long.parseLong(maxId) - 1);
        		statusses = twitter.getUserTimeline(user, paging);
	        } else {
		        Query q = new Query();
		        if (sinceId != null) q.setSinceId(Long.parseLong(sinceId));
		        if (count != -1) q.setCount(count);
		        if (query != null) q.setQuery(query);
		        if (until != null) q.setUntil(untilToString());
		        if (maxId != null) q.setMaxId(Long.parseLong(maxId) - 1);
		        statusses = twitter.search(q).getTweets();
	        }
	        return parseStatusses(statusses);
		} catch (TwitterException e) {
			e.printStackTrace();
			throw new HipagesException(e);
		}
	}
	
	private String untilToString() {
		return null;
	}
	
	protected StatusImage parseStatusImage(MediaEntity entity) {
		if (!entity.getType().equals("photo")) return null;
		StatusImage image = new StatusImage();
		image.setSrc(entity.getMediaURL());
		image.setUrl(entity.getURL());
		Size size = entity.getSizes().get(2);
		image.setHeight(size.getHeight());
		image.setWidth(size.getWidth());
		return image;
	}

	protected List<TwitterStatus> parseStatusses(List<Status> statuses) {
		List<TwitterStatus> list = new LinkedList<TwitterStatus>();
		for (Status s : statuses) {
			TwitterStatus status = new TwitterStatus();
			
			status.setText(s.getText());
			status.setId(String.valueOf(s.getId()));
			status.setDate(s.getCreatedAt().getTime());
			for (MediaEntity entity : s.getMediaEntities()) {
				StatusImage image = parseStatusImage(entity);
				if (image != null) status.addImage(image);
			}
			
			list.add(status);
		}
		return list;
	}

}
