package org.lazan.t5.offline.services.internal;

import javax.servlet.http.Cookie;

import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.annotations.Scope;
import org.lazan.t5.offline.services.OfflineCookieGlobals;

@Scope(ScopeConstants.PERTHREAD)
public class OfflineCookieGlobalsImpl implements OfflineCookieGlobals {
	private Cookie[] cookies;
	private boolean cookiesSet;

	@Override
	public void setCookies(Cookie[] cookies) {
		this.cookies = cookies;
		this.cookiesSet = true;
	}

	@Override
	public Cookie[] getCookies() {
		return this.cookies;
	}
	
	@Override
	public boolean isCookiesSet() {
		return cookiesSet;
	}
}
