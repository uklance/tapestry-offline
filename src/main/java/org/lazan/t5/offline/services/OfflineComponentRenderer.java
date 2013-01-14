package org.lazan.t5.offline.services;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import org.apache.tapestry5.json.JSONObject;

public interface OfflineComponentRenderer {
	void renderPage(Writer writer, OfflinePageRenderParameters params) throws IOException;
	void renderPage(OutputStream out, OfflinePageRenderParameters params) throws IOException;
	JSONObject renderComponent(OfflineComponentRenderParameters params) throws IOException;
}