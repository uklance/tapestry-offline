package org.lazan.t5.offline.internal;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.services.Session;

public class OfflineSession implements Session {
	private Map<String, Object> attributes;
	private boolean invalidated = false;
	
	public OfflineSession() {
	}

	@Override
	public List<String> getAttributeNames() {
		if (attributes == null) {
			return Collections.emptyList();
		}
		List<String> names = CollectionFactory.newList(attributes.keySet());
		Collections.sort(names);
		return names;
	}

	@Override
	public List<String> getAttributeNames(String prefix) {
		if (attributes == null) {
			return Collections.emptyList();
		}
		List<String> names = CollectionFactory.newList();
		for (String name : attributes.keySet()) {
			if (name.startsWith(prefix)) {
				names.add(name);
			}
		}
		Collections.sort(names);
		return names;
	}

	@Override
	public Object getAttribute(String name) {
		return attributes == null ? null : attributes.get(name);
	}

	@Override
	public void setAttribute(String name, Object value) {
		if (attributes == null) {
			attributes = CollectionFactory.newMap();
		}
		attributes.put(name,  value);
	}

	@Override
	public int getMaxInactiveInterval() {
		throw new UnsupportedOperationException("getMaxInactiveInterval");
	}

	@Override
	public void setMaxInactiveInterval(int seconds) {
		throw new UnsupportedOperationException("setMaxInactiveInterval");
	}

	@Override
	public void invalidate() {
		attributes = null;
		invalidated = true;
	}

	@Override
	public boolean isInvalidated() {
		return invalidated;
	}

	@Override
	public void restoreDirtyObjects() {
	}
}
