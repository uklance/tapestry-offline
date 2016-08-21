package org.lazan.t5.offline.services.internal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.tapestry5.ioc.services.TypeCoercer;

public class ProxyBuilder {
	private final TypeCoercer typeCoercer;
	private Map<String, MethodHandler> methodHandlers = new LinkedHashMap<String, MethodHandler>();
	private Map<String, Object> defaultValues = new LinkedHashMap<String, Object>();
	
	public ProxyBuilder(TypeCoercer typeCoercer) {
		super();
		this.typeCoercer = typeCoercer;
	}

	public ProxyBuilder withMethodHandler(String methodName, MethodHandler handler) {
		methodHandlers.put(methodName, handler);
		return this;
	}
	
	public ProxyBuilder withDefaultValues(Map<String, Object> defaultValues) {
		this.defaultValues.putAll(defaultValues);
		return this;
	}

	public ProxyBuilder withDefaultValue(String methodName, Object value) {
		this.defaultValues.put(methodName, value);
		return this;
	}
	
	public <T> T build(final Class<T> type) {
		InvocationHandler iHandler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				String methodName = method.getName();
				MethodHandler mHandler = methodHandlers.get(methodName);
				if (mHandler != null) {
					return mHandler.handle(method, args);
				} else if (void.class.equals(method.getReturnType())) {
					return null;
				} else  if (defaultValues.containsKey(methodName)) {
					Object value = defaultValues.get(methodName);
					return typeCoercer.coerce(value, method.getReturnType());
				} else if (methodName.startsWith("get")) {
					String propName = Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
					if (defaultValues.containsKey(propName)) {
						Object value = defaultValues.get(propName);
						return typeCoercer.coerce(value, method.getReturnType());
					}
				} else if (methodName.startsWith("is") && boolean.class.equals(method.getReturnType())) {
					String propName = Character.toLowerCase(methodName.charAt(2)) + methodName.substring(3);
					if (defaultValues.containsKey(propName)) {
						Object value = defaultValues.get(propName);
						return typeCoercer.coerce(value, method.getReturnType());
					}
				}
				throw new RuntimeException(String.format("Unhandeled method %s.%s(%s)", type.getSimpleName(), method.getName(), Arrays.toString(args)));
			}
		};
		return type.cast(Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { type }, iHandler));
	}
	
	public static interface MethodHandler {
		public Object handle(Method method, Object[] args);
	}
}
