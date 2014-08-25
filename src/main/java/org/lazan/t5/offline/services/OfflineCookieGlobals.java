package org.lazan.t5.offline.services;

import javax.servlet.http.Cookie;

import org.apache.tapestry5.internal.services.CookieSource;
import org.apache.tapestry5.services.Request;

/**
 * This per-thread service allows cookies to be accessed via the {@link CookieSource} whilst rendering
 * pages and components offline. I'm not sure why tapestry's {@link Request} does not have a getCookies()
 * method. If it did, this service wouldn't be required.
 */
public interface OfflineCookieGlobals {
	void storeCookies(Cookie[] cookies);
	boolean isCookiesStored();
	Cookie[] getCookies();
}
