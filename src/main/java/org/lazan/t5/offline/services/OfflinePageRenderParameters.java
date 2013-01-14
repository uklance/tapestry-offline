package org.lazan.t5.offline.services;

import java.util.Locale;
import java.util.Map;

import org.apache.tapestry5.services.PageRenderRequestParameters;

public class OfflinePageRenderParameters {
	private Map<String, Object> sessionAttributes;
	private Locale locale;
	private boolean secure;
	private PageRenderRequestParameters pageRenderRequestParameters;
	public Map<String, Object> getSessionAttributes() {
		return sessionAttributes;
	}
	public void setSessionAttributes(Map<String, Object> sessionAttributes) {
		this.sessionAttributes = sessionAttributes;
	}
	public Locale getLocale() {
		return locale;
	}
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	public boolean isSecure() {
		return secure;
	}
	public void setSecure(boolean secure) {
		this.secure = secure;
	}
	public PageRenderRequestParameters getPageRenderRequestParameters() {
		return pageRenderRequestParameters;
	}
	public void setPageRenderRequestParameters(PageRenderRequestParameters pageRenderRequestParameters) {
		this.pageRenderRequestParameters = pageRenderRequestParameters;
	}
}
