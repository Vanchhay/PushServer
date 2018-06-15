package tw.rest;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;


@Path("/pushmessage")
public class PushMessageRestService {

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	public PushMessage getPushMessageDetail() {

		PushMessage pushMessage = new PushMessage();

		return pushMessage;
	}

	@POST
	@Path("post")
	@Produces("application/json")
	@Consumes("application/json")
	public Response addPushMessage(PushMessage pm) {
		return Response.ok(pm).build();
	}
}