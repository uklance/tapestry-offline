package org.lazan.t5.offline.services.internal;

import org.apache.tapestry5.services.ApplicationGlobals;
import org.lazan.t5.offline.services.OfflineRequestGlobals;

public class OfflineRequestGlobalsImpl implements OfflineRequestGlobals {
	private final ApplicationGlobals applicationGlobals;
	
	public OfflineRequestGlobalsImpl(ApplicationGlobals applicationGlobals) {
		super();
		this.applicationGlobals = applicationGlobals;
	}

	@Override
	public String getContextPath() {
		return applicationGlobals.getServletContext().getContextPath();
	}

	@Override
	public String getServerName() {
		throw new UnsupportedOperationException("getServerName");
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
