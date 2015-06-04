package org.lazan.t5.offline.demo.services;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.lazan.t5.offline.services.TapestryOfflineModule;

@SubModule(TapestryOfflineModule.class)
public class AppModule {
	public static void contributeApplicationDefaults(MappedConfiguration<String, Object> config) {
	    config.add("tapestry-offline.serverName", "");
	    config.add("tapestry-offline.remoteHost", "");
	    config.add("tapestry-offline.localPort", "0");
	    config.add("tapestry-offline.serverPort", "0");
	}	
}
