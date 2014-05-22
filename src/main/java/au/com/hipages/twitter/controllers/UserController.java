package au.com.hipages.twitter.controllers;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import au.com.hipages.twitter.domain.HipagesException;
import au.com.hipages.twitter.domain.TwitterStatus;
import au.com.hipages.twitter.services.TwitterService;

@Path("user")
public class UserController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String doGet(
    		@QueryParam("user") String user, 
    		@QueryParam("count") @DefaultValue("20") int count,
    		@QueryParam("sinceId") @DefaultValue("-1") String sinceId,
    		@QueryParam("maxId") @DefaultValue("-1") String maxId,
    		@QueryParam("until") @DefaultValue("-1") long until,
    		@QueryParam("page") @DefaultValue("-1") int page
    		) {
        try {
        	List<TwitterStatus> list = TwitterService.getTweetsFromUser(user, sinceId, count, until, page, maxId);
        	return QueryController.toJSON(list);
		} catch (HipagesException e) {
			e.printStackTrace();
			return QueryController.toJSON(e.getErrorMessage());
		}
    }
    
}
