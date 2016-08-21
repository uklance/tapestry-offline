package org.lazan.t5.offline.services.internal;

import javax.servlet.http.HttpServletResponse;

import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.lazan.t5.offline.services.OfflineObjects;

public class OfflineObjectsImpl implements OfflineObjects {
	private final Request request;
	private final HttpServletResponse httpResponse;
	private final Response response;
	
	public OfflineObjectsImpl(Request request, HttpServletResponse httpResponse, Response response) {
		super();
		this.request = request;
		this.httpResponse = httpResponse;
		this.response = response;
	}

	@Override
	public Request getRequest() {
		return request;
	}
	
	@Override
	public HttpServletResponse getHttpServletResponse() {
		return httpResponse;
	}
	
	@Override
	public Response getResponse() {
		return response;
	}
}
