package au.com.hipages.twitter.controllers;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import au.com.hipages.twitter.domain.HipagesException;
import au.com.hipages.twitter.domain.TwitterStatus;
import au.com.hipages.twitter.services.TwitterService;

@Path("query")
public class QueryController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String doGet(
    		@QueryParam("query") String query, 
    		@QueryParam("count") @DefaultValue("20") int count,
    		@QueryParam("sinceId") @DefaultValue("-1") String sinceId,
    		@QueryParam("maxId") @DefaultValue("-1") String maxId,
    		@QueryParam("until") @DefaultValue("-1") long until
    		) {
        try {
        	List<TwitterStatus> list = TwitterService.getTweetsFromQuery(query, sinceId, count, until, maxId);
        	return toJSON(list);
		} catch (HipagesException e) {
			e.printStackTrace();
			return toJSON(e.getErrorMessage());
		}
    }
    
    public static String toJSON(Object obj) {
    	Gson gson = new Gson();
    	return gson.toJson(obj);
    }
}
