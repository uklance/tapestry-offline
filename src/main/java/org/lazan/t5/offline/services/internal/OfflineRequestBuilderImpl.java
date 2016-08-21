package org.lazan.t5.offline.services.internal;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.lazan.t5.offline.services.OfflineRequestBuilder;
import org.lazan.t5.offline.services.OfflineRequestGlobals;
import org.lazan.t5.offline.services.internal.ProxyBuilder.MethodHandler;

public class OfflineRequestBuilderImpl implements OfflineRequestBuilder {
	private final Map<String, Object> requestValues;
	private Map<String, Object> sessionAttributes = new LinkedHashMap<String, Object>();
	private Map<String, Object> headers = new LinkedHashMap<String, Object>();
	private Map<String, Object> attributes = new LinkedHashMap<String, Object>();
	private Map<String, String> parameters = new LinkedHashMap<String, String>();
	
	private final TypeCoercer typeCoercer;
	private HttpSession session;
	
	public OfflineRequestBuilderImpl(OfflineRequestGlobals offlineRequestGlobals, TypeCoercer typeCoercer) {
		super();
		this.requestValues = new LinkedHashMap<String, Object>(offlineRequestGlobals.getValues());
		this.typeCoercer = typeCoercer;
	}
	
	@Override
	public OfflineRequestBuilder withValue(String name, Object value) {
		requestValues.put(name,  value);
		return this;
	}
	
	@Override
	public OfflineRequestBuilder withLocale(Locale locale) {
		return withValue("locale", locale);
	}
	
	@Override
	public OfflineRequestBuilder withSessionAttribute(String name, Object value) {
		sessionAttributes.put(name,  value);
		return this;
	}

	@Override
	public OfflineRequestBuilder withAttribute(String name, Object value) {
		attributes.put(name,  value);
		return this;
	}
	
	@Override
	public OfflineRequestBuilder withHeader(String name, Object value) {
		headers.put(name, value);
		return this;
	}

	@Override
	public OfflineRequestBuilder withParameter(String name, String value) {
		parameters.put(name, value);
		return this;
	}
	
	@Override
	public OfflineRequestBuilder setXHR() {
		return withHeader("X-Requested-With", "XMLHttpRequest");
	}
	
	@Override
	public OfflineRequestBuilder withContentType(String contentType) {
		return withValue("contentType", contentType);
	}

	@Override
	public HttpServletRequest build() {
		MethodHandler getHeaderHandler = new MethodHandler() {
			@Override
			public Object handle(Method method, Object[] args) {
				return headers.get(args[0]);
			}
		};
		MethodHandler getAttributeHandler = new MethodHandler() {
			@Override
			public Object handle(Method method, Object[] args) {
				return attributes.get(args[0]);
			}
		};
		MethodHandler setAttributeHandler = new MethodHandler() {
			@Override
			public Object handle(Method method, Object[] args) {
				return attributes.put((String) args[0], args[1]);
			}
		};
		MethodHandler getParameterHandler = new MethodHandler() {
			@Override
			public Object handle(Method method, Object[] args) {
				return parameters.get(args[0]);
			}
		};
		MethodHandler getSessionHandler = createGetSessionHandler();
		return new ProxyBuilder(typeCoercer)
			.withMethodHandler("getSession", getSessionHandler)
			.withMethodHandler("getHeader", getHeaderHandler)
			.withMethodHandler("getParameter", getParameterHandler)
			.withMethodHandler("getAttribute", getAttributeHandler)
			.withMethodHandler("setAttribute", setAttributeHandler)
			.withDefaultValues(requestValues)
			.build(HttpServletRequest.class);
	}

	private MethodHandler createGetSessionHandler() {
		MethodHandler getSessionHandler = new MethodHandler() {
			@Override
			public Object handle(Method method, Object[] args) {
				if (session != null) return session;
				boolean create = method.getParameterTypes().length == 0 || Boolean.TRUE.equals(args[0]);
				if (!create) return null;
				return createSession();
			}
		};
		return getSessionHandler;
	}

	private HttpSession createSession() {
		MethodHandler getAttributeHandler = new MethodHandler() {
			@Override
			public Object handle(Method method, Object[] args) {
				return sessionAttributes.get((String) args[0]);
			}
		};
		MethodHandler setAttributeHandler = new MethodHandler() {
			@Override
			public Object handle(Method method, Object[] args) {
				return sessionAttributes.put((String) args[0], args[1]);
			}
		};
		session = new ProxyBuilder(typeCoercer)
				.withMethodHandler("getAttribute", getAttributeHandler)
				.withMethodHandler("setAttribute", setAttributeHandler)
				.build(HttpSession.class);
		return session;
	}
}