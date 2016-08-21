package org.lazan.t5.offline.services;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;

import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.apache.tapestry5.services.Response;

/**
 * Renders pages and components in situations that were not triggered by a HTTPServletRequest
 */
public interface OfflineComponentRenderer {
	/**
	 * Render a page response to a {@link PrintWriter}
	 * @param request Offline request see {@link OfflineRequestBuilder)
	 * @param params Defines the page and activation context
	 * @param writer Writer used to construct a {@link Response} 
	 * @return A {@link Future} to the rendering operation. Call {@link Future.get()} before attempting to access the rendered content.
	 */
	Future<?> renderPage(HttpServletRequest request, PageRenderRequestParameters params, PrintWriter writer);

	/**
	 * Render a binary page response to an {@link OutputStream}
	 * @param request Offline request see {@link OfflineRequestBuilder)
	 * @param params Defines the page and activation context
	 * @param out OutputStream used to construct a {@link Response} 
	 * @return A {@link Future} to the rendering operation. Call {@link Future.get()} before attempting to access the rendered content.
	 */
	Future<?> renderPage(HttpServletRequest request, PageRenderRequestParameters params, OutputStream out);

	/**
	 * Render a component event response to a {@link PrintWriter}
	 * @param request Offline request see {@link OfflineRequestBuilder)
	 * @param params Defines the component event and page activation context
	 * @param writer Writer used to construct a {@link Response} 
	 * @return A {@link Future} to the rendering operation. Call {@link Future.get()} before attempting to access the rendered content.
	 */
	Future<?> renderComponentEvent(HttpServletRequest request, ComponentEventRequestParameters params, PrintWriter writer);

	/**
	 * Render a binary event response to an {@link OutputStream}
	 * @param request Offline request see {@link OfflineRequestBuilder)
	 * @param params Defines the component event and page activation context
	 * @param writer Writer used to construct a {@link Response} 
	 * @return A {@link Future} to the rendering operation. Call {@link Future.get()} before attempting to access the rendered content.
	 */
	Future<?> renderComponentEvent(HttpServletRequest request, ComponentEventRequestParameters params, OutputStream out);
	
	/**
	 * Render a component event response as a {@link JSONObject}
	 * @param request Offline request see {@link OfflineRequestBuilder)
	 * @param params Defines the component event and page activation context
	 * @return a JSONObject component event response containing 'content', 'scripts' and 'inits' etc.
	 * @return A {@link Future} to the rendering operation. Call {@link Future.get()} to obtain the JSONObject.
	 */
	Future<JSONObject> renderComponentEvent(HttpServletRequest request, ComponentEventRequestParameters params);
}
