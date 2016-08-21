package org.lazan.t5.offline.services;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.Future;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.TapestryFilter;
import org.apache.tapestry5.internal.AbstractEventContext;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.After;
import org.junit.Test;

public class OfflineComponentRendererIntegrationTest {
	private Server server;

	@Test
	public void testRenderPage() throws Exception {
		Registry registry = startServer();

		OfflineComponentRenderer renderer = registry.getService(OfflineComponentRenderer.class);
		OfflineRequestBuilder requestBuilder = registry.getService(OfflineRequestBuilderFactory.class).create();
		HttpServletRequest request = requestBuilder.build();
		EventContext pageContext = createEventContext(10);
		PageRenderRequestParameters params = new PageRenderRequestParameters("TestPage", pageContext, false);
		//StringWriter writer = new StringWriter();
		//Future<?> future = renderer.renderPage(request, params, new PrintWriter(writer));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Future<?> future = renderer.renderPage(request, params, out);
		future.get();

		String html = new String(out.toByteArray(), request.getCharacterEncoding());

		assertTrue(html.contains("Page counting to 10"));
		assertTrue(html.contains("(1 2 3 4 5 6 7 8 9 10 )"));
	}

	@Test
	public void testRenderEvent() throws Exception {
		Registry registry = startServer();

		OfflineComponentRenderer renderer = registry.getService(OfflineComponentRenderer.class);
		OfflineRequestBuilder requestBuilder = registry.getService(OfflineRequestBuilderFactory.class).create();
		HttpServletRequest request = requestBuilder
				.setXHR()
				.build();
		EventContext pageContext = createEventContext(10);
		EventContext eventContext = createEventContext(5);

		//StringWriter writer = new StringWriter();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ComponentEventRequestParameters params = new ComponentEventRequestParameters("TestPage", "TestPage", "", "TestEvent", pageContext, eventContext);
		//Future<?> future = renderer.renderComponentEvent(request, params, new PrintWriter(writer));
		Future<?> future = renderer.renderComponentEvent(request, params, out);
		future.get();

		String json = new String(out.toByteArray(), request.getCharacterEncoding());

		assertTrue(json, json.contains("Event counting to 5"));
		assertTrue(json, json.contains("(1 2 3 4 5 )"));
	}
	
	protected Registry startServer() throws Exception {
		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/");
		webapp.setResourceBase("src/test/webapp");
		webapp.setClassLoader(OfflineComponentRendererIntegrationTest.class.getClassLoader());
		server = new Server(9090);
		server.setHandler(webapp);
		server.start();

		ServletContext servletContext = webapp.getServletContext();
		Registry registry = (Registry) servletContext.getAttribute(TapestryFilter.REGISTRY_CONTEXT_NAME);
		return registry;
	}
	
	private EventContext createEventContext(final Object value) {
		return new AbstractEventContext() {
			@Override
			public int getCount() {
				return 1;
			}
			
			@Override
			public <T> T get(Class<T> desiredType, int index) {
				return desiredType.cast(value);
			}
		};
	}

	@After
	public void after() throws Exception {
		if (server != null) {
			server.stop();
		}
	}
}
