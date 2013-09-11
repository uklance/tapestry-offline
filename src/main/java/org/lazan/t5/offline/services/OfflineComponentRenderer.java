package org.lazan.t5.offline.services;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.lazan.t5.offline.OfflineRequestContext;
import org.apache.tapestry5.services.Request;

/**
 * Renders pages and components in situations that were not triggered by a HTTPServletRequest
 */
public interface OfflineComponentRenderer {
	/**
	 * Render a page response to a {@link Writer}
	 * @param writer Writer
	 * @param context Offline request context (used to construct a {@link Request})
	 * @param params Defines the page and activation context
	 */
	void renderPage(Writer writer, OfflineRequestContext context, PageRenderRequestParameters params) throws IOException;

	/**
	 * Render a binary page response to an {@link OutputStream}
	 * @param out OutputStream
	 * @param context Offline request context (used to construct a {@link Request})
	 * @param params Defines the page and activation context
	 */
	void renderPage(OutputStream out, OfflineRequestContext context, PageRenderRequestParameters params) throws IOException;

	/**
	 * Render a component event response to a {@link Writer}
	 * @param writer Writer
	 * @param context Offline request context (used to construct a {@link Request})
	 * @param params Defines the component event and page activation context
	 */
	void renderComponent(Writer writer, OfflineRequestContext context, ComponentEventRequestParameters params) throws IOException;

	/**
	 * Render a component event response as a {@link JSONObject}
	 * @param context Offline request context (used to construct a {@link Request})
	 * @param params Defines the component event and page activation context
	 * @return a JSONObject component event response containing 'content' and 'scripts' etc.
	 */
	JSONObject renderComponent(OfflineRequestContext context, ComponentEventRequestParameters params) throws IOException;
}
