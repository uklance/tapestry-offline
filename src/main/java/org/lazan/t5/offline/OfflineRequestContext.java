package org.lazan.t5.offline;

import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.services.Session;

public interface OfflineRequestContext {
	public Session getSession(boolean create);

	public List<String> getParameterNames();

	public String getParameter(String name);

	public String[] getParameters(String name);

	public String getPath();

	public Locale getLocale();

	public List<String> getHeaderNames();

	public long getDateHeader(String name);

	public String getHeader(String name);

	public boolean isXHR();

	public boolean isSecure();

	public boolean isRequestedSessionIdValid();

	public Object getAttribute(String name);

	public void setAttribute(String name, Object value);

	public String getMethod();
}
