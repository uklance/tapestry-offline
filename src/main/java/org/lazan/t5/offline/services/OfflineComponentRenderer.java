package org.lazan.t5.offline.services;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.Future;

import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.lazan.t5.offline.OfflineRequestContext;

/**
 * Renders pages and components in situations that were not triggered by a HTTPServletRequest
 */
public interface OfflineComponentRenderer {
	/**
	 * Render a page response to a {@link Writer}
	 * @param writer Writer used to construct a {@link Response} 
	 * @param context Offline request context (used to construct a {@link Request})
	 * @param params Defines the page and activation context
	 */
	Future<?> renderPage(PrintWriter writer, OfflineRequestContext context, PageRenderRequestParameters params) throws IOException;

	/**
	 * Render a binary page response to an {@link OutputStream}
	 * @param out OutputStream used to construct a {@link Response} 
	 * @param context Offline request context (used to construct a {@link Request})
	 * @param params Defines the page and activation context
	 */
	Future<?> renderPage(OutputStream out, OfflineRequestContext context, PageRenderRequestParameters params) throws IOException;

	/**
	 * Render a component event response to a {@link Writer}
	 * @param writer Writer used to construct a {@link Response} 
	 * @param context Offline request context (used to construct a {@link Request})
	 * @param params Defines the component event and page activation context
	 */
	Future<?> renderComponentEvent(PrintWriter writer, OfflineRequestContext context, ComponentEventRequestParameters params) throws IOException;

	/**
	 * Render a component event response as a {@link JSONObject}
	 * @param context Offline request context (used to construct a {@link Request})
	 * @param params Defines the component event and page activation context
	 * @return a JSONObject component event response containing 'content', 'scripts' and 'inits' etc.
	 */
	Future<JSONObject> renderComponentEvent(OfflineRequestContext context, ComponentEventRequestParameters params) throws IOException;
}
