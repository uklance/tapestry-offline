package org.lazan.t5.offline.services.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lazan.t5.offline.services.OfflineRequestBuilder;
import org.lazan.t5.offline.services.OfflineRequestGlobals;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
@RunWith(MockitoJUnitRunner.class)
public class OfflineRequestBuilderImplTest {
	@Mock private TypeCoercer coercer;
	@Mock private OfflineRequestGlobals requestGlobals;
	
	@Test
	public void testSession() {
		when(requestGlobals.getValues()).thenReturn(Collections.emptyMap());
		OfflineRequestBuilder requestBuilder = new OfflineRequestBuilderImpl(requestGlobals, coercer);
		HttpServletRequest request = requestBuilder.build();
		
		HttpSession session = request.getSession(false);
		assertNull(session);
		
		session = request.getSession(true);
		assertNotNull(session);
		
		HttpSession session2 = request.getSession();
		assertSame(session, session2);
		
		session.setAttribute("foo", "bar");
		assertEquals("bar", session.getAttribute("foo"));
	}
}
