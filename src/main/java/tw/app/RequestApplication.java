package tw.app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RequestApplication{



	public static void main(String[] args)
	{
		ExecutorService ec = Executors.newFixedThreadPool(1000);

		for(int i=0;i<1000;i++)
		{
			ec.submit(new Task());
		}
		ec.shutdown();
		System.out.println("All task submitted...");
	}
}
