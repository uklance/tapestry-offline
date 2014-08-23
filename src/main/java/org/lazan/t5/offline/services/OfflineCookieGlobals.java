package org.lazan.t5.offline.services;

import javax.servlet.http.Cookie;

public interface OfflineCookieGlobals {
	void setCookies(Cookie[] cookies);
	Cookie[] getCookies();
	boolean isCookiesSet();
}
