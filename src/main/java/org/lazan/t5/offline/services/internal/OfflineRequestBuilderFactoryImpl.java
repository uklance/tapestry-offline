package org.lazan.t5.offline.services.internal;

import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.lazan.t5.offline.services.OfflineRequestBuilder;
import org.lazan.t5.offline.services.OfflineRequestBuilderFactory;
import org.lazan.t5.offline.services.OfflineRequestGlobals;

public class OfflineRequestBuilderFactoryImpl implements OfflineRequestBuilderFactory {
	private final OfflineRequestGlobals offlineRequestGlobals;
	private final TypeCoercer typeCoercer;
	
	public OfflineRequestBuilderFactoryImpl(OfflineRequestGlobals offlineRequestGlobals, TypeCoercer typeCoercer) {
		super();
		this.offlineRequestGlobals = offlineRequestGlobals;
		this.typeCoercer = typeCoercer;
	}

	@Override
	public OfflineRequestBuilder create() {
		return new OfflineRequestBuilderImpl(offlineRequestGlobals, typeCoercer);
	}
}
