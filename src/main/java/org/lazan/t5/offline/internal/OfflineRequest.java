package org.lazan.t5.offline.internal;

import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Session;
import org.lazan.t5.offline.OfflineRequestContext;
import org.lazan.t5.offline.services.OfflineRequestGlobals;

public class OfflineRequest implements Request {
	private final OfflineRequestGlobals globals;
	private final OfflineRequestContext context;
	
	public OfflineRequest(OfflineRequestGlobals globals, OfflineRequestContext context) {
		super();
		this.globals = globals;
		this.context = context;
	}

	@Override
	public Session getSession(boolean create) {
		return context.getSession(create);
	}

	@Override
	public String getContextPath() {
		return globals.getContextPath();
	}

	@Override
	public List<String> getParameterNames() {
		return context.getParameterNames();
	}

	@Override
	public String getParameter(String name) {
		return context.getParameter(name);
	}

	@Override
	public String[] getParameters(String name) {
		return context.getParameters(name);
	}

	@Override
	public String getPath() {
		return context.getPath();
	}

	@Override
	public Locale getLocale() {
		return context.getLocale();
	}

	@Override
	public List<String> getHeaderNames() {
		return context.getHeaderNames();
	}

	@Override
	public long getDateHeader(String name) {
		return context.getDateHeader(name);
	}

	@Override
	public String getHeader(String name) {
		return context.getHeader(name);
	}

	@Override
	public boolean isXHR() {
		return context.isXHR();
	}

	@Override
	public boolean isSecure() {
		return context.isSecure();
	}

	@Override
	public String getServerName() {
		return globals.getServerName();
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		return context.isRequestedSessionIdValid();
	}

	@Override
	public Object getAttribute(String name) {
		return context.getAttribute(name);
	}

	@Override
	public void setAttribute(String name, Object value) {
		context.setAttribute(name, value);
	}

	@Override
	public String getMethod() {
		return context.getMethod();
	}

	@Override
	public int getLocalPort() {
		return globals.getLocalPort();
	}

	@Override
	public int getServerPort() {
		return globals.getServerPort();
	}

	@Override
	public String getRemoteHost() {
		return globals.getRemoteHost();
	}
}
