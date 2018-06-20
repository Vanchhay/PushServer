package tw.app;

import tw.rest.PushMessageRestService;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class PushMessageApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();

	public PushMessageApplication() {
		singletons.add(new PushMessageRestService());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
