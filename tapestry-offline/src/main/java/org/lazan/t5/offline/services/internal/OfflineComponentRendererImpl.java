package org.lazan.t5.offline.services.internal;

import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.tapestry5.ioc.Invokable;
import org.apache.tapestry5.ioc.services.ParallelExecutor;
import org.apache.tapestry5.ioc.services.ThreadLocale;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.Response;
import org.lazan.t5.offline.OfflineRequestContext;
import org.lazan.t5.offline.services.OfflineComponentRenderer;
import org.lazan.t5.offline.services.OfflineCookieGlobals;
import org.lazan.t5.offline.services.OfflineObjectFactory;

public class OfflineComponentRendererImpl implements OfflineComponentRenderer {
	private final ParallelExecutor parallelExecutor;
	private final ComponentRequestHandler componentRequestHandler;
	private final RequestGlobals requestGlobals;
	private final ThreadLocale threadLocale;
	private final OfflineObjectFactory offlineObjectFactory;
	private final OfflineCookieGlobals offlineCookieGlobals;

	public OfflineComponentRendererImpl(
			ParallelExecutor parallelExecutor,
			ComponentRequestHandler componentRequestHandler,
			RequestGlobals requestGlobals,
			ApplicationGlobals applicationGlobals,
			TypeCoercer typeCoercer,
			ThreadLocale threadLocale,
			OfflineObjectFactory offlineObjectFactory,
			OfflineCookieGlobals offlineCookieGlobals) {
		super();
		this.parallelExecutor = parallelExecutor;
		this.componentRequestHandler = componentRequestHandler;
		this.requestGlobals = requestGlobals;
		this.threadLocale = threadLocale;
		this.offlineObjectFactory = offlineObjectFactory;
		this.offlineCookieGlobals = offlineCookieGlobals;
	}
	
	@Override
	public Future<?> renderPage(OfflineRequestContext context, PageRenderRequestParameters params, PrintWriter writer) {
		Response response = offlineObjectFactory.createResponse(context, writer);
		return doRender(response, context, params, null, writer);
	}

	@Override
	public Future<?> renderPage(OfflineRequestContext context, PageRenderRequestParameters params, OutputStream out) {
		Response response = offlineObjectFactory.createResponse(context, out);
		return doRender(response, context, params, null, out);
	}
	
	@Override
	public Future<?> renderComponentEvent(OfflineRequestContext context, ComponentEventRequestParameters params, PrintWriter writer) {
		Response response = offlineObjectFactory.createResponse(context, writer);
		return doRender(response, context, null, params, writer);
	}

	@Override
	public Future<?> renderComponentEvent(OfflineRequestContext context, ComponentEventRequestParameters params, OutputStream out) {
		Response response = offlineObjectFactory.createResponse(context, out);
		return doRender(response, context, null, params, out);
	}
	
	@Override
	public Future<JSONObject> renderComponentEvent(OfflineRequestContext context, ComponentEventRequestParameters params) {
		final StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		final Future<?> future = renderComponentEvent(context, params, printWriter);
		return new Future<JSONObject>() {
			public boolean cancel(boolean mayInterruptWhileRunning) {
				return future.cancel(mayInterruptWhileRunning);
			}
			public JSONObject get() throws InterruptedException, ExecutionException {
				future.get();
				return new JSONObject(stringWriter.toString());
			}
			public JSONObject get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
				future.get(timeout, unit);
				return new JSONObject(stringWriter.toString());
			}
			public boolean isCancelled() {
				return future.isCancelled();
			}
			public boolean isDone() {
				return future.isDone();
			}
		};
	}

	/**
	 * Render the content using the {@link ParallelExecutor} to avoid dirtying the current thread.
	 * @return A Future to the rendering operation
	 */
	protected Future<?> doRender(
			final Response response, 
			final OfflineRequestContext requestContext,
			final PageRenderRequestParameters pageParams,
			final ComponentEventRequestParameters componentParams,
			final Flushable flushable) {
		
		final boolean doPage = pageParams != null;
		final boolean doComponent = componentParams != null;
		if (doPage && doComponent) {
			throw new IllegalArgumentException();
		}
		
		Invokable<?> invokable = new Invokable<Object>() {
			public Object invoke() {
				try {
					Request request = offlineObjectFactory.createRequest(requestContext);
					if (requestContext.getLocale() != null) {
						threadLocale.setLocale(requestContext.getLocale());
					}
					offlineCookieGlobals.storeCookies(requestContext.getCookies());
					requestGlobals.storeRequestResponse(request, response);
					if (doPage) {
						componentRequestHandler.handlePageRender(pageParams);
					}
					if (doComponent) {
						componentRequestHandler.handleComponentEvent(componentParams);
					}
					flushable.flush();
					return null;
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		};
		return parallelExecutor.invoke(invokable);
	}
}
