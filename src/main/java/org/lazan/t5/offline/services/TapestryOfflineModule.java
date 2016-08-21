package org.lazan.t5.offline.services;

import java.util.Locale;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.lazan.t5.offline.services.internal.OfflineComponentRendererImpl;
import org.lazan.t5.offline.services.internal.OfflineObjectFactoryImpl;
import org.lazan.t5.offline.services.internal.OfflineRequestBuilderFactoryImpl;
import org.lazan.t5.offline.services.internal.OfflineRequestGlobalsImpl;
import org.lazan.t5.offline.services.internal.OfflineResponseGlobalsImpl;

public class TapestryOfflineModule {
	public static void bind(ServiceBinder binder) {
		binder.bind(OfflineComponentRenderer.class, OfflineComponentRendererImpl.class);
		binder.bind(OfflineRequestGlobals.class, OfflineRequestGlobalsImpl.class);
		binder.bind(OfflineResponseGlobals.class, OfflineResponseGlobalsImpl.class);
		binder.bind(OfflineObjectFactory.class, OfflineObjectFactoryImpl.class);
		binder.bind(OfflineRequestBuilderFactory.class, OfflineRequestBuilderFactoryImpl.class);
		
	}
	
	@Contribute(OfflineRequestGlobals.class)
	public void contributeOfflineRequestGlobals(MappedConfiguration<String, Object> config, ApplicationGlobals applicationGlobals, @Symbol(SymbolConstants.CHARSET) String charset) {
		config.add("locale", Locale.getDefault());
		config.add("secure", false);
		config.add("servletContext", applicationGlobals.getServletContext());
		config.add("contextPath", applicationGlobals.getServletContext().getContextPath());
		config.add("contentType", "text/html");
		config.add("protocol", "http");
		config.add("characterEncoding", charset);
	}

	@Contribute(OfflineResponseGlobals.class)
	public void contributeOfflineResponseGlobals(MappedConfiguration<String, Object> config, @Symbol(SymbolConstants.CHARSET) String charset) {
		config.add("characterEncoding", charset);
	}
}
