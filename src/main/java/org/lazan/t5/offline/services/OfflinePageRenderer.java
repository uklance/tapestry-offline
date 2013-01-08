package org.lazan.t5.offline.services;

import java.io.IOException;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

public interface OfflinePageRenderer {

	void renderPage(final Writer writer, final String logicalPageName, final Object[] activationContext,
			final Map<String, Object> sessionAttributes, final Locale locale) throws IOException;

}