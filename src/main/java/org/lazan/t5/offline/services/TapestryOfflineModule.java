package org.lazan.t5.offline.services;

import javax.servlet.http.Cookie;

import org.apache.tapestry5.internal.services.CookieSource;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.lazan.t5.offline.services.internal.OfflineComponentRendererImpl;
import org.lazan.t5.offline.services.internal.OfflineCookieGlobalsImpl;
import org.lazan.t5.offline.services.internal.OfflineObjectFactoryImpl;
import org.lazan.t5.offline.services.internal.OfflineRequestGlobalsImpl;
import org.lazan.t5.offline.services.internal.OfflineResponseGlobalsImpl;

public class TapestryOfflineModule {
	public static void bind(ServiceBinder binder) {
		binder.bind(OfflineComponentRenderer.class, OfflineComponentRendererImpl.class);
		binder.bind(OfflineRequestGlobals.class, OfflineRequestGlobalsImpl.class);
		binder.bind(OfflineResponseGlobals.class, OfflineResponseGlobalsImpl.class);
		binder.bind(OfflineObjectFactory.class, OfflineObjectFactoryImpl.class);
		binder.bind(OfflineCookieGlobals.class, OfflineCookieGlobalsImpl.class);
	}
	
	public static CookieSource decorateCookieSource(final CookieSource decorateMe, final OfflineCookieGlobals offlineCookieGlobals) {
		return new CookieSource() {
			@Override
			public Cookie[] getCookies() {
				return offlineCookieGlobals.isCookiesSet() ? offlineCookieGlobals.getCookies() : decorateMe.getCookies();
			}
		};
	}
}
