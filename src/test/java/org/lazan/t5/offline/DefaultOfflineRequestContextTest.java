package org.lazan.t5.offline;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Map;

import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.services.Session;
import org.junit.Test;

public class DefaultOfflineRequestContextTest {
	@Test
	public void testHeaders() {
		DefaultOfflineRequestContext context = new DefaultOfflineRequestContext();
		assertNull(context.getHeader("header"));
		context.setHeader("header", "value");
		assertEquals("value", context.getHeader("header"));
		context.setHeader("header", "value2");
		assertEquals("value2", context.getHeader("header"));
	}

	@Test
	public void testParameters() {
		DefaultOfflineRequestContext context = new DefaultOfflineRequestContext();
		assertNull(context.getParameter("param"));
		assertNull(context.getParameters("param"));
		context.setParameter("param", "value");
		assertEquals("value", context.getParameter("param"));
		assertArrayEquals(new String[] { "value" }, context.getParameters("param"));
		context.setParameter("param", "value2");
		assertArrayEquals(new String[] { "value", "value2" }, context.getParameters("param"));
	}
	
	@Test
	public void testSession() {
		DefaultOfflineRequestContext context = new DefaultOfflineRequestContext();
		assertNull(context.getSession(false));
		try {
			context.getSession(true);
			fail();
		} catch (Exception e) {}
		
		Map<String, Object> map = CollectionFactory.newMap();
		map.put("att", "value");
		context.setSession(map);
		
		Session session = context.getSession(true);
		assertEquals("value", session.getAttribute("att"));
	}
}
