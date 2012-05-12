package net.derstefon.tapestry.facebook.services;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.services.LibraryMapping;


public class FacebookModule {

	public static void bind(ServiceBinder binder)
    {
		binder.bind(FacebookService.class);
    }
	
	public static void contributeComponentClassResolver(Configuration<LibraryMapping> configuration)
    {
        configuration.add(new LibraryMapping("tapestryfb", "net.derstefon.tapestry.facebook"));
    }
	
}
