package org.lazan.t5.offline.services.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;
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
import org.lazan.t5.offline.internal.OfflineRequest;
import org.lazan.t5.offline.internal.OfflineResponse;
import org.lazan.t5.offline.internal.OfflineSession;
import org.lazan.t5.offline.internal.Pointer;
import org.lazan.t5.offline.services.OfflineComponentRenderParameters;
import org.lazan.t5.offline.services.OfflineComponentRenderer;
import org.lazan.t5.offline.services.OfflinePageRenderParameters;

public class OfflineComponentRendererImpl implements OfflineComponentRenderer {
	private final ParallelExecutor parallelExecutor;
	private final ComponentRequestHandler componentRequestHandler;
	private final RequestGlobals requestGlobals;
	private final String contextPath;
	private final ThreadLocale threadLocale;

	public OfflineComponentRendererImpl(
			ParallelExecutor parallelExecutor,
			ComponentRequestHandler componentRequestHandler,
			RequestGlobals requestGlobals,
			ApplicationGlobals applicationGlobals,
			TypeCoercer typeCoercer,
			ThreadLocale threadLocale) {
		super();
		this.parallelExecutor = parallelExecutor;
		this.componentRequestHandler = componentRequestHandler;
		this.requestGlobals = requestGlobals;
		this.contextPath = applicationGlobals.getServletContext().getContextPath();
		this.threadLocale = threadLocale;
	}
	
	@Override
	public void renderPage(Writer writer, OfflinePageRenderParameters params) throws IOException {
		PrintWriter printWriter = new PrintWriter(writer);
		OfflineResponse response = new OfflineResponse(printWriter);
		doRender(response, params.getSessionAttributes(), params.getLocale(), params.isSecure(), params.getPageRenderRequestParameters(), null);
		printWriter.flush();
	}

	@Override
	public void renderPage(OutputStream out, OfflinePageRenderParameters params) throws IOException {
		OfflineResponse response = new OfflineResponse(out);
		doRender(response, params.getSessionAttributes(), params.getLocale(), params.isSecure(), params.getPageRenderRequestParameters(), null);
		out.flush();
	}
	
	@Override
	public JSONObject renderComponent(OfflineComponentRenderParameters params) throws IOException {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		Response response = new OfflineResponse(printWriter);
		doRender(response, params.getSessionAttributes(), params.getLocale(), params.isSecure(), null, params.getComponentEventRequestParameters());
		printWriter.flush();
		return new JSONObject(stringWriter.toString());
	}

	protected void doRender(
			final Response response, 
			final Map<String, Object> sessionAttributes,
			final Locale locale,
			final boolean secure,
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
				OfflineSession session = new OfflineSession();
				if (sessionAttributes != null) {
					for (Map.Entry<String, Object> entry : sessionAttributes.entrySet()) {
						session.setAttribute(entry.getKey(), entry.getValue());
					}
				}
				boolean xhr = doComponent;
				OfflineRequest request = new OfflineRequest(session, contextPath, xhr, secure);
				if (locale != null) {
					threadLocale.setLocale(locale);
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
