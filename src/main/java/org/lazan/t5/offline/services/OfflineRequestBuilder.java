package org.lazan.t5.offline.services;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

public interface OfflineRequestBuilder {
	HttpServletRequest build();
	OfflineRequestBuilder setXHR();
	OfflineRequestBuilder withHeader(String name, Object value);
	OfflineRequestBuilder withSessionAttribute(String name, Object value);
	OfflineRequestBuilder withValue(String name, Object value);
	OfflineRequestBuilder withLocale(Locale locale);
	OfflineRequestBuilder withParameter(String name, String value);
	OfflineRequestBuilder withAttribute(String name, Object value);
	OfflineRequestBuilder withContentType(String contentType);
}
