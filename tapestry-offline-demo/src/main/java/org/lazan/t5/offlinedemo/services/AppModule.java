package org.lazan.t5.offlinedemo.services;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.internal.services.ArrayEventContext;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.ioc.services.cron.IntervalSchedule;
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor;
import org.apache.tapestry5.ioc.services.cron.Schedule;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.lazan.t5.offline.DefaultOfflineRequestContext;
import org.lazan.t5.offline.services.OfflineComponentRenderer;
import org.lazan.t5.offline.services.TapestryOfflineModule;

/**
 * This module is automatically included as part of the Tapestry IoC Registry,
 * it's a good place to configure and extend Tapestry, or to place your own
 * service definitions.
 */
@SubModule(TapestryOfflineModule.class)
public class AppModule {
	public static void bind(ServiceBinder binder) {
	}

	public static void contributeFactoryDefaults(MappedConfiguration<String, Object> config) {
	}

	public static void contributeApplicationDefaults(MappedConfiguration<String, Object> config) {
		config.add(SymbolConstants.SUPPORTED_LOCALES, "en,fr");
		config.add(SymbolConstants.COMPRESS_WHITESPACE, false);

		config.add("tapestry-offline.serverName", "offline-servername");
		config.add("tapestry-offline.remoteHost", "offline-remotehost");
		config.add("tapestry-offline.localPort", "-1");
		config.add("tapestry-offline.serverPort", "-2");
	}
	
	@Startup
	public static void scheduleJobs(PeriodicExecutor executor, final OfflineComponentRenderer offlineRenderer, final TypeCoercer typeCoercer) {
		Schedule schedule = new IntervalSchedule(TimeUnit.SECONDS.toMillis(5));
		Runnable runnable = new Runnable() {
			public void run() {
				Map<String, Object> sessionAttributes = new HashMap<String, Object>();
				sessionAttributes.put("sessionAttribute", "Test session attribute");
				EventContext activationContext = new ArrayEventContext(typeCoercer, 10);
				StringWriter writer = new StringWriter();
				boolean loopback = false;
				PageRenderRequestParameters params = new PageRenderRequestParameters("MyPage", activationContext, loopback);
				DefaultOfflineRequestContext requestContext = new DefaultOfflineRequestContext();
				requestContext.setLocale(Locale.FRENCH);
				requestContext.setSession(sessionAttributes);
				Future<?> future = offlineRenderer.renderPage(requestContext, params, new PrintWriter(writer));
				try {
					future.get();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				System.out.println("Offline Page Html = " + writer.toString());
			}
		};
		executor.addJob(schedule, "OfflineRender", runnable);
	}
}
