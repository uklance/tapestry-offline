package org.lazan.t5.offline.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.services.Response;
import org.lazan.t5.offline.services.OfflineResponseGlobals;

public class OfflineResponse implements Response {
	private PrintWriter printWriter;
	private OutputStream out;
	private final OfflineResponseGlobals responseGlobals;
	
	public OfflineResponse(OfflineResponseGlobals offlineResponseGlobals, PrintWriter printWriter) {
		this.responseGlobals = offlineResponseGlobals;
		this.printWriter = printWriter;
	}
	
	public OfflineResponse(OfflineResponseGlobals offlineResponseGlobals, OutputStream out) {
		this.responseGlobals = offlineResponseGlobals;
		this.out = out;
	}

	@Override
	public PrintWriter getPrintWriter(String contentType) throws IOException {
		if (printWriter == null) {
			throw new IllegalStateException("PrintWriter not set");
		}
		return printWriter;
	}

	@Override
	public OutputStream getOutputStream(String contentType) throws IOException {
		if (out == null) {
			throw new IllegalStateException("OutputStream not set");
		}
		return out;
	}

	@Override
	public void sendRedirect(String URL) throws IOException {
		throw new UnsupportedOperationException("sendRedirect");
	}

	@Override
	public void sendRedirect(Link link) throws IOException {
		throw new UnsupportedOperationException("sendRedirect");
	}

	@Override
	public void setStatus(int sc) {
		throw new UnsupportedOperationException("setStatus");
	}

	@Override
	public void sendError(int sc, String message) throws IOException {
		throw new UnsupportedOperationException("sendError");
	}

	@Override
	public void setContentLength(int length) {
	}

	@Override
	public void setDateHeader(String name, long date) {
	}

	@Override
	public void setHeader(String name, String value) {
	}

	@Override
	public void setIntHeader(String name, int value) {
	}

	@Override
	public String encodeURL(String URL) {
		return responseGlobals.encodeURL(URL);
	}

	@Override
	public String encodeRedirectURL(String URL) {
		return responseGlobals.encodeRedirectURL(URL);
	}

	@Override
	public boolean isCommitted() {
		throw new UnsupportedOperationException("isCommitted");
	}

	@Override
	public void disableCompression() {
		throw new UnsupportedOperationException("disableCompression");
	}
}
