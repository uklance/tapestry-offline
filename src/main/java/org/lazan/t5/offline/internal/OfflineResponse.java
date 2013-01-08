package org.lazan.t5.offline.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.services.Response;

public class OfflineResponse implements Response {
	private final PrintWriter printWriter;
	
	public OfflineResponse(PrintWriter printWriter) {
		this.printWriter = printWriter;
	}
	
	@Override
	public PrintWriter getPrintWriter(String contentType) throws IOException {
		return printWriter;
	}

	@Override
	public OutputStream getOutputStream(String contentType) throws IOException {
		throw new UnsupportedOperationException("getOutputStream");
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
		throw new UnsupportedOperationException("encodeURL");
	}

	@Override
	public String encodeRedirectURL(String URL) {
		throw new UnsupportedOperationException("encodeRedirectURL");
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
