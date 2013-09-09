package org.lazan.t5.offline.services.internal;

import org.lazan.t5.offline.services.OfflineResponseGlobals;

public class OfflineResponseGlobalsImpl implements OfflineResponseGlobals {
	@Override
	public String encodeRedirectURL(String URL) {
		throw new UnsupportedOperationException("encodeRedirectURL");
	}
	
	@Override
	public String encodeURL(String URL) {
		return URL;
	}
}
