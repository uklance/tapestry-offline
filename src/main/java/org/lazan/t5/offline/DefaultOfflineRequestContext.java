package org.lazan.t5.offline;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.services.Session;
import org.lazan.t5.offline.internal.OfflineSession;

public class DefaultOfflineRequestContext implements OfflineRequestContext {
	private Session session;
	private Map<String, String[]> parameters;
	private Locale locale;
	private boolean xhr;
	private boolean secure;
	private Map<String, Object> attributes;
	private boolean requestedSessionIdValid;
	private Map<String, String> headers;
	private Cookie[] cookies;
	private String method;

	@Override
	public Session getSession(boolean create) {
		if (create && session == null) {
			throw new IllegalStateException("Offline session not specified");
		}
		return session;
	}
	
	public void setSession(Map<String, Object> sessionAttributes) {
		setSession(new OfflineSession(sessionAttributes));
	}
	
	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public List<String> getParameterNames() {
		if (parameters == null) {
			return Collections.<String> emptyList();
		}
		List<String> names = CollectionFactory.newList(parameters.keySet());
		Collections.sort(names);
		return names;
	}
	
	public void setParameter(String name, String value) {
		if (parameters == null) {
			parameters = CollectionFactory.newMap();
		}
		String[] arr;
		String[] existing = parameters.get(name);
		if (existing == null) {
			arr = new String[] { value };
		} else {
			arr = new String[existing.length + 1];
			System.arraycopy(existing, 0, arr, 0, existing.length);
			arr[arr.length - 1] = value;
		}
		parameters.put(name,  arr);
	}

	@Override
	public String getParameter(String name) {
		String[] param = parameters == null ? null : parameters.get(name);
		return param == null || param.length == 0 ? null : param[0];
	}

	@Override
	public String[] getParameters(String name) {
		return parameters == null ? null : parameters.get(name);
	}

	@Override
	public Locale getLocale() {
		return locale;
	}
	
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	@Override
	public boolean isXHR() {
		return xhr;
	}
	
	public void setXHR(boolean xhr) {
		this.xhr = xhr;
	}

	@Override
	public boolean isSecure() {
		return secure;
	}
	
	public void setSecure(boolean secure) {
		this.secure = secure;
	}
	
	@Override
	public boolean isRequestedSessionIdValid() {
		return requestedSessionIdValid;
	}
	
	public void setRequestedSessionIdValid(boolean requestedSessionIdValid) {
		this.requestedSessionIdValid = requestedSessionIdValid;
	}

	@Override
	public Object getAttribute(String name) {
		return attributes == null ? null : attributes.get(name);
	}

	@Override
	public void setAttribute(String name, Object value) {
		if (attributes == null) {
			attributes = CollectionFactory.newMap();
		}
		attributes.put(name,  value);
	}
	
	@Override
	public String getPath() {
		throw new UnsupportedOperationException("getPath");
	}

	@Override
	public List<String> getHeaderNames() {
		if (headers == null) {
			return Collections.<String> emptyList();
		}
		List<String> names = CollectionFactory.newList(headers.keySet());
		Collections.sort(names);
		return names;
	}
	
	public void setHeader(String name, String value) {
		if (headers == null) {
			headers = CollectionFactory.newMap();
		}
		headers.put(name, value);
	}

	@Override
	public long getDateHeader(String name) {
		throw new UnsupportedOperationException("getDateHeader");
	}

	@Override
	public String getHeader(String name) {
		return headers == null ? null : headers.get(name);
	}
	
	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public String getMethod() {
		return method;
	}
	
	public void setCookies(Cookie[] cookies) {
		this.cookies = cookies;
	}
	
	@Override
	public Cookie[] getCookies() {
		return cookies;
	}
}
