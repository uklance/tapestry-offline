package org.lazan.t5.offline.services.internal;

import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.lazan.t5.offline.services.OfflineRequestGlobals;

public class OfflineRequestGlobalsImpl implements OfflineRequestGlobals {
	private final ApplicationGlobals applicationGlobals;
	private final String serverName;
	private final int localPort;
	private final int serverPort;
	private final String remoteHost;
	
	public OfflineRequestGlobalsImpl(
			ApplicationGlobals applicationGlobals,
			@Symbol("tapestry-offline.serverName") String serverName,
			@Symbol("tapestry-offline.remoteHost") String remoteHost,
			@Symbol("tapestry-offline.localPort") int localPort,
			@Symbol("tapestry-offline.serverPort") int serverPort) {
		super();
		this.applicationGlobals = applicationGlobals;
		this.serverName = serverName;
		this.localPort = localPort;
		this.serverPort = serverPort;
		this.remoteHost = remoteHost;
	}

	@Override
	public String getContextPath() {
		return applicationGlobals.getServletContext().getContextPath();
	}

	@Override
	public String getServerName() {
		return serverName;
	}

	@Override
	public int getLocalPort() {
		return localPort;
	}

	@Override
	public int getServerPort() {
		return serverPort;
	}

	@Override
	public String getRemoteHost() {
		return remoteHost;
	}
}
