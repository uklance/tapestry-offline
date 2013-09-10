tapestry-offline
================

Offline rendering of tapestry pages and components without a HTTP request

Usage
-----
Pages and components can be rendered via the [OfflineComponentRenderer](https://github.com/uklance/tapestry-offline/blob/master/src/main/java/org/lazan/t5/offline/services/OfflineComponentRenderer.java)
```java
@Inject OfflineComponentRenderer offlineRenderer;
@Inject TypeCoercer typeCoercer;

public void renderPage(Writer writer) throws IOException {
    // setup the PageRenderRequestParameters
    String logicalPageName = ...;
    EventContext activationContext = new ArrayEventContext(typeCoercer, ...);
    boolean loopback = false;
    PageRenderRequestParameters params = new PageRenderRequestParameters(logicalPageName, activationContext, loopback);
    
    // setup the RequestContext
    Map<String, Object> session = new HashMap<String, Object>();
    session.put("foo", "bar");
    DefaultOfflineRequestContext requestContext = new DefaultOfflineRequestContext();
    requestContext.setSession(session);
    requestContext.setParameter("someParam", "paramValue");
    requestContext.setAttribute("someAttribute", "attributeValue");
    requestContext.setHeader("someHeader", "headerValue");

    // render the page offline
    offlineRenderer.renderPage(writer, requestContext, params)
}

public JSONObject renderComponent() throws IOException {
    // setup the ComponentEventRequestParameters
    String activePageName = ...;
    String containingPageName = ...;
    String nestedComponentId = ...;
    String event = ...;
    EventContext pageActivationContext = new ArrayEventContext(typeCoercer, ...);
    EventContext eventContext = new ArrayEventContext(typeCoercer, ...);
    ComponentEventRequestParameters eventParams = new ComponentEventRequestParameters(
        activePageName,
        containingPageName,
        nestedComponentId, 
        event,
        pageActivationContext, 
        eventContext);

    // setup the RequestContext
    Map<String, Object> session = new HashMap<String, Object>();
    session.put("foo", "bar");
    DefaultOfflineRequestContext requestContext = new DefaultOfflineRequestContext();
    requestContext.setSession(session);
    requestContext.setXHR(true);
    requestContext.setParameter("someParam", "paramValue");
    requestContext.setAttribute("someAttribute", "attributeValue");
    requestContext.setHeader("someHeader", "headerValue");
    
    // render the component offline
    return offlineRenderer.renderComponent(requestContext, eventParams);
}
```

AppModule
---------
Configure the offline request symbols in your IOC Module

```java
public static void contributeApplicationDefaults(MappedConfiguration<String, Object> config) {
    config.add("tapestry-offline.serverName", ...);
    config.add("tapestry-offline.remoteHost", ...);
    config.add("tapestry-offline.localPort", ...);
    config.add("tapestry-offline.serverPort", ...);
}
```

Maven
-----
Add the following to your pom.xml:

```xml
<dependencies>
    <dependency>
        <groupId>org.lazan</groupId>
        <artifactId>tapestry-offline</artifactId>
        <version>x.y.z</version> <!-- lookup latest version at https://github.com/uklance/releases -->
    </dependency>
</dependencies>

<repositories>
    <repository>
        <id>lazan-releases</id>
        <url>https://github.com/uklance/releases/raw/master</url>
    </repository>
</repositories>
```

