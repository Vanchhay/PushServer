package tw.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("/pushmessage")
public class PushMessageRestService {

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	public PushMessage getPushMessageDetail() {

		PushMessage pushMessage = new PushMessage();

		return pushMessage;
	}
}