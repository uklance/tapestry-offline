package org.lazan.t5.offline.services;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.lazan.t5.offline.OfflineRequestContext;

public interface OfflineComponentRenderer {
	void renderPage(Writer writer, OfflineRequestContext context, PageRenderRequestParameters params) throws IOException;
	void renderPage(OutputStream out, OfflineRequestContext context, PageRenderRequestParameters params) throws IOException;
	void renderComponent(Writer writer, OfflineRequestContext context, ComponentEventRequestParameters params) throws IOException;
	JSONObject renderComponent(OfflineRequestContext context, ComponentEventRequestParameters params) throws IOException;
}