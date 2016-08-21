package org.lazan.t5.offline.services.internal;

import java.util.Map;

import org.lazan.t5.offline.services.OfflineRequestGlobals;

public class OfflineRequestGlobalsImpl implements OfflineRequestGlobals {
	private final Map<String, Object> values;
	
	public OfflineRequestGlobalsImpl(Map<String, Object> values) {
		super();
		this.values = values;
	}

	@Override
	public Map<String, Object> getValues() {
		return values;
	}
}
