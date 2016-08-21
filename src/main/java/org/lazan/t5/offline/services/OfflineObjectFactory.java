package org.lazan.t5.offline.services;

import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

public interface OfflineObjectFactory {
	OfflineObjects createOfflineObjects(HttpServletRequest httpRequest, PrintWriter writer);
	OfflineObjects createOfflineObjects(HttpServletRequest httpRequest, OutputStream out);
}
