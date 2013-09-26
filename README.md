tapestry-offline
================

Offline rendering of [Tapestry](http://tapestry.apache.org/) pages and components without a HTTP servlet request

Usage
-----
Pages and components can be rendered via the [OfflineComponentRenderer](https://github.com/uklance/tapestry-offline/blob/master/src/main/java/org/lazan/t5/offline/services/OfflineComponentRenderer.java)
which can be injected via [Tapestry IOC](http://tapestry.apache.org/ioc.html)
```java
public class MyOfflineRenderer {
    @Inject
    private OfflineComponentRenderer offlineRenderer;

    @Inject
    private TypeCoercer typeCoercer;

    public String renderPageAsString() throws IOException {
        // setup the PageRenderRequestParameters
        String logicalPageName = ...;
        EventContext activationContext = new ArrayEventContext(typeCoercer, ...);
        boolean loopback = false;
        PageRenderRequestParameters params = new PageRenderRequestParameters(
            logicalPageName,
            activationContext,
            loopback
        );
    
        // setup the RequestContext
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("foo", "bar");
        DefaultOfflineRequestContext requestContext = new DefaultOfflineRequestContext();
        requestContext.setSession(session);
        requestContext.setParameter("someParam", "paramValue");
        requestContext.setAttribute("someAttribute", "attributeValue");
        requestContext.setHeader("someHeader", "headerValue");

        // render the page offline
        StringWriter writer = new StringWriter();
        offlineRenderer.renderPage(writer, requestContext, params)
        return writer.toString();
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
            eventContext
        );

        // setup the RequestContext
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("foo", "bar");
        DefaultOfflineRequestContext requestContext = new DefaultOfflineRequestContext();
        requestContext.setSession(session);
        requestContext.setXHR(true);
        requestContext.setParameter("someParam", "paramValue");
        requestContext.setAttribute("someAttribute", "attributeValue");
        requestContext.setHeader("someHeader", "headerValue");
    
        // render the component event offline
        return offlineRenderer.renderComponent(requestContext, eventParams);
    }
}
```

AppModule
---------
Configure the offline request symbols in your IOC Module
```java
/**
 * These are usually only required if generating links when rendering pages / components offline
 */
public static void contributeApplicationDefaults(MappedConfiguration<String, Object> config) {
    // the host name of the server to which the request was sent. It is the value of the part before ":" in the
    // Host header value, if any, or the resolved server name, or the server IP address.
    config.add("tapestry-offline.serverName", ...);

    //  the fully qualified name of the client or the last proxy that sent the request
    config.add("tapestry-offline.remoteHost", ...);
    
    // the Internet Protocol (IP) port number of the interface on which the request was received.
    config.add("tapestry-offline.localPort", ...);
    
    // the port number to which the request was sent.
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

How does it work?
-----------------
First, a [Request](http://tapestry.apache.org/current/apidocs/org/apache/tapestry5/services/Request.html) is constructed
by combining [OfflineRequestContext](https://github.com/uklance/tapestry-offline/blob/master/src/main/java/org/lazan/t5/offline/OfflineRequestContext.java)
parameter with the global [OfflineRequestGlobals](https://github.com/uklance/tapestry-offline/blob/master/src/main/java/org/lazan/t5/offline/services/OfflineRequestGlobals.java)
service. Next, a [Response](http://tapestry.apache.org/current/apidocs/org/apache/tapestry5/services/Response.html)
is created by Writer / OutputStream parameter with the global [OfflineResponseGlobals](https://github.com/uklance/tapestry-offline/blob/master/src/main/java/org/lazan/t5/offline/services/OfflineResponseGlobals.java]
service. The core tapestry page / component rendering pipeline is then invoked on a seperate thread to prevent dirtying
the current thread.
