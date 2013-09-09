package org.lazan.t5.offline.services;

import org.apache.tapestry5.ioc.ServiceBinder;
import org.lazan.t5.offline.services.internal.OfflineComponentRendererImpl;
import org.lazan.t5.offline.services.internal.OfflineRequestGlobalsImpl;
import org.lazan.t5.offline.services.internal.OfflineResponseGlobalsImpl;

public class TapestryOfflineModule {
	public static void bind(ServiceBinder binder) {
		binder.bind(OfflineComponentRenderer.class, OfflineComponentRendererImpl.class);
		binder.bind(OfflineRequestGlobals.class, OfflineRequestGlobalsImpl.class);
		binder.bind(OfflineResponseGlobals.class, OfflineResponseGlobalsImpl.class);
	}
}
