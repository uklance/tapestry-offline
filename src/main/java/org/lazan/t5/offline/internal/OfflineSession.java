package org.lazan.t5.offline.internal;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.services.Session;

public class OfflineSession implements Session {
	private final Map<String, Object> attributes;
	private boolean invalidated = false;
	
	public OfflineSession(Map<String, Object> attributes) {
		this.attributes = CollectionFactory.newMap(attributes);
	}

	@Override
	public List<String> getAttributeNames() {
		List<String> names = CollectionFactory.newList(attributes.keySet());
		Collections.sort(names);
		return names;
	}

	@Override
	public List<String> getAttributeNames(String prefix) {
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
		return attributes.get(name);
	}

	@Override
	public void setAttribute(String name, Object value) {
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
		attributes.clear();
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
