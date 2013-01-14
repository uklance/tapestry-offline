package org.lazan.t5.offline.internal;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Session;

public class OfflineRequest implements Request {
	private final Session session;
	private final String contextPath;
	private final boolean xhr;
	private final boolean secure;

	public OfflineRequest(Session session, String contextPath, boolean xhr, boolean secure) {
		super();
		this.session = session;
		this.contextPath = contextPath;
		this.xhr = xhr;
		this.secure = secure;
	}

	@Override
	public Session getSession(boolean create) {
		return session;
	}

	@Override
	public String getContextPath() {
		return contextPath;
	}

	@Override
	public List<String> getParameterNames() {
		return Collections.<String> emptyList();
	}

	@Override
	public String getParameter(String name) {
		return null;
	}

	@Override
	public String[] getParameters(String name) {
		return null;
	}

	@Override
	public String getPath() {
		throw new UnsupportedOperationException("getPath");
	}

	@Override
	public Locale getLocale() {
		throw new UnsupportedOperationException("getLocale");
	}

	@Override
	public List<String> getHeaderNames() {
		throw new UnsupportedOperationException("getHeaderNames");
	}

	@Override
	public long getDateHeader(String name) {
		throw new UnsupportedOperationException("getDateHeader");
	}

	@Override
	public String getHeader(String name) {
		throw new UnsupportedOperationException("getHeader");
	}

	@Override
	public boolean isXHR() {
		return xhr;
	}

	@Override
	public boolean isSecure() {
		return secure;
	}

	@Override
	public String getServerName() {
		throw new UnsupportedOperationException("getServerName");
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		throw new UnsupportedOperationException("isRequestedSessionIdValid");
	}

	@Override
	public Object getAttribute(String name) {
		throw new UnsupportedOperationException("getAttribute");
	}

	@Override
	public void setAttribute(String name, Object value) {
		throw new UnsupportedOperationException("setAttribute");
	}

	@Override
	public String getMethod() {
		throw new UnsupportedOperationException("getMethod");
	}

	@Override
	public int getLocalPort() {
		throw new UnsupportedOperationException("getLocalPort");
	}

	@Override
	public int getServerPort() {
		throw new UnsupportedOperationException("getServerPort");
	}

	@Override
	public String getRemoteHost() {
		throw new UnsupportedOperationException("getRemoteHost");
	}
}
