package org.lazan.t5.offline.services.internal;

import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.lazan.t5.offline.OfflineRequestContext;
import org.lazan.t5.offline.internal.OfflineRequest;
import org.lazan.t5.offline.internal.OfflineResponse;
import org.lazan.t5.offline.services.OfflineObjectFactory;
import org.lazan.t5.offline.services.OfflineRequestGlobals;
import org.lazan.t5.offline.services.OfflineResponseGlobals;

public class OfflineObjectFactoryImpl implements OfflineObjectFactory {
	private final OfflineRequestGlobals offlineRequestGlobals;
	private final OfflineResponseGlobals offlineResponseGlobals;
	
	public OfflineObjectFactoryImpl(
			OfflineRequestGlobals offlineRequestGlobals,
			OfflineResponseGlobals offlineResponseGlobals) {
		this.offlineRequestGlobals = offlineRequestGlobals;
		this.offlineResponseGlobals = offlineResponseGlobals;
	}

	@Override
	public Request createRequest(OfflineRequestContext requestContext) {
		return new OfflineRequest(offlineRequestGlobals, requestContext);
	}

	@Override
	public Response createResponse(OfflineRequestContext context, PrintWriter writer) {
		return new OfflineResponse(offlineResponseGlobals, writer);
	}
	
	@Override
	public Response createResponse(OfflineRequestContext requestContext, OutputStream out) {
		return new OfflineResponse(offlineResponseGlobals, out);
	}
}
