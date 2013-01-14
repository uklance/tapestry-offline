package org.lazan.t5.offline.services;

import java.util.Locale;
import java.util.Map;

import org.apache.tapestry5.services.ComponentEventRequestParameters;

public class OfflineComponentRenderParameters {
	private Map<String, Object> sessionAttributes;
	private Locale locale;
	private boolean secure;
	private ComponentEventRequestParameters componentEventRequestParameters;

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

	public ComponentEventRequestParameters getComponentEventRequestParameters() {
		return componentEventRequestParameters;
	}

	public void setComponentEventRequestParameters(ComponentEventRequestParameters componentEventRequestParameters) {
		this.componentEventRequestParameters = componentEventRequestParameters;
	}
}
