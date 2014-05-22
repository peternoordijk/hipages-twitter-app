import static org.junit.Assert.fail;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import au.com.hipages.twitter.controllers.QueryController;
import au.com.hipages.twitter.controllers.UserController;
import au.com.hipages.twitter.domain.TwitterStatus;

public class TwitterApiTests {
	
	@SuppressWarnings("unchecked")
	public List<TwitterStatus> queryTest(String query, int count, String sinceId, String maxId, long until) {
		QueryController service = new QueryController();
		Gson gson = new Gson();
		Type listType = new TypeToken<LinkedList<TwitterStatus>>(){}.getType();
		String s = service.doGet(query, count, sinceId, maxId, until);
		System.out.println(s);
		return (List<TwitterStatus>) gson.fromJson(s, listType);
	}
	
	@SuppressWarnings("unchecked")
	public List<TwitterStatus> userTest(String user, int count, String sinceId, String maxId, long until, int page) {
		UserController service = new UserController();
		Gson gson = new Gson();
		Type listType = new TypeToken<LinkedList<TwitterStatus>>(){}.getType();
		String s = service.doGet(user, count, sinceId, maxId, until, page);
		System.out.println(s);
		return (List<TwitterStatus>) gson.fromJson(s, listType);
	}

	@Test
	public void firstTwentyTest() {
		queryTest("@hipages", 20, null, null, -1);
	}

	@Test
	public void countTest() {
		List<TwitterStatus> list = queryTest("@hipages", 1, null, null, -1);
		if (list.size() != 1) fail();
	}

	@Test
	public void sinceIdTest() {
		List<TwitterStatus> listA = queryTest("@hipages", 6, null, null, -1);
		TwitterStatus statusA = listA.get(5);
		String sinceId = statusA.getId();
		List<TwitterStatus> listB = queryTest("@hipages", 10, sinceId, null, -1);
		if (listB.size() != 5) fail();
	}

	@Test
	public void maxIdTest() {
		List<TwitterStatus> listA = queryTest("@hipages", 4, null, null, -1);
		TwitterStatus statusA = listA.get(2);
		String maxId = statusA.getId();
		List<TwitterStatus> listB = queryTest("@hipages", 3, null, maxId, -1);
		TwitterStatus statusB = listB.get(0);
		if (!listA.get(3).getId().equals(statusB.getId())) fail();
	}
	
	@Test
	public void firstUserTest() {
		userTest("hipages", 20, null, null, -1, -1);
	}
	
	@Test
	public void pageTest() {
		List<TwitterStatus> statusesA = userTest("hipages", 5, null, null, -1, -1);
		List<TwitterStatus> statusesB = userTest("hipages", 4, null, null, -1, 2);
		if (!statusesB.get(0).getId().equals(statusesA.get(4).getId())) fail();
	}

}
