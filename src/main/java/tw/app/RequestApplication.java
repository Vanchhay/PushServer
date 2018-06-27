package tw.app;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class RequestApplication implements Runnable{

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

			for(int i=0;i<5;i++)
			{
				Thread.sleep(500);
				System.out.println(t.getName()+"Hello : "+(i+1));
			}

			System.out.println(t.getName() +"  "+ response.getStatusLine().getStatusCode());
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}

	}

	public static void main(String[] args)
	{
		Thread t1 = new Thread(new RequestApplication());
		t1.setName("Request 1");
		Thread t2 = new Thread(new RequestApplication());
		t2.setName("Request 2");
		Thread t3 = new Thread(new RequestApplication());
		t3.setName("Request 3");
		Thread t4 = new Thread(new RequestApplication());
		t4.setName("Request 4");
		Thread t5 = new Thread(new RequestApplication());
		t5.setName("Request 5");

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
	}
}
