package org.lazan.t5.offline.services;

import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.lazan.t5.offline.OfflineRequestContext;

public interface OfflineObjectFactory {

	Request createRequest(OfflineRequestContext requestContext);
	
	Response createResponse(PrintWriter writer, OfflineRequestContext requestContext);

	Response createResponse(OutputStream out, OfflineRequestContext requestContext);

}
