package org.lazan.t5.offline.services;


public interface OfflineRequestGlobals {
    /**
     * Returns the context path. This always starts with a "/" character and does not end with one, with the exception
     * of servlets in the root context, which return the empty string.
     */
    String getContextPath();

    /**
     * Returns the host name of the server to which the request was sent. It is the value of the part before ":" in the
     * <code>Host</code> header, if any, or the resolved server name, or the server IP address.
     *
     * @return the name of the server
     */
    public String getServerName();

    /**
     * Returns the Internet Protocol (IP) port number of the interface
     * on which the request was received.
     *
     * @return an integer specifying the port number
     * @since 5.2.0
     */
    int getLocalPort();

    /**
     * Returns the port number to which the request was sent.
     * It is the value of the part after ":" in the <code>Host</code> header, if any, or the server port where the
     * client connection
     * was accepted on.
     *
     * @return an integer specifying the port number
     * @since 5.2.5
     */
    int getServerPort();

    /**
     * Returns the fully qualified name of the client
     * or the last proxy that sent the request.
     * If the engine cannot or chooses not to resolve the hostname
     * (to improve performance), this method returns the dotted-string form of
     * the IP address.
     *
     * @return a <code>String</code> containing the fully
     *         qualified name of the client
     * @since 5.3
     */
    String getRemoteHost();
}
