package org.lazan.t5.offline.services.internal;

import java.util.Map;

import org.lazan.t5.offline.services.OfflineResponseGlobals;

public class OfflineResponseGlobalsImpl implements OfflineResponseGlobals {
	private final Map<String, Object> values;
	
	public OfflineResponseGlobalsImpl(Map<String, Object> values) {
		super();
		this.values = values;
	}

	@Override
	public String encodeRedirectURL(String URL) {
		throw new UnsupportedOperationException("encodeRedirectURL");
	}
	
	@Override
	public String encodeURL(String URL) {
		return URL;
	}
	
	@Override
	public Map<String, Object> getValues() {
		return values;
	}
}
