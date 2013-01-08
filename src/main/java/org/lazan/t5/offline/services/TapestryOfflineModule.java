package org.lazan.t5.offline.services;

import org.apache.tapestry5.ioc.ServiceBinder;
import org.lazan.t5.offline.services.internal.OfflinePageRendererImpl;

public class TapestryOfflineModule {
	public static void bind(ServiceBinder binder) {
		binder.bind(OfflinePageRenderer.class, OfflinePageRendererImpl.class);
	}
}
