package org.lazan.t5.offline.services;

import java.util.Map;

public interface OfflineResponseGlobals {
	String encodeURL(String URL);
	String encodeRedirectURL(String URL);
	Map<String, Object> getValues();
}
