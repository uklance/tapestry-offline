package org.lazan.t5.offline.services.internal;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Future;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.internal.EmptyEventContext;
import org.apache.tapestry5.internal.services.ArrayEventContext;
import org.apache.tapestry5.ioc.Invokable;
import org.apache.tapestry5.ioc.services.ParallelExecutor;
import org.apache.tapestry5.ioc.services.ThreadLocale;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.apache.tapestry5.services.RequestGlobals;
import org.lazan.t5.offline.internal.OfflineRequest;
import org.lazan.t5.offline.internal.OfflineResponse;
import org.lazan.t5.offline.internal.OfflineSession;
import org.lazan.t5.offline.services.OfflinePageRenderer;

public class OfflinePageRendererImpl implements OfflinePageRenderer {
	private final ParallelExecutor parallelExecutor;
	private final ComponentRequestHandler componentRequestHandler;
	private final RequestGlobals requestGlobals;
	private final ApplicationGlobals applicationGlobals;
	private final TypeCoercer typeCoercer;
	private final ThreadLocale threadLocale;

	public OfflinePageRendererImpl(
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
		this.applicationGlobals = applicationGlobals;
		this.typeCoercer = typeCoercer;
		this.threadLocale = threadLocale;
	}
	
	@Override
	public void renderPage(final Writer writer, final String logicalPageName, final Object[] activationContext, final Map<String, Object> sessionAttributes, final Locale locale) throws IOException {
		final IOException[] mutableException = new IOException[1];
		Invokable<Void> invokable = new Invokable<Void>() {
			public Void invoke() {
				EventContext eventContext;
				if (activationContext == null) {
					eventContext = new EmptyEventContext();
				} else {
					eventContext = new ArrayEventContext(typeCoercer, activationContext);
				}
				PrintWriter printWriter = new PrintWriter(writer);

				OfflineSession session = new OfflineSession();
				if (sessionAttributes != null) {
					for (Map.Entry<String, Object> entry : sessionAttributes.entrySet()) {
						session.setAttribute(entry.getKey(), entry.getValue());
					}
				}
				String contextPath = applicationGlobals.getServletContext().getContextPath();

				PageRenderRequestParameters pageParams = new PageRenderRequestParameters(logicalPageName, eventContext, false);
				OfflineRequest request = new OfflineRequest(session, contextPath, false);
				OfflineResponse response = new OfflineResponse(printWriter);
				
				if (locale != null) {
					threadLocale.setLocale(locale);
				}
				requestGlobals.storeRequestResponse(request, response);
				try {
					componentRequestHandler.handlePageRender(pageParams);
					printWriter.flush();
				} catch (IOException e) {
					mutableException[0] = e;
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
		if (mutableException[0] != null) {
			throw mutableException[0];
		}
	}
}
