package org.lazan.t5.offline.services.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.concurrent.Future;

import org.apache.tapestry5.ioc.Invokable;
import org.apache.tapestry5.ioc.services.ParallelExecutor;
import org.apache.tapestry5.ioc.services.ThreadLocale;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.Response;
import org.lazan.t5.offline.OfflineRequestContext;
import org.lazan.t5.offline.internal.OfflineRequest;
import org.lazan.t5.offline.internal.OfflineResponse;
import org.lazan.t5.offline.internal.Pointer;
import org.lazan.t5.offline.services.OfflineComponentRenderer;
import org.lazan.t5.offline.services.OfflineRequestGlobals;
import org.lazan.t5.offline.services.OfflineResponseGlobals;

public class OfflineComponentRendererImpl implements OfflineComponentRenderer {
	private final ParallelExecutor parallelExecutor;
	private final ComponentRequestHandler componentRequestHandler;
	private final RequestGlobals requestGlobals;
	private final ThreadLocale threadLocale;
	private final OfflineRequestGlobals offlineRequestGlobals;
	private final OfflineResponseGlobals offlineResponseGlobals;

	public OfflineComponentRendererImpl(
			ParallelExecutor parallelExecutor,
			ComponentRequestHandler componentRequestHandler,
			RequestGlobals requestGlobals,
			ApplicationGlobals applicationGlobals,
			TypeCoercer typeCoercer,
			ThreadLocale threadLocale,
			OfflineRequestGlobals offlineRequestGlobals,
			OfflineResponseGlobals offlineResponseGlobals) {
		super();
		this.parallelExecutor = parallelExecutor;
		this.componentRequestHandler = componentRequestHandler;
		this.requestGlobals = requestGlobals;
		this.threadLocale = threadLocale;
		this.offlineRequestGlobals = offlineRequestGlobals;
		this.offlineResponseGlobals = offlineResponseGlobals;
	}
	
	@Override
	public void renderPage(Writer writer, OfflineRequestContext context, PageRenderRequestParameters params) throws IOException {
		PrintWriter printWriter = new PrintWriter(writer);
		OfflineResponse response = new OfflineResponse(offlineResponseGlobals, printWriter);
		doRender(response, context, params, null);
		printWriter.flush();
	}

	@Override
	public void renderPage(OutputStream out, OfflineRequestContext context, PageRenderRequestParameters params) throws IOException {
		OfflineResponse response = new OfflineResponse(offlineResponseGlobals, out);
		doRender(response, context, params, null);
		out.flush();
	}
	
	@Override
	public void renderComponent(Writer writer, OfflineRequestContext context, ComponentEventRequestParameters params) throws IOException {
		PrintWriter printWriter = new PrintWriter(writer);
		Response response = new OfflineResponse(offlineResponseGlobals, printWriter);
		doRender(response, context, null, params);
		printWriter.flush();
	}

	@Override
	public JSONObject renderComponent(OfflineRequestContext context, ComponentEventRequestParameters params) throws IOException {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		Response response = new OfflineResponse(offlineResponseGlobals, printWriter);
		doRender(response, context, null, params);
		printWriter.flush();
		return new JSONObject(stringWriter.toString());
	}

	protected void doRender(
			final Response response, 
			final OfflineRequestContext requestContext,
			final PageRenderRequestParameters pageParams,
			final ComponentEventRequestParameters componentParams) throws IOException {
		
		final boolean doPage = pageParams != null;
		final boolean doComponent = componentParams != null;
		if (doPage && doComponent) {
			throw new IllegalArgumentException();
		}
		final Pointer<IOException> exception = new Pointer<IOException>();
		Invokable<Void> invokable = new Invokable<Void>() {
			public Void invoke() {
				OfflineRequest request = new OfflineRequest(offlineRequestGlobals, requestContext);
				if (requestContext.getLocale() != null) {
					threadLocale.setLocale(requestContext.getLocale());
				}
				requestGlobals.storeRequestResponse(request, response);
				try {
					if (doPage) {
						componentRequestHandler.handlePageRender(pageParams);
					}
					if (doComponent) {
						componentRequestHandler.handleComponentEvent(componentParams);
					}
				} catch (IOException e) {
					exception.set(e);
				}
				return null;
			}
		};
		Future<Void> future = parallelExecutor.invoke(invokable);
		try {
			future.get();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if (exception.get() != null) {
			throw exception.get();
		}
	}
}
