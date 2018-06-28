package tw.app;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class Task implements Runnable {

	public Task() {
	}

	@Override
	public void run()
	{
		try
		{
			Thread t = Thread.currentThread();
			String payload = "{" +
					"\"sender\":\" "+t.getName()+"\" , " +
					"\"topic\": \"TEST POST\", " +
					"\"text\": \"TEST post request concurrently\"" +
					"}";
			StringEntity entity = new StringEntity(payload,	ContentType.APPLICATION_JSON);

			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost request = new HttpPost("http://localhost:8080/pushmessage/post");
			request.setEntity(entity);

			HttpResponse response = httpClient.execute(request);
			System.out.println(t.getName() +"  "+ response.getStatusLine().getStatusCode());
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}

	}
}
